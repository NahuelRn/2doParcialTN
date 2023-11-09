package clases;

import java.util.HashMap;
import java.util.Map;

public class Venta {
    private String codigo;
    private Cliente cliente;
    private Vendedor vendedor;
    private Map<Vendible, Integer> renglones;

    public Venta(String codigo, Cliente cliente, Vendedor vendedor) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.renglones = new HashMap<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public Double getTotal() {
        double total = 0.0;

        for (Map.Entry<Vendible, Integer> entry : renglones.entrySet()) {
            Vendible vendible = entry.getKey();
            Integer cantidad = entry.getValue();
            total += vendible.getPrecio() * cantidad;
        }

        return total;
    }

    public void agregarRenglon(Vendible vendible, int cantidad) throws VendibleInexistenteException, StockInsuficienteException {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }

        if (!renglones.containsKey(vendible)) {
            throw new VendibleInexistenteException("El vendible no existe en la venta.");
        }

        if (vendible instanceof Producto) {
            Producto producto = (Producto) vendible;
            int stockActual = producto.getStock();
            if (stockActual >= cantidad) {
                renglones.put(producto, renglones.get(producto) + cantidad);
                producto.setStock(stockActual - cantidad);
            } else {
                throw new StockInsuficienteException("No hay suficiente stock para agregar la cantidad especificada.");
            }
        } else if (vendible instanceof Servicio) {
            renglones.put(vendible, renglones.get(vendible) + cantidad);
        }
    }

    public void agregarRenglon(Producto producto, int cantidad) throws StockInsuficienteException, VendibleInexistenteException {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }

        if (!renglones.containsKey(producto)) {
            throw new VendibleInexistenteException("El producto no existe en la venta.");
        }

        int stockActual = producto.getStock();
        if (stockActual >= cantidad) {
            renglones.put(producto, renglones.get(producto) + cantidad);
            producto.setStock(stockActual - cantidad);
        } else {
            throw new StockInsuficienteException("No hay suficiente stock para agregar la cantidad especificada.");
        }
    }

    public void agregarRenglon(Servicio servicio, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }

        if (!renglones.containsKey(servicio)) {
            renglones.put(servicio, cantidad);
        } else {
            renglones.put(servicio, renglones.get(servicio) + cantidad);
        }
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

}
