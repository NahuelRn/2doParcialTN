package clases;

public class Producto implements Vendible {
    private String codigo;
    private String nombre;
    private Double precio;
    private int stock;


    public Producto(String codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock; 
    }



    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "Producto [Código: " + codigo + ", Nombre: " + nombre + ", Precio: " + precio + "]";
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}