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
		String querysql = "SELECT * " + "FROM employee";
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

	
	
	public static void main(String[] args) {
		getEmployee();

	}

}
