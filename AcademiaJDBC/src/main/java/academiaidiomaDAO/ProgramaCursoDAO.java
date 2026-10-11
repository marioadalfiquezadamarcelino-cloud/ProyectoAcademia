package academiaidiomaDAO;

import academiaidioma.conexion.Conexion;
import academiaidioma.modelo.ProgramaCurso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgramaCursoDAO {

    public boolean insertar(ProgramaCurso programa) {

        String sql = """
                INSERT INTO programa_curso
                (codigo_programa, nombre_programa, idioma, nivel)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, programa.getCodigoPrograma());
            ps.setString(2, programa.getNombrePrograma());
            ps.setString(3, programa.getIdioma());
            ps.setString(4, programa.getNivel());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al insertar programa: "
                    + e.getMessage());

            return false;
        }
    }

    public void listar() {

        String sql = "SELECT * FROM programa_curso";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                System.out.println(
                        "Código: " + rs.getString("codigo_programa")
                        + " | Nombre: " + rs.getString("nombre_programa")
                        + " | Idioma: " + rs.getString("idioma")
                        + " | Nivel: " + rs.getString("nivel")
                );
            }

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    public ProgramaCurso buscarPorCodigo(String codigo) {

        String sql = """
                SELECT * FROM programa_curso
                WHERE codigo_programa = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new ProgramaCurso(
                        rs.getString("codigo_programa"),
                        rs.getString("nombre_programa"),
                        rs.getString("idioma"),
                        rs.getString("nivel")
                );
            }

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizar(ProgramaCurso programa) {

        String sql = """
                UPDATE programa_curso
                SET nombre_programa = ?,
                    idioma = ?,
                    nivel = ?
                WHERE codigo_programa = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, programa.getNombrePrograma());
            ps.setString(2, programa.getIdioma());
            ps.setString(3, programa.getNivel());
            ps.setString(4, programa.getCodigoPrograma());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());

            return false;
        }
    }

    public boolean eliminar(String codigo) {

        String sql = """
                DELETE FROM programa_curso
                WHERE codigo_programa = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, codigo);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());

            return false;
        }
    }
}