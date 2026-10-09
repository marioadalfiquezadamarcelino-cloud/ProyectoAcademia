package languageacademy;

public class Matricula {
    private String dniAlumno;
    private int numeroCurso;
    private double nota;

    public Matricula() {
    }

    public Matricula(String dniAlumno, int numeroCurso, double nota) {
        this.dniAlumno = dniAlumno;
        this.numeroCurso = numeroCurso;
        this.nota = nota;
    }

    public String getDniAlumno() { return dniAlumno; }
    public void setDniAlumno(String dniAlumno) { this.dniAlumno = dniAlumno; }

    public int getNumeroCurso() { return numeroCurso; }
    public void setNumeroCurso(int numeroCurso) { this.numeroCurso = numeroCurso; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

    @Override
    public String toString() {
        return "Matricula{" +
                "dniAlumno='" + dniAlumno + '\'' +
                ", numeroCurso=" + numeroCurso +
                ", nota=" + nota +
                '}';
    }
}