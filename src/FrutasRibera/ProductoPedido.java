package FrutasRibera;

public class ProductoPedido{
	//Atributos
	private int codigoPedido;
	private int codigoProducto;
	private String nombreProducto;
	private String descripcion;
	private String temporada;
	private double precioKg;
	private double kg;
	//Constructor
		//Sin parámetros
	public ProductoPedido() {
		super();
		this.codigoPedido = 0;
		this.codigoProducto = 0;
		this.nombreProducto = "";
		this.descripcion = "";
		this.temporada = "";
		this.precioKg = 0.00;
		this.kg = 0.00;
	}
		//Con parámetros
	public ProductoPedido(int codigoPedido, int codigoProducto, String nombreProducto, String descripcion,
			String temporada, double precioKg, double kg) {
		super();
		this.codigoPedido = codigoPedido;
		this.codigoProducto = codigoProducto;
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
		this.temporada = temporada;
		this.precioKg = precioKg;
		this.kg = kg;
	}
	//Getters y Setters
	public int getCodigoPedido() {
		return codigoPedido;
	}
	public void setCodigoPedido(int codigoPedido) {
		this.codigoPedido = codigoPedido;
	}
	public int getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTemporada() {
		return temporada;
	}
	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}
	public double getPrecioKg() {
		return precioKg;
	}
	public void setPrecioKg(double precioKg) {
		this.precioKg = precioKg;
	}
	public double getKg() {
		return kg;
	}
	public void setKg(double kg) {
		this.kg = kg;
	}
	//toString
	@Override
	public String toString() {
		return ("\t"+this.nombreProducto+"\t"+this.precioKg+"€/kg\t"+this.kg+"kg");

	}

	



		

		
		
}
