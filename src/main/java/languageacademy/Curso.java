package languageacademy;

import java.time.LocalDate;

public class Curso {
    private int numeroCurso;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String horario;
    private int duracionTotal;
    private String dniProfesor;
    private String codigoPrograma;

    public Curso() {
    }

    public Curso(int numeroCurso, LocalDate fechaInicio, LocalDate fechaFin, String horario, int duracionTotal, String dniProfesor, String codigoPrograma) {
        this.numeroCurso = numeroCurso;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horario = horario;
        this.duracionTotal = duracionTotal;
        this.dniProfesor = dniProfesor;
        this.codigoPrograma = codigoPrograma;
    }

    public int getNumeroCurso() { return numeroCurso; }
    public void setNumeroCurso(int numeroCurso) { this.numeroCurso = numeroCurso; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public int getDuracionTotal() { return duracionTotal; }
    public void setDuracionTotal(int duracionTotal) { this.duracionTotal = duracionTotal; }

    public String getDniProfesor() { return dniProfesor; }
    public void setDniProfesor(String dniProfesor) { this.dniProfesor = dniProfesor; }

    public String getCodigoPrograma() { return codigoPrograma; }
    public void setCodigoPrograma(String codigoPrograma) { this.codigoPrograma = codigoPrograma; }

    @Override
    public String toString() {
        return "Curso{" +
                "numeroCurso=" + numeroCurso +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", horario='" + horario + '\'' +
                ", duracionTotal=" + duracionTotal +
                ", dniProfesor='" + dniProfesor + '\'' +
                ", codigoPrograma='" + codigoPrograma + '\'' +
                '}';
    }
}