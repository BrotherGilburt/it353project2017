/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
package DAO;

import Model.Login;
import Model.Account;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author IT353S710
 */
/*public class StudentDB {
    public static int createStudent(String userID) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/LinkedU";// connection string
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String insertString;
            Statement stmt = DBConn.createStatement();

            insertString = "INSERT INTO LinkedU.student VALUES ('"
                    + userID
                    + "')";

            rowCount += stmt.executeUpdate(insertString);

            System.out.println("insert string =" + insertString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }

    public static ArrayList getStudents(String searchText) {
        searchText = searchText.toUpperCase();
        Student record = null;
        ArrayList<Student> recordsList = null;
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.users "
                    + "WHERE upper(firstname) LIKE '%" + searchText
                    + "%' OR upper(lastname) LIKE '%" + searchText + "%'");

            while (rs.next()) {
                if (recordsList == null) recordsList = new ArrayList();
                record = new Student();
                recordsList.add(record);
                record.setFirstName(rs.getString("firstname"));
                record.setLastName(rs.getString("lastname"));
                record.setUserID(rs.getString("userid"));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return recordsList;
    }
}*/
