import languageacademy.Connection;
import languageacademy.Model;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try {

            Connection.connect();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

}