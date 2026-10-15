package academiaidiomaDAO;

import academiaidioma.conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultasDAO {

    // 1. Alumnos matriculados en cada curso
    public void alumnosPorCurso() {

        String sql = """
                SELECT
                    c.numero_curso,
                    p.nombre_programa,
                    a.dni,
                    a.nombre AS alumno
                FROM matricula m
                INNER JOIN alumno a
                    ON m.dni_alumno = a.dni
                INNER JOIN curso c
                    ON m.numero_curso = c.numero_curso
                INNER JOIN programa_curso p
                    ON c.codigo_programa = p.codigo_programa
                ORDER BY c.numero_curso, a.nombre
                """;

        ejecutarConsulta(sql);
    }

    // 2. Cursos y sus profesores
    public void cursosPorProfesor() {

        String sql = """
                SELECT
                    c.numero_curso,
                    p.nombre AS profesor,
                    p.apellidos,
                    pc.nombre_programa,
                    pc.idioma,
                    pc.nivel
                FROM curso c
                INNER JOIN profesor p
                    ON c.dni_profesor = p.dni
                INNER JOIN programa_curso pc
                    ON c.codigo_programa = pc.codigo_programa
                ORDER BY c.numero_curso
                """;

        ejecutarConsulta(sql);
    }

    // 3. Cursos de un idioma
    public void cursosPorIdioma(String idioma) {

        String sql = """
                SELECT
                    c.numero_curso,
                    pc.nombre_programa,
                    pc.idioma,
                    pc.nivel,
                    c.fecha_inicio,
                    c.fecha_fin,
                    c.horario
                FROM curso c
                INNER JOIN programa_curso pc
                    ON c.codigo_programa = pc.codigo_programa
                WHERE pc.idioma = ?
                ORDER BY c.fecha_inicio
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, idioma);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    System.out.println(
                            "Curso: " + rs.getInt("numero_curso")
                            + " | Programa: " + rs.getString("nombre_programa")
                            + " | Idioma: " + rs.getString("idioma")
                            + " | Nivel: " + rs.getString("nivel")
                            + " | Inicio: " + rs.getDate("fecha_inicio")
                            + " | Fin: " + rs.getDate("fecha_fin")
                            + " | Horario: " + rs.getString("horario")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }

    // 4. Notas de los alumnos
    public void notasAlumnos() {

        String sql = """
                SELECT
                    a.dni,
                    a.nombre AS alumno,
                    c.numero_curso,
                    pc.nombre_programa,
                    m.nota
                FROM matricula m
                INNER JOIN alumno a
                    ON m.dni_alumno = a.dni
                INNER JOIN curso c
                    ON m.numero_curso = c.numero_curso
                INNER JOIN programa_curso pc
                    ON c.codigo_programa = pc.codigo_programa
                ORDER BY a.nombre, m.nota DESC
                """;

        ejecutarConsulta(sql);
    }

    // 5. Información completa de las matrículas
    public void informacionMatriculas() {

        String sql = """
                SELECT
                    a.nombre AS alumno,
                    a.dni,
                    pc.nombre_programa,
                    pc.idioma,
                    pc.nivel,
                    c.numero_curso,
                    pr.nombre AS profesor,
                    pr.apellidos,
                    m.nota
                FROM matricula m
                INNER JOIN alumno a
                    ON m.dni_alumno = a.dni
                INNER JOIN curso c
                    ON m.numero_curso = c.numero_curso
                INNER JOIN programa_curso pc
                    ON c.codigo_programa = pc.codigo_programa
                INNER JOIN profesor pr
                    ON c.dni_profesor = pr.dni
                ORDER BY a.nombre
                """;

        ejecutarConsulta(sql);
    }

    // Método común para consultas sin parámetros
    private void ejecutarConsulta(String sql) {

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int contador = 0;

            while (rs.next()) {

                contador++;

                System.out.println("----------------------------------------");

                int columnas = rs.getMetaData().getColumnCount();

                for (int i = 1; i <= columnas; i++) {
                    System.out.println(
                            rs.getMetaData().getColumnLabel(i)
                            + ": "
                            + rs.getString(i)
                    );
                }
            }

            if (contador == 0) {
                System.out.println("No se encontraron resultados.");
            }

            System.out.println("----------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }
}