package academiaidiomaDAO;

import academiaidioma.conexion.Conexion;
import academiaidioma.modelo.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class CursoDAO {

    public boolean insertar(Curso curso) {

        String sql = """
                INSERT INTO curso
                (numero_curso, fecha_inicio, fecha_fin,
                 horario, duracion_total,
                 dni_profesor, codigo_programa)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, curso.getNumeroCurso());
            ps.setDate(2, Date.valueOf(curso.getFechaInicio()));
            ps.setDate(3, Date.valueOf(curso.getFechaFin()));
            ps.setString(4, curso.getHorario());
            ps.setInt(5, curso.getDuracionTotal());
            ps.setString(6, curso.getDniProfesor());
            ps.setString(7, curso.getCodigoPrograma());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al insertar curso: "
                    + e.getMessage());

            return false;
        }
    }

    public void listar() {

        String sql = """
                SELECT c.*, p.nombre_programa
                FROM curso c
                INNER JOIN programa_curso p
                    ON c.codigo_programa = p.codigo_programa
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                System.out.println(
                        "Curso: " + rs.getInt("numero_curso")
                        + " | Programa: "
                        + rs.getString("nombre_programa")
                        + " | Inicio: "
                        + rs.getDate("fecha_inicio")
                        + " | Fin: "
                        + rs.getDate("fecha_fin")
                        + " | Horario: "
                        + rs.getString("horario")
                        + " | Profesor: "
                        + rs.getString("dni_profesor")
                );
            }

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    public Curso buscarPorNumero(int numeroCurso) {

        String sql = """
                SELECT * FROM curso
                WHERE numero_curso = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, numeroCurso);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Curso(
                        rs.getInt("numero_curso"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_fin").toLocalDate(),
                        rs.getString("horario"),
                        rs.getInt("duracion_total"),
                        rs.getString("dni_profesor"),
                        rs.getString("codigo_programa")
                );
            }

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizar(Curso curso) {

        String sql = """
                UPDATE curso
                SET fecha_inicio = ?,
                    fecha_fin = ?,
                    horario = ?,
                    duracion_total = ?,
                    dni_profesor = ?,
                    codigo_programa = ?
                WHERE numero_curso = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(curso.getFechaInicio()));
            ps.setDate(2, Date.valueOf(curso.getFechaFin()));
            ps.setString(3, curso.getHorario());
            ps.setInt(4, curso.getDuracionTotal());
            ps.setString(5, curso.getDniProfesor());
            ps.setString(6, curso.getCodigoPrograma());
            ps.setInt(7, curso.getNumeroCurso());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());

            return false;
        }
    }

    public boolean eliminar(int numeroCurso) {

        String sql = """
                DELETE FROM curso
                WHERE numero_curso = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, numeroCurso);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());

            return false;
        }
    }
}