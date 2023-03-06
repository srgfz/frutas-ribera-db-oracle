package FrutasRibera;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class App {
	//***Creo los empleados a mano ya que aunque carga bien los parámetros de la BD y la consulta es correcta no me devuelve ningún dato de la bd
	
	public static void main(String[] args){
		ArrayList<Empleado> empleados = new ArrayList<Empleado>();
		BD miconexion = BD.getInstance();
		miconexion.cargarParametrosConexion();

		if(args.length>0) {//Hay parametros
			String[] parametro=args[0].split("=");
			System.out.println(parametro[1]);
			if(args[0].contains("--empleado")) {//1. El programa recibe el parametro --profesor
				paramDni(parametro);


			}
	} else {// 5. El programa no recibe ningun parametro
		paramNone();
		System.out.println("no hay parametros");
	}
		
}

//METODOS
	//Parametro: dni
	public static void paramDni(String[] parametro) {
		//***Así es como consultaría los empleados y generaría las facturas si la BD me devolviera datos:
		BD miconexion = BD.getInstance();
		miconexion.cargarParametrosConexion();
//		String dni = parametro[1];
//		for (Empleado x : miconexion.cargaEmpleados()) {
//			System.out.println(x);
//			for (Pedido y : x.getPedidosAsignados()) {
//				System.out.println(y);
//				miconexion.generarFactura(y);
//			}
//		}
//		try {
//			miconexion.cerrarConsulta();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//**Como la BD no ejecuta la consulta creo los empleados manualmente:
		ArrayList<Empleado> empleados = new ArrayList<Empleado>();
		Empleado e =new Empleado("61111111H", "Carmen", "García", "Dir A", 1);
		Empleado e2 =new Empleado("15151515P", "Nombre2", "Apellido2", "Dir B", 2);
		empleados.add(e);
		empleados.add(e2);
		for (Empleado x : empleados) {
			x.asignarPedidosTurno();
			System.out.println(x+"- "+x.getPedidosAsignados());
			for (Pedido y : x.getPedidosAsignados()) {
				miconexion.generarFactura(y);
				
			}
		}
	}
	
	//Ningun parametro
	public static void paramNone() {
		
	}
	//cargaEmpleados
	public ArrayList<Empleado> cargaEmpleados() {
		BD miconexion = BD.getInstance();
		miconexion.cargarParametrosConexion();
		ArrayList<Empleado> empleados = new ArrayList<>();
		try {
			System.out.println("entra");
			ResultSet rset=BD.getInstance().consultaBD("SELECT * FROM PERSONA WHERE TURNO <> 0");
			rset=miconexion.consultaBD("COMMIT");
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
}
	

