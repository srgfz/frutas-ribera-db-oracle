package FrutasRibera;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Factura implements impuestos{
	//Atributos
	private String codigoFactura;
	private int codigoPedido;
	private String dniCliente;
	private ArrayList<ProductoPedido> productosPedidos;
	private double total;
	//Constructor
		//Sin parámetros
	public Factura() {
		super();
		this.codigoFactura = "";
		this.codigoPedido = 0;
		this.dniCliente = "";
		this.productosPedidos = new ArrayList<>();;
		this.total = 0.00;
	}
		//Con parámetros
	public Factura(String codigoFactura, int codigoPedido, String dniCliente,
			ArrayList<ProductoPedido> productosPedidos, double total) {
		super();
		this.codigoFactura = codigoFactura;
		this.codigoPedido = codigoPedido;
		this.dniCliente = dniCliente;
		this.productosPedidos = new ArrayList<>();;
		this.total = total;
	}
	//Getters y Setters
	public String getCodigoFactura() {
		return codigoFactura;
	}
	public void setCodigoFactura(String codigoFactura) {
		this.codigoFactura = codigoFactura;
	}
	public int getCodigoPedido() {
		return codigoPedido;
	}
	public void setCodigoPedido(int codigoPedido) {
		this.codigoPedido = codigoPedido;
	}
	public String getDniCliente() {
		return dniCliente;
	}
	public void setDniCliente(String dniCliente) {
		this.dniCliente = dniCliente;
	}
	public ArrayList<ProductoPedido> getProductosPedidos() {
		return productosPedidos;
	}
	public void setProductosPedidos(ArrayList<ProductoPedido> productosPedidos) {
		this.productosPedidos = productosPedidos;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	//toString
	@Override
	public String toString() {
		return "Factura [codigoFactura=" + codigoFactura + ", codigoPedido=" + codigoPedido + ", dniCliente="
				+ dniCliente + ", productosPedidos=" + productosPedidos + ", total=" + total + "]";
	}
	//MÉTODOS
		//cargarProductos
			public ArrayList<ProductoPedido> cargarProductos() {
				File fichero = new File("ProductosPedidos.csv");
				try {
					Scanner entrada = new Scanner(fichero);
					String cadena = "";
					String[] linea;
					entrada.nextLine();
					while (entrada.hasNext()) { //Lee si quedan datos en el fichero
						cadena = entrada.nextLine();
						linea = cadena.split(";"); //Aquí se guardarán los datos
						for (int i = 0; i < linea.length; i++) {//Quito los espacios en blanco al principio y al final de cada línea:
							linea[i]=linea[i].trim();
						}
						
							this.productosPedidos.add(new ProductoPedido(Integer.parseInt(linea[0]),Integer.parseInt(linea[1]), linea[2], linea[3], linea[4], Double.parseDouble(linea[5]), Double.parseDouble(linea[6])));
						
					}
					entrada.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("No existe el fichero");
					e.printStackTrace();
				}
				return productosPedidos;
			}


@Override
public double ivaReducido() {
	// TODO Auto-generated method stub
	double ivaReducido=0;
	for (ProductoPedido x : productosPedidos) {//Recorre las facturas
		if (x.getCodigoPedido()==this.codigoPedido) {
			ivaReducido += (x.getKg()*x.getPrecioKg())*0.04;

		}
	}
	return ivaReducido;
}
@Override
public double totalSinIVA() {
	// TODO Auto-generated method stub
	double totalPedidoSinIva=0.00;
	for (ProductoPedido x : productosPedidos) {//Recorre las facturas
		if (x.getCodigoPedido()==this.codigoPedido) {
			totalPedidoSinIva += x.getKg()*x.getPrecioKg();

		}

	}
	return totalPedidoSinIva;
}


////main de pruebas
//public static void main(String[] args) {
//Factura f = new Factura();
//double prueba=0.0;
//for (ProductoPedido x : f.getProductosPedidos()) {
//	System.out.println(x);
//}
//
//}		

		
		
}
