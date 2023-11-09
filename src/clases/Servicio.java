package clases;

public class Servicio implements Vendible {

    private String codigo;
    private String nombre;
    private Double precio;
    private String fechaDeInicio;
    private String fechaDeFinalizacion;

    public Servicio(String codigo, String nombre, Double precio, String fechaDeInicio, String fechaDeFinalizacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.fechaDeInicio = fechaDeInicio;
        this.fechaDeFinalizacion = fechaDeFinalizacion;
    }

    @Override
    public String getCodigo() {
        return String.valueOf(codigo);
    }


    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public Double getPrecio() {
        return precio;
    }

    public String getFechaDeInicio() {
        return fechaDeInicio;
    }

    public String getFechaDeFinalizacion() {
        return fechaDeFinalizacion;
    }
}
