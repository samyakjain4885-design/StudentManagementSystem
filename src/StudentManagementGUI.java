
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class StudentManagementGUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Management System");

frame.setSize(650, 500);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setLocationRelativeTo(null);

        JLabel title = new JLabel(
            "Student Management System",
            SwingConstants.CENTER
        );
        title.setFont(new Font("Arial", Font.BOLD, 24));
title.setHorizontalAlignment(SwingConstants.CENTER);
title.setBorder(
    javax.swing.BorderFactory.createEmptyBorder(15, 10, 15, 10)
);

        JLabel nameLabel = new JLabel("Student Name:");
        JTextField nameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel courseLabel = new JLabel("Course:");
        JTextField courseField = new JTextField(20);

        JLabel marksLabel = new JLabel("Marks:");
        JTextField marksField = new JTextField(20);

nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
courseLabel.setFont(new Font("Arial", Font.BOLD, 14));
marksLabel.setFont(new Font("Arial", Font.BOLD, 14));


        JButton addButton = new JButton("Add Student");
        JButton viewButton = new JButton("View Students");
        JButton deleteButton = new JButton("Delete Student");
        JButton updateButton = new JButton("Update Student");
        JButton searchButton = new JButton("Search Student");

addButton.setFont(new Font("Arial", Font.BOLD, 14));
viewButton.setFont(new Font("Arial", Font.BOLD, 14));
updateButton.setFont(new Font("Arial", Font.BOLD, 14));
deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
searchButton.setFont(new Font("Arial", Font.BOLD, 14));

Color blue = new Color(40, 100, 180);
addButton.setBackground(blue);
viewButton.setBackground(blue);
updateButton.setBackground(blue);
deleteButton.setBackground(blue);
searchButton.setBackground(blue);

        addButton.addActionListener(e -> {
    addStudent(
        nameField.getText().trim(),
        emailField.getText().trim(),
        courseField.getText().trim(),
        marksField.getText().trim()
    );
});
viewButton.addActionListener(e -> viewStudents());

updateButton.addActionListener(e -> {
    try {
        String input = JOptionPane.showInputDialog(
            frame, "Enter Student ID:");

        if (input == null || input.trim().isEmpty()) {
            return;
        }

        int id = Integer.parseInt(input.trim());

        String name = JOptionPane.showInputDialog(
            frame, "Enter new name:");

        if (name == null || name.trim().isEmpty()) {
            return;
        }

        String email = JOptionPane.showInputDialog(
            frame, "Enter new email:");

        if (email == null) {
            return;
        }

        String course = JOptionPane.showInputDialog(
            frame, "Enter new course:");

        if (course == null || course.trim().isEmpty()) {
            return;
        }

        String marksText = JOptionPane.showInputDialog(
            frame, "Enter new marks (0-100):");

        if (marksText == null || marksText.trim().isEmpty()) {
            return;
        }

        double marks = Double.parseDouble(marksText.trim());

        if (marks < 0 || marks > 100) {
            JOptionPane.showMessageDialog(
                frame, "Marks must be between 0 and 100!");
            return;
        }

        updateStudent(
            id, name.trim(), email.trim(),
            course.trim(), marks
        );

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(
            frame, "Enter a valid ID and numeric marks!");
    }
});

searchButton.addActionListener(e -> {
    String input = JOptionPane.showInputDialog(
        frame, "Enter Student ID to search:");

    if (input == null || input.trim().isEmpty()) {
        return;
    }

    try {
        int id = Integer.parseInt(input.trim());
        searchStudent(id);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(
            frame, "Please enter a valid numeric ID!");
    }
});

deleteButton.addActionListener(e -> {
    String input = JOptionPane.showInputDialog(
        frame, "Enter Student ID to delete:");

    if (input == null || input.trim().isEmpty()) {
        return;
    }

    try {
        int id = Integer.parseInt(input.trim());

        int confirm = JOptionPane.showConfirmDialog(
            frame,
            "Are you sure you want to delete Student ID " + id + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String url =
            "jdbc:mysql://localhost:3306/student_management";
       String user = System.getenv("DB_USER");
String password = System.getenv("DB_PASSWORD");

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection con =
                 DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(
                    frame, "Student deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(
                    frame, "Student ID not found!");
            }
        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(
            frame, "Please enter a valid numeric ID!");
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(
            frame, "Database error: " + ex.getMessage());
    }
});
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

panel.setBackground(new Color(240, 245, 250));

panel.setBorder(
    javax.swing.BorderFactory.createEmptyBorder(
        25,25,25,25
    )
);
        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(courseLabel);
        panel.add(courseField);

        panel.add(marksLabel);
        panel.add(marksField);

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(deleteButton);
        panel.add(updateButton);
        panel.add(searchButton);

        frame.setLayout(new BorderLayout(10, 10));
        frame.add(title, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
    private static void addStudent(
        String name, String email, String course, String marksText){

    String url = "jdbc:mysql://localhost:3306/student_management";
   
    String sql = "INSERT INTO students (name, email, course, marks) "
               + "VALUES (?, ?, ?, ?)";

    if (name.isBlank() || course.isBlank()) {
        JOptionPane.showMessageDialog(null,
                "Name and Course are required!");
        return;
    }

    try {
        double marks = Double.parseDouble(marksText);

        if (marks < 0 || marks > 100) {
            JOptionPane.showMessageDialog(null,
                    "Marks must be between 0 and 100!");
            return;
        }
          String user = System.getenv("DB_USER");
String password = System.getenv("DB_PASSWORD");

        try (Connection con =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(
        sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, email.isBlank() ? null : email);
            ps.setString(3, course);
            ps.setDouble(4, marks);
       
ps.executeUpdate();

try (ResultSet rs = ps.getGeneratedKeys()) {
    if (rs.next()) {
        int studentId = rs.getInt(1);

        JOptionPane.showMessageDialog(null,
                "Student added successfully!\n"
                + "Your Student ID is: " + studentId);
    } else {
        JOptionPane.showMessageDialog(null,
                "Student added successfully!");
    }
}
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null,
                "Please enter valid numeric marks!");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null,
                "Database error: " + e.getMessage());
    }
}
private static void viewStudents() {

    String url = "jdbc:mysql://localhost:3306/student_management";
  

    String sql = "SELECT * FROM students";
      String user = System.getenv("DB_USER");
String password = System.getenv("DB_PASSWORD");

    try (Connection con =
                 DriverManager.getConnection(url, user, password);
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        String[] columns = {"ID", "Name", "Email", "Course", "Marks"};
        javax.swing.table.DefaultTableModel model =
                new javax.swing.table.DefaultTableModel(columns, 0);

        while (rs.next()) {
            Object[] row = {
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("course"),
                rs.getDouble("marks")
            };

            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame tableFrame = new JFrame("All Students");
        tableFrame.add(scrollPane);
        tableFrame.setSize(700, 300);
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setVisible(true);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null,
                "Database error: " + e.getMessage());
    }
}

private static void updateStudent(
        int id, String name, String email,
        String course, double marks) {
            

    String url =
        "jdbc:mysql://localhost:3306/student_management";
 String user = System.getenv("DB_USER");
String password = System.getenv("DB_PASSWORD");

    String sql = "UPDATE students SET name=?, email=?, "
               + "course=?, marks=? WHERE id=?";

    try (Connection con =
             DriverManager.getConnection(url, user, password);
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, name);
        ps.setString(2, email.trim().isEmpty() ? null : email);
        ps.setString(3, course);
        ps.setDouble(4, marks);
        ps.setInt(5, id);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            JOptionPane.showMessageDialog(
                null, "Student updated successfully!");
        } else {
            JOptionPane.showMessageDialog(
                null, "Student ID not found!");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            null, "Database error: " + e.getMessage());
    }
}

private static void searchStudent(int id) {
    String url =
        "jdbc:mysql://localhost:3306/student_management";
    String user = System.getenv("DB_USER");
String password = System.getenv("DB_PASSWORD");

    String sql =
        "SELECT * FROM students WHERE id = ?";

    try (Connection con =
             DriverManager.getConnection(url, user, password);
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);

        try (java.sql.ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String details =
                    "ID: " + rs.getInt("id") +
                    "\nName: " + rs.getString("name") +
                    "\nEmail: " + rs.getString("email") +
                    "\nCourse: " + rs.getString("course") +
                    "\nMarks: " + rs.getDouble("marks");

                JOptionPane.showMessageDialog(
                    null, details, "Student Details",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                    null, "Student ID not found!");
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            null, "Database error: " + e.getMessage());
    }
}
}
