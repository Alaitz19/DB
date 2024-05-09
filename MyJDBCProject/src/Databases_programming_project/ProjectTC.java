package Databases_programming_project;

import java.sql.*;
import java.util.Scanner; //TODO to be removed

public class ProjectTC {
    public final static String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public final static String DB_URL = "jdbc:mysql://dif-mysql.ehu.es:23306/DBI10";
    public final static String DB_USERNAME = "DBI10";
    public final static String DB_PASSWORD = "Chineo1!";

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
	public static void main(String[] args) {
		Connection conn = null;
        try {
            System.out.println("Welcome to this query application\n");
            System.out.println("Conection to Tommaso's database:\n");
            conn = getConnection();
            conn.setAutoCommit(false);
            System.out.println("First let's make some configurations in the database:\n");

            // Insert into dependent table, it allows me to insert the same rows more the once TODO must check
            System.out.println("Inserting into dependent table without using savepoint:");
            insertDependent(conn, "123456789", "Tom", "M", "2010-09-30","Father");
            conn.commit();
            System.out.println("Transaction committed successfully.");
            
          //TODO to be removed, just for debugging purpose
            System.out.println("DELETE?"); 
            Scanner input=new Scanner(System.in);
            String dl=input.nextLine();
            
            if(dl.equalsIgnoreCase("yes")) {
            	deleteDependent(conn, "123456789", "Tom");
            	System.out.println("Delete committed successfully.");
            	conn.commit();
            }
            
            //INSERT into PROJECT table
            System.out.println("Inserting into project table without using savepoint:");
            insertProject(conn, "progetto strano", 55, "Padova", 5);
            conn.commit();
            System.out.println("Transaction committed successfully.");
          
            //TODO to be removed, just for debugging purpose
            System.out.println("DELETE?"); 
            String d=input.nextLine();
            
            if(d.equalsIgnoreCase("yes")) {
            	deleteProject(conn, 55);
            	System.out.println("Delete committed successfully.");
            	conn.commit();
            }
            
            
            
            
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    System.out.println("Rolling back transaction...");
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Error rolling back transaction: " + ex.getMessage());
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection closed successfully.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


	}
	private static void insertDependent(Connection conn, String Essn, String Dependent_name, String Sex, String Bdate, String Relationship) throws SQLException {
        String sql = "INSERT INTO dependent (Essn, Dependent_name, Sex, Bdate, Relationship) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Essn);
            pstmt.setString(2, Dependent_name);
            pstmt.setString(3, Sex);
            pstmt.setString(4, Bdate);
            pstmt.setString(5 , Relationship);
            pstmt.executeUpdate();
            System.out.println("Inserted row into dependent table: " + Essn + " "+ Dependent_name );
        }
    }
	private static void deleteDependent(Connection conn, String Essn,String Dependent_name) throws SQLException {
	        String deleteDpnd = "DELETE FROM dependent WHERE (Essn = ? and Dependent_name= ?)";
	        try (PreparedStatement pstmt = conn.prepareStatement(deleteDpnd)) {
	        	pstmt.setString(1, Essn);
	        	pstmt.setString(2, Dependent_name);
	            int rowsAffected = pstmt.executeUpdate();
	            System.out.println("Deleted " + rowsAffected + " dependent(s) from dependent table.");
	        }
	    }
	private static void insertProject(Connection conn, String Pname, int Pnumber, String Plocation, int Dnum ) throws SQLException {
        String sql = "INSERT INTO project (Pname, Pnumber, Plocation,Dnum) VALUES (?, ?, ?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Pname);
            pstmt.setInt(2, Pnumber);
            pstmt.setString(3,Plocation);
            pstmt.setInt(4, Dnum);
            pstmt.executeUpdate();
            System.out.println("Inserted row into Project table: " + Pnumber );
        }
    }
	private static void deleteProject(Connection conn, int Pnumber) throws SQLException {
		//have to remove from WORKS_ON table first
        String deleteSql = "DELETE FROM works_on WHERE Pnumber = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
            pstmt.setInt(1, Pnumber);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " Worker(s) from the works_on table.");
        }    
		
		String delete = "DELETE FROM project WHERE Pnumber = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(delete)) {
        	pstmt.setInt(1, Pnumber);
        	int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " project(s) from project table.");
        }
    }

}
