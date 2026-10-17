# Student Management System

## Project Overview
The Student Management System is a desktop application developed using Java Swing and MySQL. It helps users manage student records through a graphical user interface.

## Technologies Used
- Java
- Java Swing
- JDBC (Java Database Connectivity)
- MySQL
- MySQL Connector/J
- Visual Studio Code

## Features
- Add new student records
- Automatically generate Student IDs
- View all student records
- Search students by ID
- Update student details
- Delete student records
- Validate student name, course, and marks
- Handle duplicate email addresses

## Database Setup
1. Open MySQL Workbench.
2. Create a database named `student_management`.
3. Create the `students` table with columns for ID, name, email, course, and marks.
4. Configure the database connection details in the Java source code.

## How to Run
1. Install the JDK and MySQL.
2. Add MySQL Connector/J to the project's `lib` folder.
3. Configure the Java project to include the connector JAR.
4. Update the database username and password in the Java code.
5. Run `StudentManagementGUI.java` from VS Code.

## Learning Outcomes
- Java Swing GUI development
- JDBC and MySQL integration
- CRUD operations
- Input validation and exception handling