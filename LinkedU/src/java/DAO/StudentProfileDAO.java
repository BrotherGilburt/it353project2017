/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Profile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import javax.inject.Named;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Gile
 */
@Named(value = "studentProfileDAO")
@RequestScoped
public class StudentProfileDAO {

    /**
     * Creates a new instance of StudentProfileDAO
     */
    public StudentProfileDAO() {
    }

    public static int setProfile(Profile record) {
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

            ArrayList<String> uniList = record.getUniversities();
            StringBuilder universities = new StringBuilder();
            for (int i = 0; i < uniList.size(); i++) {
                universities.append(uniList.get(i) + ";");
            }

            ArrayList<String> majList = record.getMajors();
            StringBuilder majors = new StringBuilder();
            for (int i = 0; i < majList.size(); i++) {
                majors.append(majList.get(i) + ";");
            }

            String insertString;
            Statement stmt = DBConn.createStatement();

            insertString = "INSERT INTO LinkedU.Students VALUES ('"
                    + record.getUserID() + "',"
                    + record.getFirstName() + "',"
                    + record.getLastName() + "',"
                    + record.getACT() + ","
                    + record.getSAT() + ","
                    + record.getPSAT_NMSQT() + ",'"
                    + universities.toString() + "','"
                    + majors.toString() + "','"
                    + record.getImage() + "','"
                    + record.getMixtape() + "','"
                    + record.getEssay() + "')";

            rowCount += stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }
    
    public static Profile getProfile(String userID) {
        Profile record = new Profile();
                DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        
        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.Students WHERE userID='" + userID + "'");

            if (rs.next()) {
                record.setUserID(rs.getString("userid"));
                record.setFirstName(rs.getString("FirstName"));
                record.setFirstName(rs.getString("LastName"));
                record.setACT(rs.getInt("ACT"));
                record.setSAT(rs.getInt("SAT"));
                record.setPSAT_NMSQT(rs.getInt("PSAT_NMSQT"));
                
                String uniList = rs.getString("Universities");
                if (!uniList.equals(""))
                    record.setUniversities(new ArrayList<String>(Arrays.asList(uniList.split(";"))));
                else
                    record.setUniversities(new ArrayList<String>());
                
                String majList = rs.getString("Majors");
                if (!majList.equals(""))
                    record.setMajors(new ArrayList<String>(Arrays.asList(majList.split(";"))));
                else
                    record.setMajors(new ArrayList<String>());
                
                record.setImage(rs.getString("Image"));
                record.setMixtape(rs.getString("Mixtape"));
                record.setEssay(rs.getString("Essay"));
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
    
    public static ArrayList getStudentsByName(String searchText) {
        searchText = searchText.toUpperCase();
        Profile record = null;
        ArrayList<Profile> recordsList = null;
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.Students "
                    + "WHERE upper(firstname) LIKE '%" + searchText
                    + "%' OR upper(lastname) LIKE '%" + searchText + "%'");

            while (rs.next()) {
                if (recordsList == null) recordsList = new ArrayList();
                record = new Profile();
                recordsList.add(record);
                record.setUserID(rs.getString("userid"));
                record.setFirstName(rs.getString("firstname"));
                record.setLastName(rs.getString("lastname"));
                record.setACT(rs.getInt("ACT"));
                record.setSAT(rs.getInt("SAT"));
                record.setPSAT_NMSQT(rs.getInt("PSAT_NMSQT"));
                
                String uniList = rs.getString("Universities");
                if (!uniList.equals(""))
                    record.setUniversities(new ArrayList<String>(Arrays.asList(uniList.split(";"))));
                else
                    record.setUniversities(new ArrayList<String>());
                
                String majList = rs.getString("Majors");
                if (!majList.equals(""))
                    record.setMajors(new ArrayList<String>(Arrays.asList(majList.split(";"))));
                else
                    record.setMajors(new ArrayList<String>());
                
                record.setImage(rs.getString("Image"));
                record.setMixtape(rs.getString("Mixtape"));
                record.setEssay(rs.getString("Essay"));
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
