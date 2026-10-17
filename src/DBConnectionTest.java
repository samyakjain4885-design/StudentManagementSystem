
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionTest {

    public static void main(String[] args) {

        String url =
            "jdbc:mysql://localhost:3306/student_management";

       String user = System.getenv("DB_USER");
String password = System.getenv("DB_PASSWORD");

        try (Connection con =
                 DriverManager.getConnection(url, user, password)) {

            System.out.println(
                "MySQL connection successful!"
            );

        } catch (SQLException e) {

            System.out.println(
                "Connection failed: " + e.getMessage()
            );
        }
    }
}

