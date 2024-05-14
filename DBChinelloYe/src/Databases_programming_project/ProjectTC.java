package Databases_programming_project;

import java.sql.*;

import javax.swing.JOptionPane;

public class ProjectTC {
    public final static String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public final static String DB_URL = "jdbc:mysql://dif-mysql.ehu.es:23306/DBI10";
    public final static String DB_USERNAME = "DBI10";
    public final static String DB_PASSWORD = "Chineo1!";
    // Tommaso Chinello's Database
    /**
     * Establishes a connection to the MySQL database.
     * @return Connection object representing the database connection.
     * @throws ClassNotFoundException If the database driver class is not found.
     * @throws SQLException If an SQL exception occurs while establishing the connection.
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        System.out.println("==> Starting the connection <==");
        Class.forName(DB_DRIVER_CLASS);
        System.out.println("  ==> Driver loaded <==");
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
    /**
     * Main method to execute the database interaction application.
     */
	public static void main(String[] args) {
		Connection conn = null;
        try {
            System.out.println("Welcome to this query application\n");

            int option = Integer.parseInt(JOptionPane.showInputDialog("Choose an option:\n1. Configure Database\n2. Revert all changes \n3. Execute queries:"));

            switch (option) {
	            case 1:
	            	conn = getConnection();
	                configureDatabase(conn);
	                break;        
	            case 2:
	            	conn = getConnection();
                    revertChanges(conn);
                    break;
	            case 3:
	            	// Execute queries
	            	conn = getConnection();
	            	Querys(conn);
	            	break;
	            default:
	            	System.out.println("Invalid option selected.");
            }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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
	
	/**
     * Configures the database by performing various transactions.
     * @param conn Connection object representing the database connection.
     * @throws SQLException If an SQL exception occurs during database configuration.
     */
    private static void configureDatabase(Connection conn) throws SQLException {
    	Savepoint savepoint1=null;
    	try {
	        conn.setAutoCommit(false);
	        System.out.println("First let's make some configurations in the database:\n");
	        System.out.println("\n\n");
	        
	        //INSERT into Dependent table
	        System.out.println("Inserting into dependent table without using savepoint:");
	        getDependentTable(conn);
	        System.out.println("\n");
	        insertDependent(conn, "123456789", "Tom", "M", "1992-09-30","Son");
	        insertDependent(conn, "123456789", "Mark", "M", "1988-03-30","Uncle");
	        System.out.println("\n");
	        getDependentTable(conn);
	        conn.commit();
	        System.out.println("Transaction committed successfully.");
	        System.out.println("\n\n");
	        
	        // INSERT into project table using SAVEPOINT
	        System.out.println("Inserting into project table using savepoint:");
	        getProjectTable(conn);
	        System.out.println("\n");
	        insertProject(conn, "ProductJ", 55, "New York", 5);
	        savepoint1 = conn.setSavepoint("savedfirst1"); // savepoint
	        insertProject(conn,"ProductT",22,"Rome",1);
	        insertProject(conn, "ProductFake", 59, "Los Angeles", 4);
	        getProjectTable(conn);
	        conn.commit();
	        System.out.println("Transaction committed successfully.");
	        System.out.println("\n\n");
	        
	       // UPDATE table works_on
	        System.out.println("Updating works-on table by changing the number of the project to a worker:");
	        getWorks_onTable(conn);
	        System.out.println("\n");
	        updateWorkson(conn);
	        getWorks_onTable(conn);
	        System.out.println("Update commited successfully.");
	        System.out.println("\n\n");
	        
	        // DELETE from tables
	        System.out.println("Delete a project and some dependents:"); 
	        System.out.println("If we delete a project, we delete also the workers that work on that project,\n so it will be shown also the works_on table: ");
	        getProjectTable(conn);
	        System.out.println("\n");
	        getWorks_onTable(conn);
	        System.out.println("\n");
	        System.out.println("We will delete also some Dependent");
	        getDependentTable(conn);
	        System.out.println("\n");
	        deleteProject(conn, 1);
	    	deleteDependent(conn,"333445555","Alice");
	    	deleteDependent(conn,"333445555","Theodore");
	    	System.out.println("\n");
	    	getProjectTable(conn);
	    	System.out.println("\n");
	    	getWorks_onTable(conn);
	    	System.out.println("\n");
	    	getDependentTable(conn);
	    	System.out.println("Delete committed successfully.");
	    	conn.commit();        
    	} catch (SQLException e) {
            e.printStackTrace();
            if (conn != null && savepoint1 != null) {
                System.out.println("Rolling back transaction...");
                conn.rollback(savepoint1);
            }
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
                System.out.println("Connection closed successfully.");
            }
        }
    }
    /**
     * Reverts the changes made to the database.
     * @param conn Connection object representing the database connection.
     * @throws SQLException If an SQL exception occurs during the rollback process.
     */
    private static void revertChanges(Connection conn) throws SQLException {
	    conn.setAutoCommit(false);
	    Savepoint savepoint1 = null;
		try {
			savepoint1 = conn.setSavepoint("savedfirst1");
			
		    // Insert statements
		    insertDependent(conn,"333445555","Alice","F","1986-04-05","Daughter");
		    insertDependent(conn,"333445555","Theodore","M","1983-10-25","Son");
		    insertProject(conn,"ProductX",1,"Bellaire",5);
		    insertWorker(conn,"123456789",1,"32.5");
		    insertWorker(conn,"453453453",1,"20.0");
		    
		    // Update statement
		    // The update was done on a row that was deleted, so we insert the row with the correct project number
		    insertWorker(conn,"666884444",3,"40.0");
	
		    // Delete statements
		    deleteDependent(conn, "123456789", "Tom");
	    	deleteDependent(conn, "123456789", "Mark");
	    	deleteProject(conn,22);
	    	deleteProject(conn,55);
	    	deleteProject(conn,59);
	    	System.out.println("\n\n");
	    	System.out.println("This is the unchanged table that were used in the transactions: ");
	    	getProjectTable(conn);
	    	System.out.println("\n");
	        getWorks_onTable(conn);
	        System.out.println("\n");
	        getDependentTable(conn);
	        System.out.println("\n");
	    	
		    conn.commit();
		    System.out.println("Database changes applied successfully.");
			} catch (SQLException e) {
		    e.printStackTrace();
		    if (conn != null && savepoint1 != null) {
                System.out.println("Rolling back transaction...");
                conn.rollback(savepoint1);
            }
        }finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
                System.out.println("Connection closed successfully.");
            }
        }
		
    }
	

	/**
     * Executes SQL queries interactively based on user input.
     * @param conn Connection object representing the database connection.
     */
	public static void Querys(Connection conn) {
        try {
            boolean flag= false;
            while (!flag) {
            	Statement statement = conn.createStatement();

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
                            
                            System.out.println("Retrieve a list of project's names and numbers for projects that involve an employee whose last name is '?':\n");
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
                        case 5:
                        	System.out.println("Retrieve the names, the ids and the phone numbers of those customers that are staying in a hotel in the city of ? and order them by name:\n");
                            String city5 = JOptionPane.showInputDialog("Insert a city: ");
                            
                            ResultSet result5 = statement.executeQuery(""
                            		+ "SELECT c.custname,htc.CustomerId, c.custphone "
                            		+ "FROM hotel_trip_customer AS htc NATURAL JOIN customer AS c "
                            		+ "	INNER JOIN hotel AS h ON htc.HotelId=h.HotelId "
                            		+ "WHERE h.hotelcity='"+city5+"' "
                            		+ "ORDER BY c.custname;"
                                    );
                            while (result5.next()) {
                            	System.out.print(result5.getString("custname") +" , "+ result5.getString("CustomerId") +" , "+ result5.getString("custphone") + "\n");
                            }
                            break;
                        default:
                            System.out.println("ERROR. Invalid query.\n");
                            break;
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
	// Function to add, remove and update data: 
	
	 /**
     * Inserts a dependent record into the database.
     * @param conn Connection object representing the database connection.
     * @param Essn Employee's Ssn.
     * @param Dependent_name Dependent name.
     * @param Sex Dependent Sex.
     * @param Bdate Dependent's Birth date.
     * @param Relationship Dependent's relationship with employee.
     * @throws SQLException If an SQL exception occurs during the insertion process.
     */
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
	
	
	/**
     * Deletes a dependent record from the database.
     * @param conn Connection object representing the database connection.
     * @param Essn one of the 2 keys to identify one dependent.
     * @param Dependent_name one of the 2 keys to identify one dependent.
     * @throws SQLException If an SQL exception occurs during the deletion process.
     */
	private static void deleteDependent(Connection conn, String Essn,String Dependent_name) throws SQLException {
        String deleteDpnd = "DELETE FROM dependent WHERE (Essn = ? and Dependent_name= ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteDpnd)) {
        	pstmt.setString(1, Essn);
        	pstmt.setString(2, Dependent_name);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " dependent(s) from dependent table.");
        }
    }
	
	/**
     * Updates a Worker record in the database.
     * @param conn Connection object representing the database connection.
     * @throws SQLException If an SQL exception occurs during the update process.
     */
	private static void updateWorkson(Connection conn) throws SQLException {
        String updateSql = "UPDATE works_on SET Pno =1 WHERE Essn = 666884444 AND Pno =3";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated " + rowsAffected + " row(s) in the 'Works_on' table.");
        }
    
	}
	
	/**
     * Inserts a project record into the database.
     * @param conn Connection object representing the database connection.
     * @param Pname name of the project.
     * @param Pnumber unique identifier of a project.
     * @param Plocation location of the project.
     * @param Dnum deparment number, foreign key.
     * @throws SQLException If an SQL exception occurs during the insertion process.
     */
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
	
	/**
     * Inserts a worker record into the database.
     * @param conn Connection object representing the database connection.
     * @param Essn employee's Ssn.
     * @param Pno Project's number.
     * @param Hours Hours worked by employee.
     * @throws SQLException If an SQL exception occurs during the insertion process.
     */
	private static void insertWorker(Connection conn, String Essn, int Pno, String Hours) throws SQLException {
        String sql = "INSERT INTO works_on (Essn, Pno, Hours) VALUES (?, ?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Essn);
            pstmt.setInt(2, Pno);
            pstmt.setString(3,Hours);
            pstmt.executeUpdate();
            System.out.println("Inserted row into Works_on table: " + Essn+ " "+Pno );
        }
    }

	/**
     * Deletes a person record from the database.
     * @param conn Connection object representing the database connection.
     * @param Pnumber The unique identifier of the Project.
     * @throws SQLException If an SQL exception occurs during the deletion process.
     */
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
	/**
     * Shows in output the dependent table.
     * @param conn Connection object representing the database connection.
     * @throws SQLException If an SQL exception occurs during the process.
     */
	private static void getDependentTable(Connection conn) throws SQLException {
		try(Statement statement = conn.createStatement()){
			ResultSet result = statement.executeQuery("SELECT * FROM dependent");
			System.out.println("DEPENDENT TABLE");
			System.out.println("------------------------------------------------------");
			while (result.next()) {
				System.out.print(result.getString("Essn") + " , " + result.getString("Dependent_name")+ " , " + result.getString("Sex")+ " , " + result.getString("Bdate")+ " ,  " + result.getString("Relationship") + "\n");
			}
			System.out.println("------------------------------------------------------");
		}
	}
	/**
     * Shows in output the project table.
     * @param conn Connection object representing the database connection.
     * @throws SQLException If an SQL exception occurs during the process.
     */
	private static void getProjectTable(Connection conn) throws SQLException {
		try(Statement statement = conn.createStatement()){
			ResultSet result = statement.executeQuery("SELECT * FROM project");
			System.out.println("PROJECT TABLE");
			System.out.println("------------------------------------------------------");
			while (result.next()) {
				System.out.print(result.getString("Pname") + " , " + result.getString("Pnumber")+ " , " + result.getString("Plocation")+ " , " + result.getString("Dnum") + "\n");
			}
			System.out.println("------------------------------------------------------");
		}
	}
	/**
     * Shows in output the works_on table.
     * @param conn Connection object representing the database connection.
     * @throws SQLException If an SQL exception occurs during the process.
     */
	private static void getWorks_onTable(Connection conn) throws SQLException {
		try(Statement statement = conn.createStatement()){
			ResultSet result = statement.executeQuery("SELECT * FROM works_on");
			System.out.println("WORKS_ON TABLE");
			System.out.println("------------------------------------------------------");
			while (result.next()) {
				System.out.print(result.getString("Essn") + " , " + result.getString("Pno")+ " , " + result.getString("Hours")+ "\n");
			}
			System.out.println("------------------------------------------------------");
		}
	}
}
	
	
