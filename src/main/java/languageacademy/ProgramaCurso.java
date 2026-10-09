package languageacademy;

public class ProgramaCurso {
    private String codigoPrograma;
    private String nombrePrograma;
    private String idioma;
    private String nivel;

    public ProgramaCurso() {
    }

    public ProgramaCurso(String codigoPrograma, String nombrePrograma, String idioma, String nivel) {
        this.codigoPrograma = codigoPrograma;
        this.nombrePrograma = nombrePrograma;
        this.idioma = idioma;
        this.nivel = nivel;
    }

    public String getCodigoPrograma() { return codigoPrograma; }
    public void setCodigoPrograma(String codigoPrograma) { this.codigoPrograma = codigoPrograma; }

    public String getNombrePrograma() { return nombrePrograma; }
    public void setNombrePrograma(String nombrePrograma) { this.nombrePrograma = nombrePrograma; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    @Override
    public String toString() {
        return "ProgramaCurso{" +
                "codigoPrograma='" + codigoPrograma + '\'' +
                ", nombrePrograma='" + nombrePrograma + '\'' +
                ", idioma='" + idioma + '\'' +
                ", nivel='" + nivel + '\'' +
                '}';
    }
}