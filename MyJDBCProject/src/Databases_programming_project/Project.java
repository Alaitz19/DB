package Databases_programming_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Project {
	public final static String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	public final static String DB_URL = "jdbc:mysql://dif-mysql.ehu.es:23306/DBI51";

	public final static String DB_USERNAME = "DBI51";
	public final static String DB_PASSWORD = "DBI51";
	
	

	//  static method 
	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		Connection con = null;
		System.out.println("==> Starting the connection <==");
		// load the Driver Class
		Class.forName(DB_DRIVER_CLASS);
		System.out.println("  ==> Driver loaded <==");
		// create the connection now
		con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

		System.out.println("  ==> DB Connection created successfully");
		return con;
		
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
	System.out.println("Welcome to this query application\n");
	System.out.println("Conection to the database:\n");
	getConnection();
	System.out.println("First let's make some configurations in the database:\n");
	}
}
