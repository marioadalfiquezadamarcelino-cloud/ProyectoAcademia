
package academiaidiomaDAO;

import academiaidioma.conexion.Conexion;
import academiaidioma.modelo.Colegio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ColegioDAO {

    // INSERTAR COLEGIO
    public boolean insertar(Colegio colegio) {

        String sql = """
                INSERT INTO colegio (nombre, direccion, telefono)
                VALUES (?, ?, ?)
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, colegio.getNombre());
            ps.setString(2, colegio.getDireccion());
            ps.setString(3, colegio.getTelefono());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error al insertar colegio: " + e.getMessage()
            );
            return false;
        }
    }

    // LISTAR COLEGIOS
    public void listar() {

        String sql = "SELECT * FROM colegio ORDER BY codigo_colegio";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean encontrado = false;

            while (rs.next()) {
                encontrado = true;

                System.out.println(
                        "Código: " + rs.getInt("codigo_colegio")
                        + " | Nombre: " + rs.getString("nombre")
                        + " | Dirección: " + rs.getString("direccion")
                        + " | Teléfono: " + rs.getString("telefono")
                );
            }

            if (!encontrado) {
                System.out.println("No hay colegios registrados.");
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar colegios: " + e.getMessage()
            );
        }
    }

    // BUSCAR COLEGIO POR CÓDIGO
    public Colegio buscarPorCodigo(int codigo) {

        String sql = """
                SELECT *
                FROM colegio
                WHERE codigo_colegio = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Colegio(
                            rs.getInt("codigo_colegio"),
                            rs.getString("nombre"),
                            rs.getString("direccion"),
                            rs.getString("telefono")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al buscar colegio: " + e.getMessage()
            );
        }

        return null;
    }

    // ACTUALIZAR COLEGIO
    public boolean actualizar(Colegio colegio) {

        String sql = """
                UPDATE colegio
                SET nombre = ?,
                    direccion = ?,
                    telefono = ?
                WHERE codigo_colegio = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, colegio.getNombre());
            ps.setString(2, colegio.getDireccion());
            ps.setString(3, colegio.getTelefono());
            ps.setInt(4, colegio.getCodigoColegio());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error al actualizar colegio: " + e.getMessage()
            );
            return false;
        }
    }

    // ELIMINAR COLEGIO
    public boolean eliminar(int codigo) {

        String sql = """
                DELETE FROM colegio
                WHERE codigo_colegio = ?
                """;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, codigo);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error al eliminar colegio: " + e.getMessage()
            );
            return false;
        }
    }
}
