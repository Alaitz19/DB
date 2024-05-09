package Dbtransactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import java.sql.CallableStatement;

public class TransactionExampleTC {

	/**
	 * Get all rows from the employee
	 */
	public static void getEmployee() {
		System.out.println(" ===> Get the Content of the table employee <===");
		String querysql = "SELECT * " + "FROM EMPLOYEE";
		Connection conn = null;
		try {
			conn = MyDBConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(querysql);
			// loop through the result set
			System.out.println("     => Printing the content ");
			while (rs.next()) {
				System.out.format("%-15s %-5s %-15s %-15s %-15s %-25s %-5s %-10s %-15s %-5s%n",
						rs.getString("Fname"),rs.getString("Minit"), rs.getString("Lname"),rs.getString("Ssn"), rs.getString("Bdate"),rs.getString("Address"),rs.getString("Sex"),rs.getString("Salary"),rs.getString("Super_Ssn"),rs.getString("Dno"));

			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private static String INSERTemployee = "INSERT INTO DBI10.EMPLOYEE " // change to your account
			+ "(Fname,Minit,Lname,Ssn,Bdate,Address,Sex,Salary,Super_ssn,Dno) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static void insertRowEmployee(Connection conn, String Fname,String Minit,String Lname,int Ssn,String Bdate,String Address,String Sex,double Salary,int Super_ssn,int Dno)
			throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(INSERTemployee);
		pstmt.setString(1, Fname);
		pstmt.setString(2, Minit);
		pstmt.setString(3, Lname);
		pstmt.setInt(4, Ssn);
		pstmt.setString(5, Bdate);
		pstmt.setString(6, Address);
		pstmt.setString(7, Sex);
		pstmt.setDouble(8, Salary);
		pstmt.setInt(9, Super_ssn);
		pstmt.setInt(10, Dno);
		pstmt.execute();
		pstmt.close();
	}
	public static void exampleTransaction() {
		Connection conn = null;
		Savepoint savepoint1 = null;
		try {
			conn = MyDBConnection.getConnection();
			// disable Autocommit
			conn.setAutoCommit(false);
			insertRowEmployee(conn, "Claudia", "R","De Carlo",555555555, "2002-09-19","corso del popolo","F", 5945,68696, 4);
			//insertRowEmployee(conn, "51", "Leire", 66678);
			//insertRowDepartment(conn, "Marketing", 2, "453453453", "2002-05-22");
			// if code reached here, means main work is done successfully
			savepoint1 = conn.setSavepoint("savedfirst2");
			//insertRowGuide(conn, "52", "Stella", 66679);
			//insertRowGuide(conn, "52", "Stella", 66679);
			// now commit transaction
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if (savepoint1 == null) {
					System.out.println("JDBC WHOLE Transaction rolled back successfully");
					// SQLException occurred when inserting first 2 insertRowGuide
					conn.rollback();
				} else {
					// exception occurred after savepoint
					// we can ignore it by rollback to the savepoint
					System.out.println("Exception after savepoint1. roll back successfully");
					conn.rollback(savepoint1);
					// lets commit now
					conn.commit();
				}
			} catch (SQLException e1) {
				System.out.println("SQLException in rollback" + e.getMessage());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}

	
	
	public static void main(String[] args) {
		getEmployee();
		exampleTransaction();
		getEmployee();
	}

}
