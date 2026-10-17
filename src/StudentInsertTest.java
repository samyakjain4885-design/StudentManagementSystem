
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentInsertTest {

    public static void main(String[] args) {

        String url =
            "jdbc:mysql://localhost:3306/student_management";
        String user = System.getenv("DB_USER");
String password = System.getenv("DB_PASSWORD");

        String sql = """
            INSERT INTO students (name, email, course, marks)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection con =
                 DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "Amit Verma");
            ps.setString(2, "amit.verma@example.com");
            ps.setString(3, "Java");
            ps.setDouble(4, 88.50);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
        }
    }
}
