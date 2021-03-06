import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;


public class Bricks_Management {

	public static void main(String[] args) throws Exception {
		createTable();
		menu();
	}
	
	public static void menu() throws Exception{
		System.out.println("\nMenu");
		System.out.println("1 -> Create Order");
		System.out.println("2 -> Retrieve Order");
		System.out.println("3 -> Update Order");
		System.out.println("4 -> Dispatch Order");
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String s = br.readLine();
			int menu_item = Integer.parseInt(s);
			if(menu_item == 1 || menu_item == 2 || menu_item == 3 || menu_item == 4){
				switch(menu_item){
					case 1:
						create_order();
						break;
					case 2:
						retrieve_order();
						break;
					case 3:
						update_order();
						break;
					case 4:
						dispatch_order();
						break;
				}
			}else{
				System.out.println("\nPlease select a choice from the menu\n");
				menu();
			}
		}catch(Exception e){
			System.out.println("\nInvalid Data\n");
			menu();
		}
	}
	
	public static void dispatch_order() throws Exception{
		try{
			System.out.println("Order Reference Number to dispatch: ");
			Scanner scanner1 = new Scanner(System.in);
			String ref_num = scanner1.nextLine();
			int ref_num_int = Integer.parseInt(ref_num);
			
			if(ref_num_int > 0){
				Connection con = getConnection();
				PreparedStatement retrieve = con.prepareStatement("SELECT Order_Reference_Number,Dispatched, Dispatched_Date FROM Orders where Order_Reference_Number ="+ref_num_int);
				ResultSet result = retrieve.executeQuery();
	
				if(result.next()){
					String reference = result.getString(1);
					String Dispatched = result.getString(2);
					String Dispatched_Date = result.getString(3);
					if(Dispatched.equals("NO")){
						String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
						PreparedStatement set_dispatched = con.prepareStatement("UPDATE Orders SET Dispatched = 'YES', Dispatched_Date = '"+timeStamp+"' where Order_Reference_Number ="+reference);
						set_dispatched.executeUpdate();
						System.out.println("\nUpdate is Complete");
					}else if (Dispatched.equals("YES")){
						System.out.println("\n400 bad request response. Order with Reference Number: "+reference+" has already been dispatched on: "+Dispatched_Date);
					}
				}else{
					System.out.println("\nReference Number not found.");
				}
				con.close();
			}else{
				System.out.println("\nInvalid Reference Number.");
			}
		}catch (Exception e){
			System.out.println("\nInvalid Reference Number.");
		}
		menu();
	}
	
	public static void update_order() throws Exception{
		try{
			System.out.println("Order Reference Number: ");
			Scanner scanner1 = new Scanner(System.in);
			int ref_num = Integer.parseInt(scanner1.nextLine());
		
			if(ref_num > 0){
				Connection con = getConnection();
				PreparedStatement retrieve = con.prepareStatement("SELECT Order_Reference_Number,Number_of_Bricks,Dispatched, Dispatched_Date FROM Orders where Order_Reference_Number ="+ref_num);
				ResultSet result = retrieve.executeQuery();
	
				if(result.next()){
					String reference = result.getString(1);
					String number_of_bricks = result.getString(2);
					String dispatched = result.getString(3);
					String dispatched_date = result.getString(4);
					if(dispatched.equals("NO")){
						System.out.println("Order with Reference Number: "+reference+" has "+number_of_bricks+" bricks.");
						System.out.println("New number of bricks: ");
						Scanner scanner2 = new Scanner(System.in);
						String bricks_num = scanner2.nextLine();
						int bricks_num_int = Integer.parseInt(bricks_num);
						if(bricks_num_int > 0){
							PreparedStatement update = con.prepareStatement("UPDATE Orders set Number_of_Bricks = "+bricks_num+" where Order_Reference_Number ="+reference);
							update.executeUpdate();
							System.out.println("\nUpdate is Complete.");
						}else{
							System.out.println("Invalid Number of Bricks");
						}
					}else if(dispatched.equals("YES")){
							System.out.println("400 bad request response. Order with Reference Number: "+reference+" has been dispatched on: "+dispatched_date);
					}
				}else{
					System.out.println("\nReference Number not found.");
				}
				con.close();
			}else{
				System.out.println("\nInvalid Reference Number.");
			}
		}catch (Exception e){
			System.out.println("\nInvalid Reference Number.");
		}
		menu();
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
		menu();
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
			
			if(number_of_bricks > 0){
				//making insert query
				Connection con = getConnection();
				PreparedStatement insert = con.prepareStatement("INSERT INTO Orders(Customer_Surname,Customer_Name,Number_of_Bricks,Dispatched,Dispatched_Date) VALUES ('"+customers_surname+"','"+customers_name+"','"+number_of_bricks+"','NO','')");
				insert.executeUpdate();
				con.close();
				System.out.println("\nOrder created");
			}else{
				System.out.println("\nInvalid Number of Bricks");
			}
		}catch(Exception e){
			System.out.println("Cannot create order. Invalid data.");
		}
		menu();
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

