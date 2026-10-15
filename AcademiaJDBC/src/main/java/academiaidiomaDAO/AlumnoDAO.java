package academiaidiomaDAO;

import academiaidioma.conexion.Conexion;
import academiaidioma.modelo.Alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumnoDAO {

    public boolean insertar(Alumno alumno) {

        String sql = """
                INSERT INTO alumno
                (dni, nombre, direccion, telefono, edad, tipo_alumno)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, alumno.getDni());
            ps.setString(2, alumno.getNombre());
            ps.setString(3, alumno.getDireccion());
            ps.setString(4, alumno.getTelefono());
            ps.setInt(5, alumno.getEdad());
            ps.setString(6, alumno.getTipoAlumno());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println("Error al insertar alumno: "
                    + e.getMessage());

            return false;
        }
    }

    public void listar() {

        String sql = "SELECT * FROM alumno";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                System.out.println(
                        "DNI: " + rs.getString("dni")
                        + " | Nombre: " + rs.getString("nombre")
                        + " | Dirección: " + rs.getString("direccion")
                        + " | Teléfono: " + rs.getString("telefono")
                        + " | Edad: " + rs.getInt("edad")
                        + " | Tipo: " + rs.getString("tipo_alumno")
                );
            }

        } catch (SQLException e) {

            System.out.println("Error al consultar alumnos: "
                    + e.getMessage());
        }
    }

    public Alumno buscarPorDni(String dni) {

        String sql = "SELECT * FROM alumno WHERE dni = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Alumno(
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("direccion"),
                            rs.getString("telefono"),
                            rs.getInt("edad"),
                            rs.getString("tipo_alumno")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println("Error al buscar alumno: "
                    + e.getMessage());
        }

        return null;
    }

    public boolean actualizar(Alumno alumno) {

        String sql = """
                UPDATE alumno
                SET nombre = ?,
                    direccion = ?,
                    telefono = ?,
                    edad = ?,
                    tipo_alumno = ?
                WHERE dni = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getDireccion());
            ps.setString(3, alumno.getTelefono());
            ps.setInt(4, alumno.getEdad());
            ps.setString(5, alumno.getTipoAlumno());
            ps.setString(6, alumno.getDni());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al actualizar alumno: "
                    + e.getMessage());

            return false;
        }
    }

    public boolean eliminar(String dni) {

        String sql = "DELETE FROM alumno WHERE dni = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, dni);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al eliminar alumno: "
                    + e.getMessage());

            return false;
        }
    }
}