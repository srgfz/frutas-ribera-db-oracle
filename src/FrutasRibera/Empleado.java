package FrutasRibera;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


public class Empleado extends Persona{
	//Atributos
	private int turno;
	private ArrayList<Pedido> pedidosAsignados;
	//Constructor
		//Sin parámetros
		public Empleado() {
			super();
			// TODO Auto-generated constructor stub
			this.turno=0;
			this.pedidosAsignados = new ArrayList<>();
		}
		//Con parámetros
		public Empleado(String dNI, String nombre, String apellidos, String direccion, int turno) {
			super(dNI, nombre, apellidos, direccion);
			// TODO Auto-generated constructor stub
			this.turno = turno;
			this.pedidosAsignados = new ArrayList<>();
		}
	//Getters y Setters
		public int getTurno() {
			return turno;
		}
		public void setTurno(int turno) {
			this.turno = turno;
		}
		public ArrayList<Pedido> getPedidosAsignados() {
			return pedidosAsignados;
		}
		public void setPedidosAsignados(ArrayList<Pedido> pedidosAsignados) {
			this.pedidosAsignados = pedidosAsignados;
		}
		//toString
		@Override
		public String toString() {
			return "Empleado [turno=" + turno + ", DNI=" + DNI + ", nombre=" + nombre + ", apellidos=" + apellidos
					+ ", direccion=" + direccion + "]";
		}
		//MÉTODOS
		//cargarModulosImpartidos
		public void asignarPedidosTurno() {
			
			File fichero = new File("Pedidos.csv");

			//Habrá que añadir el AL en el que queremos guardar los datos que vayamos a leer

			try {
				Scanner entrada = new Scanner(fichero);
				String cadena = "";
				String[] linea;
				while (entrada.hasNext()) { //Lee si quedan datos en el fichero
					entrada.nextLine();
					cadena = entrada.nextLine();
					linea = cadena.split(";"); //Aquí se guardarán los datos
					if(turno==Integer.parseInt(linea[3])) {//Si el turno coincide
						Pedido p = new Pedido(Integer.parseInt(linea[0]), linea[1], Double.parseDouble(linea[2]), Integer.parseInt(linea[3]));
						pedidosAsignados.add(p);
					}
				}
				entrada.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("No existe el fichero");
				e.printStackTrace();
			}
			
		}

		
}
