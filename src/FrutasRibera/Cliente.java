package FrutasRibera;

public class Cliente extends Persona{
	//Atributos
	private String metodoPago;
	//Constructor
		//Sin parámetros
		public Cliente() {
			super();
			// TODO Auto-generated constructor stub
			this.metodoPago="";
		}
		//Con parámetros
		public Cliente(String dNI, String nombre, String apellidos, String direccion, String metodoPago) {
			super(dNI, nombre, apellidos, direccion);
			// TODO Auto-generated constructor stub
			this.metodoPago = metodoPago;
		}
	//Getters y Setters
		public String gettipoMusica() {
			return metodoPago;
		}
		public void settipoMusica(String metodoPago) {
			this.metodoPago = metodoPago;
		}
	//toString
		@Override
		public String toString() {
			return "Cliente [metodoPago=" + metodoPago + ", DNI=" + DNI + ", nombre=" + nombre + ", apellidos="
					+ apellidos + ", direccion=" + direccion + "]";
		}
		
		
}
