package languageacademy;

public class Alumno {
    private String dni;
    private String nombre;
    private String direccion;
    private String telefono;
    private int edad;
    private String tipoAlumno;

    public Alumno() {
    }

    public Alumno(String dni, String nombre, String direccion, String telefono, int edad, String tipoAlumno) {
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.edad = edad;
        this.tipoAlumno = tipoAlumno;
    }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getTipoAlumno() { return tipoAlumno; }
    public void setTipoAlumno(String tipoAlumno) { this.tipoAlumno = tipoAlumno; }

    @Override
    public String toString() {
        return "Alumno{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", edad=" + edad +
                ", tipoAlumno='" + tipoAlumno + '\'' +
                '}';
    }
}