package clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tienda {

    private String cuit;
    private String nombre;
    private Map<Producto, Integer> stock;
    private Map<String, Cliente> clientes;
    private Map<String, Vendedor> vendedores;
    private Map<String, Venta> ventas;
    private List<Servicio> servicios;

    public Tienda(String cuit, String nombre) {
        this.cuit = cuit;
        this.nombre = nombre;
        this.stock = new HashMap<>();
        this.clientes = new HashMap<>();
        this.vendedores = new HashMap<>();
        this.ventas = new HashMap<>();
        this.servicios = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        agregarProducto(producto, 0);
    }

    public void agregarProducto(Producto producto, Integer cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }

        stock.put(producto, cantidad);
    }

    public Integer getStock(Producto producto) {
        return stock.getOrDefault(producto, 0);
    }

    public void agregarCliente(Cliente cliente) {
        clientes.put(cliente.getCuit(), cliente);
    }

    public Cliente getCliente(String cuitEjemplo) {
        return clientes.get(cuitEjemplo);
    }

    public void agregarVendedor(Vendedor vendedor) {
        vendedores.put(vendedor.getDni(), vendedor);
    }

    public Vendedor getVendedor(String dniEjemplo) {
        return vendedores.get(dniEjemplo);
    }

    public void agregarVenta(Venta venta) {
        ventas.put(venta.getCodigo(), venta);
    }

    public void agregarProductoAVenta(String codigoVenta, Producto producto, Integer cantidadVendida) throws VentaInexistenteException, StockInsuficienteException, VendibleInexistenteException {
     
        Venta venta = obtenerVentaPorCodigo(codigoVenta);
        if (venta == null) {
            throw new VentaInexistenteException("La venta con el código " + codigoVenta + " no existe.");
        }

        if (!stock.containsKey(producto)) {
            throw new VendibleInexistenteException("El producto no existe en la tienda.");
        }

        Integer stockDisponible = stock.get(producto);
        if (stockDisponible < cantidadVendida) {
            throw new StockInsuficienteException("Stock insuficiente para agregar la cantidad especificada.");
        }

        venta.agregarRenglon(producto, cantidadVendida);
        stock.put(producto, stockDisponible - cantidadVendida);
    }



    private Venta obtenerVentaPorCodigo(String codigoVenta) {
        for (Venta venta : ventas.values()) {
            if (venta.getCodigo().equals(codigoVenta)) {
                return venta;
            }
        }
        return null;
    }


	public Vendible getVendible(String codigo) throws VendibleInexistenteException {
        for (Producto producto : stock.keySet()) {
            if (producto.getCodigo().equals(codigo)) {
                return producto;
            }
        }
        if (servicios != null) {
            for (Servicio servicio : servicios) {
                if (servicio.getCodigo().equals(codigo)) {
                    return servicio;
                }
            }
        }

        throw new VendibleInexistenteException("El vendible con código " + codigo + " no existe en la tienda.");
    }


    public void agregarServicio(Servicio servicio) {
        List<Servicio> serviciosDisponibles = new ArrayList<>();

        serviciosDisponibles.add(servicio);
    }
    public void agregarServicioAVenta(String codigo, Servicio servicio) throws VentaInexistenteException {
       
        Venta venta = buscarVentaPorCodigo(codigo);

        if (venta == null) {
            throw new VentaInexistenteException("La venta con el código " + codigo + " no existe.");
        }

        venta.agregarRenglon(servicio, 1); 
    }
   
    private Venta buscarVentaPorCodigo(String codigo) {
        for (Map.Entry<String, Venta> entry : ventas.entrySet()) {
            if (entry.getValue().getCodigo().equals(codigo)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void establecerPorcentajeComisionVendedor(String dniVendedor, double porcentajeComisionEsperado) throws VendedorInexistenteException {
        Vendedor vendedor = vendedores.get(dniVendedor);
        if (vendedor != null) {
            vendedor.setPorcentajeComision(porcentajeComisionEsperado);
        } else {
          
            throw new VendedorInexistenteException("El vendedor con DNI " + dniVendedor + " no existe en la tienda.");
        }
    }
    public double calcularMontoTotalComisionesVendedor(String dniVendedor, Map<String, Venta> ventas) {
        double montoComisionTotal = 0.0;

        for (Map.Entry<String, Venta> entry : ventas.entrySet()) {
            Venta venta = entry.getValue();
            if (venta.getVendedor().getDni().equals(dniVendedor)) {
                double porcentajeComision = obtenerPorcentajeComisionVendedor(dniVendedor);

                double precioTotalVenta = calcularPrecioTotalVenta(venta);

                double montoComision = (precioTotalVenta * porcentajeComision) / 100.0;
                montoComisionTotal += montoComision;
            }
        }

        return montoComisionTotal;
    }

    private double calcularPrecioTotalVenta(Venta venta) {
       
        return 0.0;
        }


    private double obtenerPorcentajeComisionVendedor(String dniVendedor) {
        Vendedor vendedor = vendedores.get(dniVendedor);

        if (vendedor != null) {
            return vendedor.getPorcentajeComision();
        } else {
            return 0.0;
        }
    }

	public Map<String, Venta> getVentas() {
		return ventas;
	}


}
