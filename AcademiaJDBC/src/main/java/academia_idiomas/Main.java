
package academia_idiomas;

import academiaidioma.conexion.Conexion;
import academiaidiomaDAO.ConsultasDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Arrays;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final Tabla[] TABLAS = {
        new Tabla("colegio",
            new String[]{"codigo_colegio", "nombre", "direccion", "telefono"},
            new String[]{"codigo_colegio"}, "codigo_colegio"),

        new Tabla("alumno",
            new String[]{"dni", "nombre", "direccion", "telefono",
                "edad", "tipo_alumno", "codigo_colegio"},
            new String[]{"dni"}, null),

        new Tabla("profesor",
            new String[]{"dni", "nombre", "apellidos", "direccion", "telefono"},
            new String[]{"dni"}, null),

        new Tabla("programa_curso",
            new String[]{"codigo_programa", "nombre_programa", "idioma", "nivel"},
            new String[]{"codigo_programa"}, null),

        new Tabla("curso",
            new String[]{"numero_curso", "fecha_inicio", "fecha_fin",
                "horario", "duracion_total", "dni_profesor", "codigo_programa"},
            new String[]{"numero_curso"}, null),

        new Tabla("matricula",
            new String[]{"dni_alumno", "numero_curso", "nota"},
            new String[]{"dni_alumno", "numero_curso"}, null)
    };

    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("\n================================");
            System.out.println("       ACADEMIA DE IDIOMAS");
            System.out.println("================================");
            System.out.println("1. Gestionar colegios");
            System.out.println("2. Gestionar alumnos");
            System.out.println("3. Gestionar profesores");
            System.out.println("4. Gestionar programas de cursos");
            System.out.println("5. Gestionar cursos");
            System.out.println("6. Gestionar matrículas");
            System.out.println("7. Menú de consultas");
            System.out.println("0. Salir");
            System.out.println("================================");

            opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1 -> menuTabla(TABLAS[0]);
                case 2 -> menuTabla(TABLAS[1]);
                case 3 -> menuTabla(TABLAS[2]);
                case 4 -> menuTabla(TABLAS[3]);
                case 5 -> menuTabla(TABLAS[4]);
                case 6 -> menuTabla(TABLAS[5]);
                case 7 -> menuConsultas();
                case 0 -> System.out.println("Aplicación finalizada.");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        sc.close();
    }

    // Menú CRUD de cada tabla
    private static void menuTabla(Tabla tabla) {
        int opcion;

        do {
            System.out.println("\n--- GESTIÓN DE " + tabla.nombre.toUpperCase() + " ---");
            System.out.println("1. Insertar");
            System.out.println("2. Listar");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al menú principal");

            opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1 -> insertar(tabla);
                case 2 -> listar(tabla);
                case 3 -> actualizar(tabla);
                case 4 -> eliminar(tabla);
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    // CREATE: insertar registros
    private static void insertar(Tabla tabla) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tabla.nombre).append(" (");

        String[] columnas = tabla.columnas;
        int inicio = tabla.columnaAutoIncremental == null ? 0 : 1;

        String[] columnasInsertar = Arrays.copyOfRange(
            columnas, inicio, columnas.length
        );

        sql.append(String.join(", ", columnasInsertar));
        sql.append(") VALUES (");

        sql.append("?,".repeat(columnasInsertar.length));
        sql.setLength(sql.length() - 1);
        sql.append(")");

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql.toString())) {

            for (int i = 0; i < columnasInsertar.length; i++) {
                String columna = columnasInsertar[i];
                String valor = leerTexto("Introduce " + columna + ": ");
                asignarValor(ps, i + 1, valor);
            }

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Registro insertado correctamente.");
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // READ: listar todos los registros
    private static void listar(Tabla tabla) {
        String sql = "SELECT * FROM " + tabla.nombre;

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int columnas = rs.getMetaData().getColumnCount();
            boolean hayResultados = false;

            while (rs.next()) {
                hayResultados = true;

                System.out.println("\n------------------------------");

                for (int i = 1; i <= columnas; i++) {
                    System.out.println(
                        rs.getMetaData().getColumnLabel(i)
                        + ": " + rs.getObject(i)
                    );
                }
            }

            if (!hayResultados) {
                System.out.println("No hay registros en esta tabla.");
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // UPDATE: actualizar registros mediante su clave primaria
    private static void actualizar(Tabla tabla) {
        StringBuilder sql = new StringBuilder(
            "UPDATE " + tabla.nombre + " SET "
        );

        boolean primera = true;

        for (String columna : tabla.columnas) {
            if (Arrays.asList(tabla.claves).contains(columna)) {
                continue;
            }

            if (!primera) {
                sql.append(", ");
            }

            sql.append(columna).append(" = ?");
            primera = false;
        }

        if (primera) {
            System.out.println("No hay campos que actualizar.");
            return;
        }

        sql.append(" WHERE ");
        sql.append(construirCondicion(tabla.claves));

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql.toString())) {

            int posicion = 1;

            for (String columna : tabla.columnas) {
                if (Arrays.asList(tabla.claves).contains(columna)) {
                    continue;
                }

                String valor = leerTexto(
                    "Nuevo valor para " + columna + ": "
                );

                asignarValor(ps, posicion++, valor);
            }

            posicion = solicitarClaves(tabla, ps, posicion);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Registro actualizado correctamente.");
            } else {
                System.out.println("No se encontró el registro.");
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // DELETE: eliminar un registro por su clave primaria
    private static void eliminar(Tabla tabla) {
        String sql = "DELETE FROM " + tabla.nombre
            + " WHERE " + construirCondicion(tabla.claves);

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            solicitarClaves(tabla, ps, 1);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Registro eliminado correctamente.");
            } else {
                System.out.println("No se encontró el registro.");
            }

        } catch (SQLException e) {
            mostrarError(e);
        }
    }

    // Construye condiciones como dni_alumno = ? AND numero_curso = ?
    private static String construirCondicion(String[] claves) {
        StringBuilder condicion = new StringBuilder();

        for (String clave : claves) {
            if (condicion.length() > 0) {
                condicion.append(" AND ");
            }

            condicion.append(clave).append(" = ?");
        }

        return condicion.toString();
    }

    // Solicita las claves necesarias para localizar un registro
    private static int solicitarClaves(
            Tabla tabla, PreparedStatement ps, int posicion)
            throws SQLException {

        for (String clave : tabla.claves) {
            String valor = leerTexto(
                "Introduce la clave " + clave + ": "
            );

            asignarValor(ps, posicion++, valor);
        }

        return posicion;
    }

    // Asigna parámetros de forma segura
    private static void asignarValor(
            PreparedStatement ps, int posicion, String valor)
            throws SQLException {

        if (valor.equalsIgnoreCase("NULL")) {
            ps.setObject(posicion, null);
        } else {
            ps.setString(posicion, valor);
        }
    }

    // Menú de consultas
    private static void menuConsultas() {
        ConsultasDAO consultas = new ConsultasDAO();
        int opcion;

        do {
            System.out.println("\n--- MENÚ DE CONSULTAS ---");
            System.out.println("1. Alumnos por curso");
            System.out.println("2. Cursos por profesor");
            System.out.println("3. Cursos por idioma");
            System.out.println("4. Notas de los alumnos");
            System.out.println("5. Información de matrículas");
            System.out.println("0. Volver al menú principal");

            opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1 -> consultas.alumnosPorCurso();
                case 2 -> consultas.cursosPorProfesor();
                case 3 -> {
                    String idioma = leerTexto(
                        "Introduce el idioma que quieres buscar: "
                    );
                    consultas.cursosPorIdioma(idioma);
                }
                case 4 -> consultas.notasAlumnos();
                case 5 -> consultas.informacionMatriculas();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debes introducir un número entero.");
            }
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static void mostrarError(SQLException e) {
        System.out.println("Error en la base de datos: " + e.getMessage());
        System.out.println("Código SQL: " + e.getErrorCode());
    }

    // Configuración de cada tabla
    private static class Tabla {
        private final String nombre;
        private final String[] columnas;
        private final String[] claves;
        private final String columnaAutoIncremental;

        private Tabla(
                String nombre,
                String[] columnas,
                String[] claves,
                String columnaAutoIncremental) {

            this.nombre = nombre;
            this.columnas = columnas;
            this.claves = claves;
            this.columnaAutoIncremental = columnaAutoIncremental;
        }
    }
}
