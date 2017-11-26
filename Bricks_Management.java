import java.sql.Connection;
import java.sql.DriverManager;


public class Bricks_Management {

	public static void main(String[] args) throws Exception {
		getConnection();

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