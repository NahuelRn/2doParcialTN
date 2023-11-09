package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import clases.Cliente;
import clases.ClienteInexistenteException;
import clases.Producto;
import clases.Servicio;
import clases.StockInsuficienteException;
import clases.Tienda;
import clases.Vendedor;
import clases.VendedorInexistenteException;
import clases.Vendible;
import clases.VendibleInexistenteException;
import clases.Venta;
import clases.VentaInexistenteException;

public class testTienda {
	
	@Test
	public void queSePuedaAgregarProductos() throws VendibleInexistenteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		Producto producto = new Producto("1", "Producto nuevo", 100d,1);
		tienda.agregarProducto(producto);
		Vendible productoActual = (Producto) tienda.getVendible(producto.getCodigo());
		assertEquals(producto, productoActual);
	}
	
	@Test
	public void queSePuedaAgregarStock() throws VendibleInexistenteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		Producto producto = new Producto("1", "Producto nuevo", 100d,2);
		Integer cantidad = 10;
		tienda.agregarProducto(producto, cantidad);
		Integer stockActual = tienda.getStock((Producto) producto); 
		assertEquals(cantidad, stockActual);
		
		
	}
	
	@Test
	public void queSePuedaAgregarUnCliente() throws ClienteInexistenteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		String cuitEjemplo = "30123456780";
		Cliente cliente = new Cliente(cuitEjemplo, "Cliente de ejemplo");
		tienda.agregarCliente(cliente);
		Cliente clienteActual = tienda.getCliente(cuitEjemplo);
		assertEquals(cliente, clienteActual);
		
	}
	
	@Test
	public void queSePuedaAgregarUnVendedor() throws VendedorInexistenteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		String dniEjemplo = "12345678";
		Vendedor vendedor = new Vendedor (dniEjemplo, "Vendedor de ejemplo");
		tienda.agregarVendedor(vendedor);
		Vendedor vendedorActual = tienda.getVendedor(dniEjemplo);
		assertEquals(vendedor, vendedorActual);
		
	}
	
	@Test
	public void queSePuedaHacerUnaVentaDeUnProducto() throws VentaInexistenteException, VendibleInexistenteException, StockInsuficienteException {
	    Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
	    String cuitCliente = "30123456780";
	    Cliente cliente = new Cliente(cuitCliente, "Cliente de ejemplo");
	    tienda.agregarCliente(cliente);
	    String dniEjemplo = "12345678";
	    Vendedor vendedor = new Vendedor(dniEjemplo, "Vendedor de ejemplo");
	    Vendible producto = new Producto("1", "Producto nuevo", 100d, 10);
	    tienda.agregarProducto((Producto) producto, 10); 

	    Venta ticket = new Venta("C-0001", cliente, vendedor);
	    tienda.agregarVenta(ticket);
	    Integer cantidadVendida = 5; 

	    try {	    	
	    	tienda.agregarProductoAVenta(ticket.getCodigo(), (Producto) producto, cantidadVendida);	    	
	        Integer stockEsperado = 5; 
	        Integer stockActual = tienda.getStock((Producto) producto);
	        assertEquals(stockEsperado, stockActual);
	    } catch (StockInsuficienteException e) {
	        fail("La venta de producto debería ser exitosa con suficiente stock.");
	    }
	}

	
	@Test (expected = StockInsuficienteException.class)
	public void queNoSePuedaAgregarUnaVentaPorStockInsuficiente() throws VentaInexistenteException, VendibleInexistenteException, StockInsuficienteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		String cuitCliente = "30123456780";
		Cliente cliente = new Cliente(cuitCliente, "Cliente de ejemplo");
		tienda.agregarCliente(cliente);
		String dniEjemplo = "12345678";
		Vendedor vendedor = new Vendedor (dniEjemplo, "Vendedor de ejemplo");
		Producto producto = new Producto("1", "Producto nuevo", 100d,10);
		Integer stockInicial = 10;
		tienda.agregarProducto(producto, stockInicial);
		Venta ticket = new Venta("C-0001", cliente, vendedor);
		tienda.agregarVenta(ticket);
		Integer cantidadVendida = 15;
		tienda.agregarProductoAVenta(ticket.getCodigo(), producto, cantidadVendida);
		
	}
	
	@Test
	public void queSePuedaHacerUnaVentaDeUnServicio() throws VentaInexistenteException, VendibleInexistenteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		String cuitCliente = "30123456780";
		Cliente cliente = new Cliente(cuitCliente, "Cliente de ejemplo");
		tienda.agregarCliente(cliente);
		String dniEjemplo = "12345678";
		Vendedor vendedor = new Vendedor (dniEjemplo, "Vendedor de ejemplo");
		Servicio servicio = new Servicio("1", "Servicio Técnico", 100d, "2023-02-01", "2023-03-01");
		Vendible producto = new Producto("3", "Producto nuevo", 100d, 1);
		Integer stockInicial = 10;
		tienda.agregarProducto((Producto) producto, stockInicial);
		
		tienda.agregarServicio((Servicio) servicio);
		Venta venta = new Venta("C-0001", cliente, vendedor);
		tienda.agregarVenta(venta);
		tienda.agregarServicioAVenta(venta.getCodigo(), servicio);
		Double totalEsperado = 100d;
		Double totalActual = venta.getTotal();
		assertEquals(totalEsperado, totalActual);
		
	}
	@Test
	public void queSePuedaHacerUnaVentaDeUnProductosYServicios() throws VentaInexistenteException, VendibleInexistenteException, StockInsuficienteException {
	    Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
	    String cuitCliente = "30123456780";
	    Cliente cliente = new Cliente(cuitCliente, "Cliente de ejemplo");
	    tienda.agregarCliente(cliente);
	    String dniEjemplo = "12345678";
	    Vendedor vendedor = new Vendedor(dniEjemplo, "Vendedor de ejemplo");

	    Venta venta = new Venta("C-0001", cliente, vendedor);
	    tienda.agregarVenta(venta);
	    Vendible vendible;

	    vendible = new Servicio("1", "Servicio Técnico", 100d, "2023-02-01", "2023-03-01");
	    tienda.agregarServicio((Servicio) vendible);
	    tienda.agregarServicioAVenta(venta.getCodigo(), (Servicio) vendible);

	    vendible = new Producto("2", "Producto nuevo", 350d, 1);
	    Integer stockInicial = 10;
	    tienda.agregarProducto((Producto) vendible, stockInicial);
	    Integer cantidadVendida = 2;
	    tienda.agregarProductoAVenta(venta.getCodigo(), (Producto) vendible, cantidadVendida);

	    Double totalServicio = 100d; 
	    Double totalProducto = 350d * cantidadVendida;
	    Double totalEsperado = totalServicio + totalProducto;
	    Double totalActual = venta.getTotal();
	    assertEquals(totalEsperado, totalActual);
	}

	@Test
	public void queSePuedaEstablecerElPorcentajeDeComisionDeUnVendedor() {
	    Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
	    String dniVendedor = "12345678";
	    Vendedor vendedor = new Vendedor(dniVendedor, "Vendedor de ejemplo");
	    tienda.agregarVendedor(vendedor);
	    
	    double porcentajeComisionEsperado = 10.0; 

	   
	    try {
	        tienda.establecerPorcentajeComisionVendedor(dniVendedor, porcentajeComisionEsperado);
	    } catch (VendedorInexistenteException e) {
	       
	        System.err.println("Error de vendedor inexistente: " + e.getMessage());
	    }
	    
	    double porcentajeComisionActual = vendedor.getPorcentajeComision();
	    
	    assertEquals(porcentajeComisionEsperado, porcentajeComisionActual, 0.01);
	}

	@Test
	public void queSeCalculeElMontoTotalDeComisionesQueTieneUnVendedor() throws VentaInexistenteException, VendibleInexistenteException, StockInsuficienteException, VendedorInexistenteException {
	    try {
	        Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
	        String dniVendedor = "12345678";
	        Vendedor vendedor = new Vendedor(dniVendedor, "Vendedor de ejemplo");
	        tienda.agregarVendedor(vendedor);

	        double porcentajeComision = 10.0;
	        tienda.establecerPorcentajeComisionVendedor(dniVendedor, porcentajeComision);

	        String cuitCliente = "30123456780";
	        Cliente cliente = new Cliente(cuitCliente, "Cliente de ejemplo");
	        tienda.agregarCliente(cliente);

	        Venta venta = new Venta("C-0001", cliente, vendedor);
	        tienda.agregarVenta(venta);

	        Producto producto = new Producto("1", "Producto nuevo", 100d, 10);
	        tienda.agregarProducto(producto, 10);

	        int cantidadVendidaProducto = 5;
	        tienda.agregarProductoAVenta(venta.getCodigo(), producto, cantidadVendidaProducto);

	        
	        Servicio servicio = new Servicio("2", "Servicio Técnico", 200d, "2023-02-01", "2023-03-01");
	        tienda.agregarServicio(servicio);
	        tienda.agregarServicioAVenta(venta.getCodigo(), servicio);

	       
	        double montoComisionProducto = (producto.getPrecio() * cantidadVendidaProducto * porcentajeComision) / 100.0;
	        double montoComisionServicio = (servicio.getPrecio() * porcentajeComision) / 100.0;

	       
	        double montoComisionEsperado = montoComisionProducto + montoComisionServicio;
	        double montoComisionActual = tienda.calcularMontoTotalComisionesVendedor(dniVendedor, tienda.getVentas());

	        assertEquals(montoComisionEsperado, montoComisionActual, 0.01);
	    } catch (VendedorInexistenteException e) {
	        System.err.println("Error de vendedor inexistente: " + e.getMessage());
	    }
	}


	}
