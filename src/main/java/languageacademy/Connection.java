package languageacademy;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {

    private static final String URL = "jdbc:mariadb://localhost:3306/tu_base_de_datos";

    private static final String USER = "root";
    private static final String PASSWORD = "JSM123";

    public static java.sql.Connection connect() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

}

