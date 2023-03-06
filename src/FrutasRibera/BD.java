package FrutasRibera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import oracle.jdbc.driver.OracleDriver;

public class BD {
	//Atributos:
		private static BD miInstancia=null;
		private static boolean permitirInstancianueva;
		private String cadenaConexion;
		private String usuario;
		private String pass;
		private Connection conn;
		private Statement stmt;
	//Con el booleano permitirInstancianueva controlar que solo exista una
		BD() throws Exception{
			if(!permitirInstancianueva)
				throw new Exception("Ya hay una instancia greadea; usa getInstance()");
		}
	//La primera y única vez que se crea
	//Fíjate que se inicializa a nulo al declarar la variable de la clase
		public static BD getInstance() {
			if(miInstancia==null) {//Si es la primera vez que creo la instancia
				permitirInstancianueva=true;
				try {
					miInstancia=new BD();//Añadimos el try catch por la excepción que lanza el BD()
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//Creo la instancia
				permitirInstancianueva=false;//Después de crear lo vuelvo a poner a false
			}
			
			return miInstancia;
		}
	//Devuelve el conjunto de tuplas de la consulta
		public ResultSet consultaBD(String consulta) throws SQLException {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn=DriverManager.getConnection(cadenaConexion, usuario, pass);
			stmt=conn.createStatement();
			ResultSet rset=stmt.executeQuery(consulta);
			
			return rset;
		 }
	//Cerrar consulta
		public void cerrarConsulta() throws SQLException {
			stmt.close();
		 }
	//Añadir Getters y Setters
		public String getCadenaConexion() {
			return cadenaConexion;
		}
		public void setCadenaConexion(String cadenaConexion) {
			this.cadenaConexion = cadenaConexion;
		}
		public String getUsuario() {
			return usuario;
		}
		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}
		public String getPass() {
			return pass;
		}
		public void setPass(String pass) {
			this.pass = pass;
		}
		public Connection getConn() {
			return conn;
		}
		public void setConn(Connection conn) {
			this.conn = conn;
		}
		public Statement getStmt() {
			return stmt;
		}
		public void setStmt(Statement stmt) {
			this.stmt = stmt;
		}
		//OTROS MÉTODOS
			//cargarParametros de conexion
		public boolean cargarParametrosConexion() {
			File fichero = new File("configTienda.txt");
			String cadena = ""; //Donde guardare los datos que vaya a leer 
			Scanner entrada = null;
			try {
				entrada = new Scanner(fichero);
				String[] linea;
				while (entrada.hasNext()) { //Lee si quedan datos en el fichero
					cadena = entrada.next();//Están todos los parámetros en la misma línea
					linea = cadena.split("-");
						setCadenaConexion(linea[0]);
						setUsuario(linea[1]);
						setPass(linea[2]);
				}
				entrada.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("No existe el fichero");
				e.printStackTrace();
			}
			//Hago una consulta de prueba para ver si se ha conectado correctamente a la BD
			BD miconexion = BD.getInstance();
			try {
				ResultSet rset=miconexion.consultaBD("SELECT * FROM DUAL");
				rset=miconexion.consultaBD("COMMIT");
				miconexion.cerrarConsulta();
			return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}	
		}
		//cargaEmpleados
		public ArrayList<Empleado> cargaEmpleados() {
			BD miconexion = BD.getInstance();
			miconexion.cargarParametrosConexion();
			ArrayList<Empleado> empleados = new ArrayList<>();
			try {
				System.out.println("entra");
				ResultSet rset=BD.getInstance().consultaBD("SELECT * FROM PERSONA WHERE TURNO <> 0");

				while(rset.next()){//Mientras haya datos
			//Guardo los datos en un AL
			System.out.println("datos");

			System.out.println(rset.getString(1));
			Empleado e = new Empleado(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getInt(5));
			e.asignarPedidosTurno();
			empleados.add(e);
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("exception");
		}
			try {
				miconexion.cerrarConsulta();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(empleados);
			return empleados;
		}
		
		//generarFactura
		public Factura generarFactura(Pedido p) {
			//Creo el objeto factura
			Factura f = new Factura(p.getCodigoPedido()+"_"+p.getDniCliente(), p.getCodigoPedido(), p.getDniCliente(), new ArrayList<>(), 0.0);
			f.cargarProductos();
			//Lo escribo en un .txt
			try {
				PrintWriter salida = new PrintWriter(new File("Facturas/Factura"+f.getDniCliente()+".txt"));

				//Habrá que agregar el AL del que leer los datos que vamos a escribir
				salida.println("Factura: "+f.getCodigoFactura());
				salida.println("Cliente: "+f.getDniCliente());
				salida.println("****************************Productos****************************");
				for (ProductoPedido x : f.getProductosPedidos()) {
					salida.println(x);
				}
				salida.println("\t\t\t TOTAL "+f.totalSinIVA());
				salida.println("\t\t\t IVA "+f.ivaReducido());
				//Poner aquí el descuento que está guardado en el pedido
				salida.println("\t\t\t IMPORTE TOTAL:  "+(f.totalSinIVA()+f.ivaReducido()));
				salida.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Pongo los datos de la factura en la BD:
			try {
				ResultSet rset=getInstance().consultaBD("INSERT INTO FACTURA VALUES('"+f.getCodigoFactura()+"',"+f.getCodigoPedido()+",'"+f.getDniCliente()+"',"+(f.totalSinIVA()+f.ivaReducido())+")");
				rset = getInstance().consultaBD("COMMIT");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				BD.getInstance().cerrarConsulta();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return f;
		}
		
		
		

		public void aser() {
			BD miconexion = BD.getInstance();
			miconexion.cargarParametrosConexion();
			try {
				ResultSet rset=miconexion.consultaBD("SELECT * FROM PERSONA");
				while (rset.next()) {
					System.out.println(rset.getString(1));
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//main de prueba
		public static void main(String[] args) {
			ArrayList<Empleado> emp = new ArrayList<Empleado>();
	
			BD miconexion = BD.getInstance();
			if(miconexion.cargarParametrosConexion()) {
				System.out.println("true");
			}
			miconexion.aser();


			emp = miconexion.cargaEmpleados();
			for (Empleado x : emp) {
				System.out.println(x);
			}
		}
}