package Databases_programming_project;

import java.sql.*;
import javax.swing.JOptionPane;

public class ProjectAY {
	//Alaitz Ye database
    public final static String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public final static String DB_URL = "jdbc:mysql://dif-mysql.ehu.es:23306/DBI51";
    public final static String DB_USERNAME = "DBI51";
    public final static String DB_PASSWORD = "DBI51";
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
                    conn = getConnection();
                    executeQueries(conn);
                    break;
                default:
                    System.out.println("Invalid option selected.");
            }
        } catch (SQLException | ClassNotFoundException e) {
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
        conn.setAutoCommit(false);
        System.out.println("First let's make some configurations in the database:\n");

        // INSERT INTO department table without using savepoint
        System.out.println("Inserting into department table without using savepoint:");
        insertDepartment(conn, "Finance", 6, "123456789", "2010-09-30");
        conn.commit();
        System.out.println("Transaction committed successfully.");

        // INSERT INTO department table using savepoint
        System.out.println("\nInserting into department table using savepoint:");
        @SuppressWarnings("unused")
		Savepoint savepoint1 = conn.setSavepoint("savedfirst1");
        insertDepartment(conn, "Marketing", 2, "453453453", "2002-05-22");
        insertDepartment(conn, "Human resources", 3, "453453453", "2000-06-20");
        conn.commit();
        System.out.println("Transaction committed successfully.");

        //UPDATE one optional excursion trip to Madrid
        updateOptionalExcursion(conn);

        // DELETE FROM person table
        System.out.println("\nDeleting records from the person table:");
        deletePerson(conn, "Abigail");
        deletePerson(conn, "Alexander");
        conn.commit();
        System.out.println("Transaction committed successfully.");
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
            insertPerson(conn, "Abigail", 30, "Female", "123456");
            insertPerson(conn, "Alexander", 35, "Male", "789123");

            // Update statement
            updateOptionalExcursion(conn);

            // Delete statements
            deleteDepartment(conn, 2);
            deleteDepartment(conn, 3);
            deleteDepartment(conn, 6);

            conn.commit();
            System.out.println("Database changes applied successfully.");
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
     * Executes SQL queries interactively based on user input.
     * @param conn Connection object representing the database connection.
     */
    private static void executeQueries(Connection conn) {
        try {
            boolean exit = false;
            while (!exit) {
                Statement statement = conn.createStatement();

                String query = JOptionPane.showInputDialog("Insert a number for a query(1-5, 0 EXIT).");
                int queryNum = Integer.parseInt(query);

                if (queryNum == 0) {
                    exit = true;
                } else if (queryNum >= 1 && queryNum <= 5) {
                    System.out.println("Executing query " + queryNum + ":\n");

                    switch (queryNum) {
                        case 1:
                            ResultSet result1 = statement.executeQuery(
                                    "SELECT g.guidename FROM tourguide AS g WHERE g.GuideId IN ( SELECT t.GuideId FROM trip AS t JOIN customer AS c ON t.GuideId = c.CustomerId WHERE c.custname LIKE CONCAT(g.guidename, '%'))");
                            while (result1.next()) {
                                System.out.print(result1.getString("guidename") + "\n");
                            }
                            break;
                        case 2:
                            ResultSet result2 = statement.executeQuery(
                            	    "SELECT DISTINCT p.nameId " +
                            	    "FROM person p " +
                            	    "WHERE NOT EXISTS ( " +
                            	    "    SELECT * " +
                            	    "    FROM sales s " +
                            	    "    WHERE s.restaurname = 'KingMeal' AND s.amount > 10000 " +
                            	    ") " +
                            	    "AND NOT EXISTS ( " +
                            	    "    SELECT * " +
                            	    "    FROM frequents f " +
                            	    "    WHERE f.nameId = p.nameId AND f.restaurname = 'GoodFood' " +
                            	    ") " +
                            	    "AND NOT EXISTS ( " +
                            	    "    SELECT * " +
                            	    "    FROM eats e " +
                            	    "    WHERE e.nameId = p.nameId AND e.dish = 'eggsbenedicte' " +
                            	    ") " +
                            	    "AND NOT EXISTS ( " +
                            	    "    SELECT * " +
                            	    "    FROM serves se " +
                            	    "    WHERE se.restaurname = 'LittleFat' AND se.price > 10.00 " +
                            	    ")");
                            	while (result2.next()) {
                            	    System.out.print(result2.getString("nameId") + "\n");
                            	}
                            break;
                        case 3:
                            ResultSet result3 = statement.executeQuery(
                                    "SELECT guidename FROM tourguide WHERE guidename NOT IN (SELECT custname FROM customer)");
                            while (result3.next()) {
                                System.out.print(result3.getString("guidename") + "\n");
                            }
                            break;
                        case 4:
                            String language = JOptionPane.showInputDialog("Insert a language: ");
                            ResultSet result4 = statement.executeQuery(
                                "SELECT DISTINCT custname " +
                                "FROM trip T, tourguide TG, languages L, hotel_trip_customer HTC, customer C " +
                                "WHERE T.guideId = TG.GuideId " +
                                "AND TG.guideId = L.guideId " +
                                "AND L.Lang = '" + language + "' " +
                                "AND T.TripTo = HTC.TripTo " +
                                "AND HTC.CustomerId = C.CustomerId");
                            while (result4.next()) {
                                System.out.print(result4.getString("custname") + "\n");
                            }
                            break;
                        case 5:
                            String cityname = JOptionPane.showInputDialog("Insert a city: ");
                            ResultSet result5 = statement.executeQuery(
                                    "SELECT TripTo, DepartureDate FROM trip WHERE CityDeparture='" + cityname + "'");
                            while (result5.next()) {
                                System.out.print(result5.getString("TripTo") + ", " + result5.getString("DepartureDate") + "\n");
                            }
                            break;
                        default:
                            System.out.println("ERROR. Invalid query.\n");
                            break;
                    }

                    System.out.println("Query " + queryNum + " executed successfully.\n");
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
    /**
     * Inserts a department record into the database.
     * @param conn Connection object representing the database connection.
     * @param name Department name.
     * @param number Department number.
     * @param mgrSsn Manager's SSN.
     * @param mgrStartDate Manager's start date.
     * @throws SQLException If an SQL exception occurs during the insertion process.
     */
    private static void insertDepartment(Connection conn, String name, int number, String mgrSsn, String mgrStartDate) throws SQLException {
        String sql = "INSERT INTO department (Dname, Dnumber, Mgr_ssn, Mgr_start_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, number);
            pstmt.setString(3, mgrSsn);
            pstmt.setString(4, mgrStartDate);
            pstmt.executeUpdate();
            System.out.println("Inserted row into department table: " + name);
        }
    }
    /**
     * Deletes a person record from the database.
     * @param conn Connection object representing the database connection.
     * @param nameId The unique identifier of the person.
     * @throws SQLException If an SQL exception occurs during the deletion process.
     */
    private static void deletePerson(Connection conn, String nameId) throws SQLException {
        String deleteEatsSql = "DELETE FROM eats WHERE nameId = ?";
        try (PreparedStatement pstmtEats = conn.prepareStatement(deleteEatsSql)) {
            pstmtEats.setString(1, nameId);
            int rowsAffected = pstmtEats.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " record(s) from the 'eats' table.");
        }

        String deleteFrequentsSql = "DELETE FROM frequents WHERE nameId = ?";
        try (PreparedStatement pstmtFrequents = conn.prepareStatement(deleteFrequentsSql)) {
            pstmtFrequents.setString(1, nameId);
            int rowsAffected = pstmtFrequents.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " record(s) from the 'frequents' table.");
        }

        String deletePersonSql = "DELETE FROM person WHERE nameId = ?";
        try (PreparedStatement pstmtPerson = conn.prepareStatement(deletePersonSql)) {
            pstmtPerson.setString(1, nameId);
            int rowsAffected = pstmtPerson.executeUpdate();
            System.out.println(
                    "Deleted " + rowsAffected + " record(s) from the 'person' table for nameId: " + nameId);
        }
    }
    /**
     * Updates an optional excursion in the database.
     * @param conn Connection object representing the database connection.
     * @throws SQLException If an SQL exception occurs during the update process.
     */
    private static void updateOptionalExcursion(Connection conn) throws SQLException {
        String updateSql = "UPDATE `optional_excursion` SET `Price` = 800 WHERE `TripTo` = 'Madrid' AND `DepartureDate` = '2018-05-01' AND `CodeExc` = 13";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated " + rowsAffected + " row(s) in the 'optional_excursion' table.");
        }
    }
    /**
     * Inserts a person record into the database.
     * @param conn Connection object representing the database connection.
     * @param nameId The unique identifier of the person.
     * @param age The age of the person.
     * @param gender The gender of the person.
     * @param id The ID of the person.
     * @throws SQLException If an SQL exception occurs during the insertion process.
     */
    private static void insertPerson(Connection conn, String nameId, int age, String gender, String id) throws SQLException {
        String sql = "INSERT INTO person (nameId, age, gender, id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nameId);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.setString(4, id);
            pstmt.executeUpdate();
            System.out.println("Inserted row into person table: " + nameId);
        }
    }
    /**
     * Deletes a department record from the database.
     * @param conn Connection object representing the database connection.
     * @param dnumber The department number.
     * @throws SQLException If an SQL exception occurs during the deletion process.
     */
    private static void deleteDepartment(Connection conn, int dnumber) throws SQLException {
        String deleteSql = "DELETE FROM department WHERE Dnumber = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
            pstmt.setInt(1, dnumber);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " record(s) from the 'department' table for Dnumber: " + dnumber);
        }
    }
}
