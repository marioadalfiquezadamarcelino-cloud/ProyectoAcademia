package academiaidiomaDAO;

import academiaidioma.conexion.Conexion;
import academiaidioma.modelo.Matricula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatriculaDAO {

    public boolean insertar(Matricula matricula) {

        String sql = """
                INSERT INTO matricula
                (dni_alumno, numero_curso, nota)
                VALUES (?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, matricula.getDniAlumno());
            ps.setInt(2, matricula.getNumeroCurso());
            ps.setDouble(3, matricula.getNota());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al matricular alumno: "
                    + e.getMessage());

            return false;
        }
    }

    public void listar() {

        String sql = """
                SELECT
                    m.dni_alumno,
                    a.nombre,
                    m.numero_curso,
                    m.nota
                FROM matricula m
                INNER JOIN alumno a
                    ON m.dni_alumno = a.dni
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                System.out.println(
                        "Alumno: " + rs.getString("nombre")
                        + " | DNI: " + rs.getString("dni_alumno")
                        + " | Curso: " + rs.getInt("numero_curso")
                        + " | Nota: " + rs.getDouble("nota")
                );
            }

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean actualizarNota(
            String dniAlumno,
            int numeroCurso,
            double nota) {

        String sql = """
                UPDATE matricula
                SET nota = ?
                WHERE dni_alumno = ?
                AND numero_curso = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setDouble(1, nota);
            ps.setString(2, dniAlumno);
            ps.setInt(3, numeroCurso);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());

            return false;
        }
    }

    public boolean eliminar(
            String dniAlumno,
            int numeroCurso) {

        String sql = """
                DELETE FROM matricula
                WHERE dni_alumno = ?
                AND numero_curso = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, dniAlumno);
            ps.setInt(2, numeroCurso);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());

            return false;
        }
    }
}