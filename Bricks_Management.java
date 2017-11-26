import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class Bricks_Management {

	public static void main(String[] args) throws Exception {
		createTable();

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
