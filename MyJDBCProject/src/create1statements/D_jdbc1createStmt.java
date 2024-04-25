package create1statements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;	

public class D_jdbc1createStmt {

	public static void main(String[] args) throws SQLException {

		Connection conn = null;
		Statement myfirststmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement pstamt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://dif-mysql.ehu.es:23306/DBI51", "DBI51", "DBI51"); 
			System.out.println("connection Establishedd!!!");
			
			myfirststmt = conn.createStatement();
			rs2 = pstamt.executeQuery("select ? from employee");
			rs = myfirststmt.executeQuery("select * from employee;");
			// "select * from employee;"

			while (rs.next()) {
				String partid = rs.getString("Ssn");
				String partname = rs.getString("salary");
				String partcolor = rs.getString("dno");

				System.out
						.println("Employee ssn: " + partid + "  Employee Salary: " + partname + "\t\t  Employee departnumber: " + partcolor);
			}

		} catch (SQLException ex) {
			System.out.println("SQLexception   " + ex.getMessage());
			System.out.println("SQLstate   " + ex.getSQLState());
			System.out.println("Error   " + ex.getErrorCode());
			System.out.println(" oooo  Connection failed!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {

				if (rs != null)
					rs.close();
				if (myfirststmt != null)
					myfirststmt.close();
				if (conn != null)
					conn.close();

				System.out.println(" --> Connection Closed!");
			} catch (SQLException ex) {

				System.out.println(" Closing Connection Went Wrong!");
			}

		}

	}

}
