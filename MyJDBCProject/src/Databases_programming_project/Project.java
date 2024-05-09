package Databases_programming_project;

import java.sql.*;
import javax.swing.JOptionPane;

public class Project {

    public final static String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public final static String DB_URL = "jdbc:mysql://dif-mysql.ehu.es:23306/DBI51";
    public final static String DB_USERNAME = "DBI51";
    public final static String DB_PASSWORD = "DBI51";

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
        Connection conn = null;
        try {
            System.out.println("Welcome to this query application\n");
            System.out.println("Connection to Alaitz's database:\n");
            conn = getConnection();
            connection = conn;
            conn.setAutoCommit(false);
            System.out.println("First let's make some configurations in the database:\n");

            // INSERT INTO department table without using savepoint
            System.out.println("Inserting into department table without using savepoint:");
            insertDepartment(conn, "Finance", 6, "123456789", "2010-09-30");
            conn.commit();
            System.out.println("Transaction committed successfully.");

            // INSERT INTO department table using savepoint
            System.out.println("\nInserting into department table using savepoint:");
            conn.setAutoCommit(false);
            Savepoint savepoint1 = conn.setSavepoint("savedfirst1");
            insertDepartment(conn, "Marketing", 2, "453453453", "2002-05-22");
            insertDepartment(conn, "Human resources", 3, "453453453", "2000-06-20");
            conn.commit();
            System.out.println("Transaction committed successfully.");

            // DELETE FROM person table
            System.out.println("\nDeleting records from the person table:");
            deletePerson(conn, "Abigail");
            deletePerson(conn, "Alexander");
            conn.commit();
            System.out.println("Transaction committed successfully.");

            // Execute queries
            Querys();

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

    private static void deletePerson(Connection conn, String nameId) throws SQLException {
        // As there are restricions in person table of foreign keys, have to remove
        // from eats table first
        String deleteEatsSql = "DELETE FROM eats WHERE nameId = ?";
        try (PreparedStatement pstmtEats = conn.prepareStatement(deleteEatsSql)) {
            pstmtEats.setString(1, nameId);
            int rowsAffected = pstmtEats.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " record(s) from the 'eats' table.");
        }
        // As there are restricions in person table of foreign keys, have to remove
        // from frequents table second
        String deleteFrequentsSql = "DELETE FROM frequents WHERE nameId = ?";
        try (PreparedStatement pstmtFrequents = conn.prepareStatement(deleteFrequentsSql)) {
            pstmtFrequents.setString(1, nameId);
            int rowsAffected = pstmtFrequents.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " record(s) from the 'frequents' table.");
        }

        // Then, delete the record from the 'person' table
        String deletePersonSql = "DELETE FROM person WHERE nameId = ?";
        try (PreparedStatement pstmtPerson = conn.prepareStatement(deletePersonSql)) {
            pstmtPerson.setString(1, nameId);
            int rowsAffected = pstmtPerson.executeUpdate();
            System.out.println(
                    "Deleted " + rowsAffected + " record(s) from the 'person' table for nameId: " + nameId);
        }
    }

    public static void Querys() {
        String query = JOptionPane.showInputDialog("Insert a number for a query(1-5, 0 EXIT).");
        try {
            int queryNum = Integer.parseInt(query);
            if (queryNum >= 1 && queryNum <= 5) {
                Querys(queryNum);
            } else if (queryNum == 0) {
                System.out.println("Exit.\n");
            } else {
                System.out.println("ERROR. It must be a number from 0 to 5.\n");
                Querys();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR. It must be a number.\n");
            Querys();
        }
    }

    public static void Querys(int query) {
        try {

            statement = connection.createStatement();

            switch (query) {
            case 1:
                try {
                    System.out.println(
                            "Find the names of guides who have gone on trips with customers whose names start with the same letter as the guide's name:\n");
                    ResultSet result1 = statement.executeQuery(
                            "SELECT g.Name FROM Guide AS g WHERE g.Ssn IN ( SELECT t.GuideSsn FROM Trip AS t JOIN Customer AS c ON t.CustId = c.CustId WHERE c.Name LIKE CONCAT(g.Name, '%'))");
                    while (result1.next()) {
                        System.out.print(result1.getString("Name") + "\n");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("ERROR executing query 1.\n");
                }
                break;
            case 2:
                System.out.println("Find the ingredients that have not been used in any menu item:\n");
                ResultSet result2 = statement.executeQuery(
                    "SELECT * FROM Ingredients i WHERE NOT EXISTS (" +
                    "    SELECT * FROM Menu m WHERE NOT EXISTS (" +
                    "        SELECT * FROM Recipe r WHERE r.MenuItemID = m.MenuItemID AND r.IngredientID = i.IngredientID" +
                    "    )" +
                    ")");
                while (result2.next()) {
                    System.out.print(result2.getString("ingredient name") + "\n");
                }
                break;
            case 3:
                System.out.println(
                        "Find the names that appear in the Guides table but that do not appear in the Customers table:\n");
                ResultSet result3 = statement.executeQuery(
                        "select guidename from TOURGUIDE tg where tg.guidename NOT IN(select custname from CUSTOMER ) ");
                while (result3.next()) {
                    System.out.print(result3.getString("guidename") + "\n");
                }
                break;
            case 4:
                String language = JOptionPane.showInputDialog("Insert a language: ");
                System.out.println("These are the names of the customers of trips with guides speaking the language you have chosen:\n");
                ResultSet result4 = statement.executeQuery(
                    "SELECT DISTINCT custname " +
                    "FROM TRIP T, TOURGUIDE TG, LANGUAGE L, TRIP_CUSTOMER TC, CUSTOMER C " +
                    "WHERE T.guideId = TG.GuideId " +
                    "AND TG.LanguageId = L.LanguageId " +
                    "AND L.LanguageName = '" + language + "' " +
                    "AND T.TripId = TC.TripId " +
                    "AND TC.CustomerId = C.CustomerId");
                while (result4.next()) {
                    System.out.print(result4.getString("custname") + "\n");
                }
                break;
            case 5:
                String cityname = JOptionPane.showInputDialog("Insert a city: ");
                System.out.println("This are the trips which are in the city you have choosen:\n");
                ResultSet result5 = statement.executeQuery(
                        "select TripTo, DepartureDate From TRIP Where TRIP.CityDeparture='" + cityname + "' ");
                while (result5.next()) {
                    System.out.print(result5.getString("TripTo") + ", " + result5.getString("DepartureDate") + "\n");
                }
                break;
            default:
                System.out.println("ERROR. Invalid query.\n");
                break;
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR. Connection failed!\n");
        }
    }
}
