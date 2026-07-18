package jdbc_task;

import java.sql.*;
import java.util.Scanner;

public class EmployeeLeaveSystem {

    static final String URL = "jdbc:mysql://localhost:3306/jdbc_task";
    static final String USER = "root";
    static final String PASSWORD = "123456789";

    static Connection con;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            int choice;

            do {

                System.out.println("\n========== Employee Leave Management ==========");
                System.out.println("1. Apply Leave");
                System.out.println("2. View Leave Requests");
                System.out.println("3. Approve Leave");
                System.out.println("4. Reject Leave");
                System.out.println("5. Delete Leave Request");
                System.out.println("6. Exit");
                System.out.print("Enter Choice : ");

                choice = sc.nextInt();

                switch(choice) {

                    case 1:
                        applyLeave();
                        break;

                    case 2:
                        viewLeaves();
                        break;

                    case 3:
                        approveLeave();
                        break;

                    case 4:
                        rejectLeave();
                        break;

                    case 5:
                        deleteLeave();
                        break;

                    case 6:
                        System.out.println("Thank You");
                        break;

                    default:
                        System.out.println("Invalid Choice");

                }

            } while(choice != 6);

            con.close();

        }

        catch(Exception e) {
            e.printStackTrace();
        }

    }

    // Apply Leave

    static void applyLeave() {

        try {

            System.out.print("Employee ID : ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Employee Name : ");
            String name = sc.nextLine();

            System.out.print("Leave Type : ");
            String type = sc.nextLine();

            System.out.print("From Date (YYYY-MM-DD) : ");
            String from = sc.nextLine();

            System.out.print("To Date (YYYY-MM-DD) : ");
            String to = sc.nextLine();

            System.out.print("Reason : ");
            String reason = sc.nextLine();

            String sql = "INSERT INTO employee_leave(emp_id,emp_name,leave_type,from_date,to_date,reason,status) VALUES(?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, type);
            ps.setDate(4, Date.valueOf(from));
            ps.setDate(5, Date.valueOf(to));
            ps.setString(6, reason);
            ps.setString(7, "Pending");

            ps.executeUpdate();

            System.out.println("Leave Request Submitted.");

        }

        catch(Exception e) {
            e.printStackTrace();
        }

    }

    // View Leaves

    static void viewLeaves() {

        try {

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM employee_leave");

            System.out.println("\n-------------------------------------------------------------");

            while(rs.next()) {

                System.out.println("Leave ID : " + rs.getInt("leave_id"));
                System.out.println("Employee ID : " + rs.getInt("emp_id"));
                System.out.println("Name : " + rs.getString("emp_name"));
                System.out.println("Type : " + rs.getString("leave_type"));
                System.out.println("From : " + rs.getDate("from_date"));
                System.out.println("To : " + rs.getDate("to_date"));
                System.out.println("Reason : " + rs.getString("reason"));
                System.out.println("Status : " + rs.getString("status"));
                System.out.println("--------------------------------------------");

            }

        }

        catch(Exception e) {
            e.printStackTrace();
        }

    }

    // Approve Leave

    static void approveLeave() {

        try {

            System.out.print("Enter Leave ID : ");
            int id = sc.nextInt();

            String sql = "UPDATE employee_leave SET status='Approved' WHERE leave_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int row = ps.executeUpdate();

            if(row > 0)
                System.out.println("Leave Approved.");
            else
                System.out.println("Leave ID Not Found.");

        }

        catch(Exception e) {
            e.printStackTrace();
        }

    }

    // Reject Leave

    static void rejectLeave() {

        try {

            System.out.print("Enter Leave ID : ");
            int id = sc.nextInt();

            String sql = "UPDATE employee_leave SET status='Rejected' WHERE leave_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int row = ps.executeUpdate();

            if(row > 0)
                System.out.println("Leave Rejected.");
            else
                System.out.println("Leave ID Not Found.");

        }

        catch(Exception e) {
            e.printStackTrace();
        }

    }

    // Delete Leave

    static void deleteLeave() {

        try {

            System.out.print("Enter Leave ID : ");
            int id = sc.nextInt();

            String sql = "DELETE FROM employee_leave WHERE leave_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int row = ps.executeUpdate();

            if(row > 0)
                System.out.println("Leave Request Deleted.");
            else
                System.out.println("Leave ID Not Found.");

        }

        catch(Exception e) {
            e.printStackTrace();
        }

    }

}