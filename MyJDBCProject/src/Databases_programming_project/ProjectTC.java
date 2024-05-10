package Databases_programming_project;

import java.sql.*;
import java.util.Scanner; //TODO to be removed

import javax.swing.JOptionPane;

public class ProjectTC {
    public final static String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public final static String DB_URL = "jdbc:mysql://dif-mysql.ehu.es:23306/DBI10";
    public final static String DB_USERNAME = "DBI10";
    public final static String DB_PASSWORD = "Chineo1!";
    
    private static Connection connection;
    private static Statement statement;

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
		//Connection conn = null;
		Scanner input=new Scanner(System.in);
		Savepoint sv1=null;
		Connection conn = null;
        try {
            System.out.println("Welcome to this query application\n");

            int option = Integer.parseInt(JOptionPane.showInputDialog("Choose an option:\n1. Configure Database\n2. Revert all changes \n3. Execute queries:"));

            if (option == 1) {
                System.out.println("Connection to Tommaso's database:\n");
                conn = getConnection();
                connection = conn;
                conn.setAutoCommit(false);
                System.out.println("First let's make some configurations in the database:\n");
	            //INSERT in Dependent table
	            System.out.println("Inserting into dependent table without using savepoint:");
	            insertDependent(conn, "123456789", "Tom", "M", "1992-09-30","Son");
	            insertDependent(conn, "123456789", "Mark", "M", "1988-03-30","Uncle");
	            
	            conn.commit();
	            System.out.println("Transaction committed successfully.");
	            
	          //TODO to be removed, just for debugging purpose
	            System.out.println("DELETE?"); 
	            String dl=input.nextLine();
	            
	            if(dl.equalsIgnoreCase("yes")) {
	            	deleteDependent(conn, "123456789", "Tom");
	            	deleteDependent(conn, "123456789", "Mark");
	            	
	            	System.out.println("Delete committed successfully.");
	            	conn.commit();
	            }
	            
	            //INSERT into project table using SAVEPOINT
	           
	            System.out.println("Inserting into project table using savepoint:");
	            insertProject(conn, "ProductJ", 55, "New York", 5);
	            sv1=conn.setSavepoint();
	            insertProject(conn,"ProductT",22,"Rome",1);
	            insertProject(conn, "ProductFake", 59, "Los Angeles", 4);
	            conn.commit();
	            System.out.println("Transaction committed successfully.");
	            
	            //TODO MISSING THE UPDATE
	            // TODO the update in works on, change the project on 2 of the workers
	            
	            // TODO DELETE FROM PROJECT TABLE
	            System.out.println("DELETE:"); 
	        	deleteProject(conn, 55);
	        	deleteDependent(conn,"333445555","Alice");
	        	deleteDependent(conn,"333445555","Theodore");
	        	System.out.println("Delete committed successfully.");
	        	conn.commit();       
	            input.close(); 
            }else if(option==2){
            	try {
            	    conn = getConnection();
            	    conn.setAutoCommit(false);

            	    // Insert statements
            	    insertDependent(conn,"333445555","Alice","F","1986-04-05","Daughter");
            	    insertDependent(conn,"333445555","Theodore","M","1983-10-25","Son");
            	    	
            	    // Update statement
     // TODO with project in works on update(conn);

            	    // Delete statements
            	    deleteDependent(conn, "123456789", "Tom");
	            	deleteDependent(conn, "123456789", "Mark");
	            	deleteProject(conn,22);
	            	//deleteProject(conn,55);
	            	deleteProject(conn,59);
	            	
            	    conn.commit();
            	    System.out.println("Database changes applied successfully.");
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
            	}finally {
            	    if (conn != null) {
            	        try {
            	            conn.setAutoCommit(true); 
            	            conn.close();
            	            System.out.println("Connection closed successfully.");
            	        }catch (SQLException e) {
            	            e.printStackTrace();
            	        }
            	    }
            	}
            	
            }else if (option == 3) {
          	  // Execute queries
         	 System.out.println("Connection to Tommaso's database:\n");
              conn = getConnection();
              Querys(conn);
             
          }//TODO check for savepoint 
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    System.out.println("Rolling back transaction...");
                    if(sv1!=null) {
	                    conn.rollback(sv1);
	                    conn.commit();
                    }else{
                    	conn.rollback();
                    }
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
	
	
	// ADDING AND REMOVING SOME DATA
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
        String deleteSql = "DELETE FROM works_on WHERE Pno= ?";
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
	
	
	
	
	public static void Querys(Connection conn) {
        try {
            boolean flag= false;
            while (!flag) {
                statement = conn.createStatement();

                String query = JOptionPane.showInputDialog("Insert a number for a query(1-5, 0 EXIT).");
                int queryN = Integer.parseInt(query);

                if(queryN == 0) {
                    flag = true;
                }else if(queryN >= 1 && queryN <= 5) {
                    // Print the query being executed
                    System.out.println("Executing query " + queryN + ":\n");

                    switch (queryN) {
                        case 1:
                            System.out.println("Retrieve the trips whose price per day is greater than the price per day of ALL trip with destination ?:\n");
                            String city = JOptionPane.showInputDialog("Insert a city: ");
                            ResultSet result1 = statement.executeQuery(
                                    "select TripTo,DepartureDate from trip where Ppday> all(select Ppday from trip where TripTo='"+city+"');");
                            while (result1.next()) {
                                System.out.print(result1.getString("TripTo") + ", " + result1.getString("DepartureDate") + "\n");
                            }
                            break;
                        case 2:
                            System.out.println("For each department that has more than 2 employees, retrieve the Department number,\r\n"
                            		+ "Department name and the number of its employees who are making more than or equal to ?.:\n");
                            String number = JOptionPane.showInputDialog("Insert a Salary: ");
                            ResultSet result2 = statement.executeQuery(
                            	    "SELECT e.Dno,d.Dname,count(*) " +
                            	    "FROM employee AS e INNER JOIN department AS d ON e.Dno=d.Dnumber " +
                            	    "WHERE e.Salary >= " + number +" "+ 
                            	    "AND e.Dno IN ( " +
                            	    "    SELECT e2.Dno" +
                            	    "    FROM employee AS e2"+
                            	    " 	 GROUP BY e2.Dno " +
                            	    "    HAVING count(*) > 2" +
                            	    ") " +
                            	    "GROUP BY e.Dno ");
                            	while (result2.next()) {
                            	    System.out.print(result2.getString("Dno") +" , "+ result2.getString("Dname")+" , "+ result2.getString("count(*)")+ "\n");
                            	}
                            break;
                        case 3:
                            System.out.println("Find the restaurants that servers every dish:\n");
                            ResultSet result3 = statement.executeQuery(
                                    "SELECT DISTINCT s.restaurname "+
                                    "FROM serves AS s "
                            		+ "WHERE ("
                            		+ "		  NOT EXISTS ("
                            		+ "					  SELECT d.dish"
                            		+ "					  FROM dishes as d"
                            		+ "					  WHERE ("
                            		+ "							 NOT EXISTS ("
                            		+ "										 SELECT s1.restaurname"
                            		+ "									 	 FROM serves s1"
                            		+ "										 WHERE s1.restaurname=s.restaurname"
                            		+ "											AND s1.dish=d.dish"
                            		+ "										 )"
                            		+ "							 )"
                            		+ "					  )"
                            		+ "		  );");
                            while (result3.next()) {
                                System.out.print(result3.getString("restaurname") + "\n");
                            }
                            break;
                        case 4:
                            
                            System.out.println("Retrieve a list of project name and numbers for projects that involve an employee whose last name is '?':\n");
                            String lname = JOptionPane.showInputDialog("Insert a last name: ");
                            ResultSet result4 = statement.executeQuery(
                                "  SELECT DISTINCT p.Pname,p.Pnumber "
                                + "FROM ("
                                + "	project AS p INNER JOIN department AS d ON p.Dnum=d.Dnumber "
                                + "	) "
                                + "INNER JOIN employee AS e on d.Mgr_ssn=e.Ssn "
                                + "WHERE Lname ='"+lname+"' "
                                		+ "UNION "
                                + "SELECT DISTINCT p.Pname,p.Pnumber "
                                + "FROM ("
                                + "	project AS p INNER JOIN works_on AS w ON p.Pnumber=w.Pno "
                                + "	)"
                                + "INNER JOIN employee AS e ON w.Essn=e.Ssn "
                                + "WHERE Lname='"+lname+"';");
                            while (result4.next()) {
                                System.out.print(result4.getString("Pname") +" , "+ result4.getString("Pnumber") + "\n");
                            }
                            break;
                        case 5: //TODO missing fifth query
                            /*
                            ResultSet result5 = statement.executeQuery(
                                    );
                            while (result5.next()) {
                                System.out.print();
                            }
                            break;
                        default:
                            System.out.println("ERROR. Invalid query.\n");
                            break;*/
                    }

                    // Print statement for query completion
                    System.out.println("Query " + queryN + " executed successfully.\n");
                } else {
                    System.out.println("ERROR. It must be a number from 0 to 5.\n");
                }

                statement.close();
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            System.out.println("ERROR. Connection failed!\n");
        }
    }
}
