import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class Bricks_Management {

	public static void main(String[] args) throws Exception {
		createTable();
		//create_order();
		//retrieve_order();
		update_order();

	}
	
	public static void update_order() throws Exception{
		try{
			System.out.println("Order Reference Number: ");
			Scanner scanner1 = new Scanner(System.in);
			int ref_num = Integer.parseInt(scanner1.nextLine());
		
			if(ref_num > 0){
				Connection con = getConnection();
				PreparedStatement retrieve = con.prepareStatement("SELECT Order_Reference_Number,Number_of_Bricks FROM Orders where Order_Reference_Number ="+ref_num);
				ResultSet result = retrieve.executeQuery();
	
				if(result.next()){
					String reference = result.getString(1);
					String number_of_bricks = result.getString(2);
					System.out.println("Order with Reference Number: "+reference+" has "+number_of_bricks+" bricks.");
					System.out.println("New number of bricks: ");
					Scanner scanner2 = new Scanner(System.in);
					String bricks_num = scanner2.nextLine();
						
					PreparedStatement update = con.prepareStatement("UPDATE Orders set Number_of_Bricks = "+bricks_num+" where Order_Reference_Number ="+reference);
					update.executeUpdate();
					System.out.println("\nUpdate is Complete.");
				}else{
					System.out.println("\nReference Number not found.");
				}
				con.close();
			}else{
				System.out.println("\nInvalid number.");
			}
		}catch (Exception e){
			System.out.println("\nInvalid Reference Number.");
		}
	}
	
	public static void retrieve_order() throws Exception{
		try{
			System.out.println("How many orders to retrieve: \n");
			Scanner scanner1 = new Scanner(System.in);
			String num_of_orders = scanner1.nextLine();
			int num_of_orders_int = Integer.parseInt(num_of_orders);
			if(num_of_orders_int > 0){
				for(int i = 0; i < num_of_orders_int; i++){
					System.out.println("Reference Number: \n");
					Scanner scanner2 = new Scanner(System.in);
					String reference_number = scanner2.nextLine();
				
					Connection con = getConnection();
					PreparedStatement retrieve = con.prepareStatement("SELECT Order_Reference_Number,Number_of_Bricks FROM Orders where Order_Reference_Number ="+reference_number);
					ResultSet result = retrieve.executeQuery();
			
					if(result.next()){
						String reference = result.getString(1);
						String number_of_bricks = result.getString(2);
						System.out.println("Reference Number: "+reference+" Number of Bricks: "+number_of_bricks);
					}else{
						System.out.println("Reference Number not found.");
					}
					con.close();
				}
			}else{
				System.out.println("Invalid number.");
			}
		}catch (Exception e){
			System.out.println("Invalid Data.");
		}
	}
	
	
	public static void create_order() throws Exception{
		try{
			//getting order's info
			System.out.println("Customer's Surname: ");
			Scanner scanner = new Scanner(System.in);
			String customers_surname = scanner.nextLine();
			System.out.println("Customer's Name: ");
			String customers_name = scanner.nextLine();
			System.out.println("Number of Bricks: ");
			int number_of_bricks = Integer.parseInt(scanner.nextLine());
			
			//making insert query
			Connection con = getConnection();
			PreparedStatement insert = con.prepareStatement("INSERT INTO Orders(Customer_Surname,Customer_Name,Number_of_Bricks,Dispatched,Dispatched_Date) VALUES ('"+customers_surname+"','"+customers_name+"','"+number_of_bricks+"','NO','')");
			insert.executeUpdate();
			con.close();
			System.out.println("\nOrder created");
		}catch(Exception e){
			System.out.println("Cannot create order. Invalid data.");
		}
	}

	public static void createTable() throws Exception{
		try{
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS Orders (Order_Reference_Number INT NOT NULL AUTO_INCREMENT,Customer_Surname varchar(20) NOT NULL,Customer_Name varchar(20) NOT NULL,Number_of_Bricks INT NOT NULL,Dispatched varchar(3),Dispatched_Date varchar(20),PRIMARY KEY (Order_Reference_Number))");
			create.executeUpdate();
			con.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	
	public static Connection getConnection() throws Exception{
		//credentials
		try{
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3307/CRM_DB";
			String username = "root";
			String password = "";
			Class.forName(driver);
			//establish the connection
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		}catch(Exception e){
			System.out.println(e);
		}
		return null;
	}
}

