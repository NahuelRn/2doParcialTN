package clases;

public class Vendedor {
    private String dni;
    private String nombre;
	private double porcentajeComision;

    public Vendedor(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPorcentajeComision(double porcentajeComisionEsperado) {
        this.porcentajeComision = porcentajeComisionEsperado;
    }

    public double getPorcentajeComision() {
        return this.porcentajeComision;
    }


}
