package academiaidiomaDAO;

import academiaidioma.conexion.Conexion;
import academiaidioma.modelo.Profesor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfesorDAO {

    public boolean insertar(Profesor profesor) {

        String sql = """
                INSERT INTO profesor
                (dni, nombre, apellidos, direccion, telefono)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, profesor.getDni());
            ps.setString(2, profesor.getNombre());
            ps.setString(3, profesor.getApellidos());
            ps.setString(4, profesor.getDireccion());
            ps.setString(5, profesor.getTelefono());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al insertar profesor: "
                    + e.getMessage());

            return false;
        }
    }

    public void listar() {

        String sql = "SELECT * FROM profesor";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                System.out.println(
                        "DNI: " + rs.getString("dni")
                        + " | Nombre: " + rs.getString("nombre")
                        + " | Apellidos: " + rs.getString("apellidos")
                        + " | Teléfono: " + rs.getString("telefono")
                );
            }

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    public Profesor buscarPorDni(String dni) {

        String sql = "SELECT * FROM profesor WHERE dni = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, dni);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Profesor(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("direccion"),
                        rs.getString("telefono")
                );
            }

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizar(Profesor profesor) {

        String sql = """
                UPDATE profesor
                SET nombre = ?,
                    apellidos = ?,
                    direccion = ?,
                    telefono = ?
                WHERE dni = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, profesor.getNombre());
            ps.setString(2, profesor.getApellidos());
            ps.setString(3, profesor.getDireccion());
            ps.setString(4, profesor.getTelefono());
            ps.setString(5, profesor.getDni());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());

            return false;
        }
    }

    public boolean eliminar(String dni) {

        String sql = "DELETE FROM profesor WHERE dni = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, dni);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());

            return false;
        }
    }
}