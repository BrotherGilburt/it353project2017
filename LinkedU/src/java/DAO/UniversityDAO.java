/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.University;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Gile
 */
@Named(value = "universityDAO")
@RequestScoped
public class UniversityDAO {

    /**
     * Creates a new instance of UniversityDAO
     */
    public UniversityDAO() {
    }

    /**
     * Inserts university into database.
     * @param record - The university to be inserted.
     * @return row count
     */
    public static int setUniversity(University record) {
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

            StringBuilder majorsList = new StringBuilder();
            for (int i = 0; i < record.getMajors().size(); i++) {
                majorsList.append(record.getMajors().get(i)).append(";");
            }

            String insertString;
            Statement stmt = DBConn.createStatement();

            insertString = "INSERT INTO LinkedU.Universities VALUES ('"
                    + record.getUserID()
                    + "'," + record.isPremium()
                    + ",'" + record.getName()
                    + "','" + majorsList.toString()
                    + "','" + record.getStreet()
                    + "','" + record.getCity()
                    + "','" + record.getState()
                    + "','" + record.getZip()
                    + "','" + record.getImage()
                    + "')";

            rowCount += stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }

    /**
     * Finds a university in the database by user ID.
     * @param userID
     * @return 
     */
    public static University getUniversityByID(String userID) {
        University record = new University();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.Universities WHERE userID='" + userID + "'");

            if (rs.next()) {
                record.setUserID(rs.getString("userid"));
                record.setPremium(rs.getBoolean("premium"));
                record.setName(rs.getString("name"));

                String majorsList = rs.getString("majors");
                if (!majorsList.equals("")) {
                    record.setMajors(new ArrayList<String>(Arrays.asList(majorsList.split(";"))));
                } else {
                    record.setMajors(new ArrayList<String>());
                }

                record.setStreet(rs.getString("street"));
                record.setCity(rs.getString("city"));
                record.setState(rs.getString("state"));
                record.setZip(rs.getString("zip"));
                record.setImage(rs.getString("image"));
            } else {
                record = null;
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
        return record;
    }
    
    /**
     * Finds a university in the database by name;
     * @param name
     * @return 
     */
    public static University getUniversityByName(String name) {
        University record = new University();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.Universities WHERE name='" + name + "'");

            if (rs.next()) {
                record.setUserID(rs.getString("userid"));
                record.setPremium(rs.getBoolean("premium"));
                record.setName(rs.getString("name"));

                String majorsList = rs.getString("majors");
                if (!majorsList.equals("")) {
                    record.setMajors(new ArrayList<String>(Arrays.asList(majorsList.split(";"))));
                } else {
                    record.setMajors(new ArrayList<String>());
                }

                record.setStreet(rs.getString("street"));
                record.setCity(rs.getString("city"));
                record.setState(rs.getString("state"));
                record.setZip(rs.getString("zip"));
                record.setImage(rs.getString("image"));
            } else {
                record = null;
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
        return record;
    }

    /**
     * Finds all universities in the database.
     * @return 
     */
    public static ArrayList<University> getAllUniversities() {
        ArrayList<University> recordsList = new ArrayList<University>();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.Universities");

            while (rs.next()) {
                University record = new University();
                recordsList.add(record);
                record.setUserID(rs.getString("userid"));
                record.setPremium(rs.getBoolean("premium"));
                record.setName(rs.getString("name"));

                String majorsList = rs.getString("majors");
                if (!majorsList.equals("")) {
                    record.setMajors(new ArrayList<String>(Arrays.asList(majorsList.split(";"))));
                } else {
                    record.setMajors(new ArrayList<String>());
                }

                record.setStreet(rs.getString("street"));
                record.setCity(rs.getString("city"));
                record.setState(rs.getString("state"));
                record.setZip(rs.getString("zip"));
                record.setImage(rs.getString("image"));
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
}
