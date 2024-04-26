package Dbtransactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import java.sql.CallableStatement;

public class QueriesExamples {

	/**
	 * Get all rows from the tourguides
	 */
	public static void getTableGuides() {
		System.out.println(" ===> Get the Content of the table Tourguide <===");
		String querysql = "SELECT * " + "FROM tourguide";
		Connection conn = null;
		try {
			conn = MyDBConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(querysql);
			// loop through the result set
			System.out.println("     => Printing the content ");
			while (rs.next()) {
				System.out.println(
						rs.getString("GuideId") + "\t" + rs.getString("guidename") + "\t" + rs.getString("guidephone"));
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

	private static String INSERTtourguide = "INSERT INTO DBI51.tourguide " // change to your account
			+ "(GuideId, guidename, guidephone) VALUES (?, ?, ?)";

	public static void insertRowGuide(Connection conn, String idRow, String content1, int content2)
			throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(INSERTtourguide);
		pstmt.setString(1, idRow);
		pstmt.setString(2, content1);
		pstmt.setInt(3, content2);
		pstmt.execute();
		pstmt.close();
	}

	// @SuppressWarnings("unused")
	public static void exampleTransaction() {
		Connection conn = null;
		Savepoint savepoint1 = null;
		try {
			conn = MyDBConnection.getConnection();
			// disable Autocommit
			conn.setAutoCommit(false);
			insertRowGuide(conn, "50", "Daniela", 6667);
			insertRowGuide(conn, "51", "Leire", 66678);
			// if code reached here, means main work is done successfully
			savepoint1 = conn.setSavepoint("savedfirst2");
			insertRowGuide(conn, "52", "Stella", 66679);
			insertRowGuide(conn, "52", "Stella", 66679);
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


	/**
	 *
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) {
		exampleTransaction();
		getTableGuides();

	}
}
