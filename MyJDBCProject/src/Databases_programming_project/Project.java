package Databases_programming_project;

import java.sql.*;

public class Project {
    public final static String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public final static String DB_URL = "jdbc:mysql://dif-mysql.ehu.es:23306/DBI51";
    public final static String DB_USERNAME = "DBI51";
    public final static String DB_PASSWORD = "DBI51";

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
            System.out.println("Conection to Alaitz's database:\n");
            conn = getConnection();
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
       //As there are restricions in person table of foreign keys, have to remove from eats table first
        String deleteEatsSql = "DELETE FROM eats WHERE nameId = ?";
        try (PreparedStatement pstmtEats = conn.prepareStatement(deleteEatsSql)) {
            pstmtEats.setString(1, nameId);
            int rowsAffected = pstmtEats.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " record(s) from the 'eats' table.");
        }
        //As there are restricions in person table of foreign keys, have to remove from frequents table second
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
            System.out.println("Deleted " + rowsAffected + " record(s) from the 'person' table for nameId: " + nameId);
        }
    }

}
