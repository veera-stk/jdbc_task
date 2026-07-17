package jdbc_task;

import java.sql.*;
import java.util.Scanner;

public class TrainingInstitute {

    static final String URL = "jdbc:mysql://localhost:3306/jdbc_task";
    static final String USER = "root";
    static final String PASSWORD = "123456789";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            int choice;

            do {
                System.out.println("\n===== TRAINING INSTITUTE MANAGEMENT =====");
                System.out.println("1. Add Student");
                System.out.println("2. Add Course");
                System.out.println("3. Enroll Student");
                System.out.println("4. Mark Attendance");
                System.out.println("5. Add Marks");
                System.out.println("6. View Students");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        sc.nextLine();
                        System.out.print("Enter Student Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter Phone: ");
                        String phone = sc.nextLine();

                        PreparedStatement ps1 = con.prepareStatement(
                                "INSERT INTO students(name,email,phone) VALUES(?,?,?)");
                        ps1.setString(1, name);
                        ps1.setString(2, email);
                        ps1.setString(3, phone);
                        ps1.executeUpdate();

                        System.out.println("Student Added Successfully.");
                        break;

                    case 2:
                        sc.nextLine();
                        System.out.print("Enter Course Name: ");
                        String course = sc.nextLine();

                        System.out.print("Enter Duration: ");
                        String duration = sc.nextLine();

                        PreparedStatement ps2 = con.prepareStatement(
                                "INSERT INTO courses(course_name,duration) VALUES(?,?)");
                        ps2.setString(1, course);
                        ps2.setString(2, duration);
                        ps2.executeUpdate();

                        System.out.println("Course Added Successfully.");
                        break;

                    case 3:
                        System.out.print("Enter Student ID: ");
                        int sid = sc.nextInt();

                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();

                        PreparedStatement ps3 = con.prepareStatement(
                                "INSERT INTO enrollments(student_id,course_id) VALUES(?,?)");
                        ps3.setInt(1, sid);
                        ps3.setInt(2, cid);
                        ps3.executeUpdate();

                        System.out.println("Enrollment Successful.");
                        break;

                    case 4:
                        System.out.print("Enter Student ID: ");
                        sid = sc.nextInt();

                        System.out.print("Enter Course ID: ");
                        cid = sc.nextInt();

                        sc.nextLine();
                        System.out.print("Enter Date (YYYY-MM-DD): ");
                        String date = sc.nextLine();

                        System.out.print("Enter Status (Present/Absent): ");
                        String status = sc.nextLine();

                        PreparedStatement ps4 = con.prepareStatement(
                                "INSERT INTO attendance(student_id,course_id,attendance_date,status) VALUES(?,?,?,?)");
                        ps4.setInt(1, sid);
                        ps4.setInt(2, cid);
                        ps4.setDate(3, Date.valueOf(date));
                        ps4.setString(4, status);
                        ps4.executeUpdate();

                        System.out.println("Attendance Marked.");
                        break;

                    case 5:
                        System.out.print("Enter Student ID: ");
                        sid = sc.nextInt();

                        System.out.print("Enter Course ID: ");
                        cid = sc.nextInt();

                        System.out.print("Enter Marks: ");
                        int marks = sc.nextInt();

                        PreparedStatement ps5 = con.prepareStatement(
                                "INSERT INTO marks(student_id,course_id,marks) VALUES(?,?,?)");
                        ps5.setInt(1, sid);
                        ps5.setInt(2, cid);
                        ps5.setInt(3, marks);
                        ps5.executeUpdate();

                        System.out.println("Marks Added.");
                        break;

                    case 6:
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM students");

                        System.out.println("\nStudent Details");
                        System.out.println("-----------------------------------------");

                        while (rs.next()) {
                            System.out.println(
                                    rs.getInt("student_id") + "  " +
                                    rs.getString("name") + "  " +
                                    rs.getString("email") + "  " +
                                    rs.getString("phone"));
                        }
                        break;

                    case 7:
                        System.out.println("Thank You!");
                        break;

                    default:
                        System.out.println("Invalid Choice!");
                }

            } while (choice != 7);

            con.close();
            sc.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}