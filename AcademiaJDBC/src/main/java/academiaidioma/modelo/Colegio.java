
package academiaidioma.modelo;

public class Colegio {

    private int codigoColegio;
    private String nombre;
    private String direccion;
    private String telefono;

    public Colegio() {
    }

    public Colegio(int codigoColegio, String nombre,
                   String direccion, String telefono) {
        this.codigoColegio = codigoColegio;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getCodigoColegio() {
        return codigoColegio;
    }

    public void setCodigoColegio(int codigoColegio) {
        this.codigoColegio = codigoColegio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Colegio{" +
                "codigoColegio=" + codigoColegio +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
