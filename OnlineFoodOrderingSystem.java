package jdbc_task;

import java.sql.*;
import java.util.Scanner;

public class OnlineFoodOrderingSystem {

    static final String URL = "jdbc:mysql://localhost:3306/online_food_ordering";
    static final String USER = "root";
    static final String PASSWORD = "123456789";

    static Connection con;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            int ch;

            do {

            System.out.println("\n===== ONLINE FOOD ORDERING =====");

            System.out.println("1.Register Customer");
            System.out.println("2.View Customer");
            System.out.println("3.View Menu");
            System.out.println("4.Place Order");
            System.out.println("5.View Orders");
            System.out.println("6.Update Customer");
            System.out.println("7.Delete Customer");
            System.out.println("8.Exit");

            System.out.print("Enter Choice : ");

            ch = sc.nextInt();

            switch(ch){

                case 1:
                    registerCustomer();
                    break;
                    
                case 2:
                    viewCustomer();
                    break;

                case 3:
                    viewMenu();
                    break;

                case 4:
                    placeOrder();
                    break;

                case 5:
                    viewOrders();
                    break;

                case 6:
                    updateCustomer();
                    break;

                case 7:
                    deleteCustomer();
                    break;

                case 8:
                	System.out.println("Thank You");
                    break;

                default:
                    System.out.println("Invalid Choice");

            }

            } while(ch != 7);

            con.close();

        }

        catch(Exception e) {
            e.printStackTrace();
        }

    }

    static void registerCustomer(){

        try{

            sc.nextLine();

            System.out.print("Customer Name : ");

            String name=sc.nextLine();

            System.out.print("Phone : ");

            String phone=sc.nextLine();

            PreparedStatement ps=con.prepareStatement(
            "insert into customers(customer_name,phone) values(?,?)");

            ps.setString(1,name);

            ps.setString(2,phone);

            ps.executeUpdate();

            System.out.println("Customer Registered");

        }

        catch(Exception e){

            System.out.println(e);

        }

    }
    
    static void viewCustomer(){
    	 try{

    Statement st = con.createStatement();

    ResultSet rs = st.executeQuery("SELECT * FROM customers");
    
    System.out.println("\nID\tName\tPhone");
    System.out.println("-------------------------------");

    while(rs.next()) {
        System.out.println(
            rs.getInt("customer_id") + "\t" +
            rs.getString("customer_name") + "\t" +
            rs.getString("phone"));
    }
    	 }
        catch(Exception e){

            System.out.println(e);

        }

    }
    
    static void viewMenu(){

        try{

            Statement st=con.createStatement();

            ResultSet rs=st.executeQuery("select * from menu");

            System.out.println("\nID\tItem\tPrice");

            while(rs.next()){

                System.out.println(rs.getInt(1)+"\t"+
                rs.getString(2)+"\t"+
                rs.getDouble(3));

            }

        }

        catch(Exception e){

            System.out.println(e);

        }

    }

    static void placeOrder() {

        try {

            con.setAutoCommit(false);

            System.out.print("Customer ID : ");
            int cid = sc.nextInt();

            // Check Customer
            PreparedStatement checkCustomer = con.prepareStatement(
                    "SELECT * FROM customers WHERE customer_id=?");
            checkCustomer.setInt(1, cid);

            ResultSet crs = checkCustomer.executeQuery();

            if (!crs.next()) {
                System.out.println("Customer ID not found.");
                con.rollback();
                con.setAutoCommit(true);
                return;
            }

            PreparedStatement order = con.prepareStatement(
                    "INSERT INTO orders(customer_id,order_date,total) VALUES(?,CURDATE(),?)",
                    Statement.RETURN_GENERATED_KEYS);

            order.setInt(1, cid);
            order.setDouble(2, 0);

            order.executeUpdate();

            ResultSet rs = order.getGeneratedKeys();
            rs.next();

            int orderId = rs.getInt(1);

            double total = 0;

            while (true) {

                viewMenu();

                System.out.print("Enter Item ID : ");
                int item = sc.nextInt();

                System.out.print("Enter Quantity : ");
                int qty = sc.nextInt();

                PreparedStatement menu = con.prepareStatement(
                        "SELECT price FROM menu WHERE item_id=?");

                menu.setInt(1, item);

                ResultSet r = menu.executeQuery();

                if (r.next()) {

                    double price = r.getDouble("price");
                    double amount = price * qty;

                    total += amount;

                    PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO order_items(order_id,item_id,quantity,amount) VALUES(?,?,?,?)");

                    ps.setInt(1, orderId);
                    ps.setInt(2, item);
                    ps.setInt(3, qty);
                    ps.setDouble(4, amount);

                    ps.executeUpdate();
                    ps.close();

                } else {
                    System.out.println("Invalid Item ID");
                }

                System.out.print("Add More Items (Y/N): ");
                char c = sc.next().charAt(0);

                if (c == 'N' || c == 'n')
                    break;
            }

            PreparedStatement update = con.prepareStatement(
                    "UPDATE orders SET total=? WHERE order_id=?");

            update.setDouble(1, total);
            update.setInt(2, orderId);

            update.executeUpdate();

            con.commit();
            con.setAutoCommit(true);

            System.out.println("\nOrder Placed Successfully");
            System.out.println("Total Amount = " + total);

        } catch (Exception e) {

            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
        }
    }
    
    static void viewOrders(){

        try{

            String sql="select o.order_id,c.customer_name,o.order_date,o.total "+
            "from orders o join customers c "+
            "on o.customer_id=c.customer_id";

            Statement st=con.createStatement();

            ResultSet rs=st.executeQuery(sql);

            System.out.println("\nOrderID\tCustomer\tDate\t\tTotal");

            while(rs.next()){

                System.out.println(
                rs.getInt(1)+"\t"+
                rs.getString(2)+"\t"+
                rs.getDate(3)+"\t"+
                rs.getDouble(4));

            }

        }

        catch(Exception e){

            System.out.println(e);

        }

    }

    static void updateCustomer(){

        try{

            System.out.print("Customer ID : ");

            int id=sc.nextInt();

            sc.nextLine();

            System.out.print("New Name : ");

            String name=sc.nextLine();

            PreparedStatement ps=con.prepareStatement(
            "update customers set customer_name=? where customer_id=?");

            ps.setString(1,name);

            ps.setInt(2,id);

            ps.executeUpdate();

            System.out.println("Customer Updated");

        }

        catch(Exception e){

            System.out.println(e);

        }

    }

    static void deleteCustomer() {

        try {

            System.out.print("Customer ID : ");
            int id = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM customers WHERE customer_id=?");

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Customer Deleted Successfully.");
            else
                System.out.println("Customer ID Not Found.");

        } catch (SQLIntegrityConstraintViolationException e) {

            System.out.println("Customer has already placed orders. Cannot delete.");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}