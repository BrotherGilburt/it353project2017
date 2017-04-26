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

    public static int setProfile(Profile profile) {
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

            ArrayList<String> uniList = profile.getUniversities();
            StringBuilder universities = new StringBuilder();
            for (int i = 0; i < uniList.size(); i++) {
                universities.append(uniList.get(i) + ";");
            }

            ArrayList<String> majList = profile.getMajors();
            StringBuilder majors = new StringBuilder();
            for (int i = 0; i < majList.size(); i++) {
                majors.append(majList.get(i) + ";");
            }

            String insertString;
            Statement stmt = DBConn.createStatement();

            insertString = "INSERT INTO LinkedU.StudentProfile VALUES ('"
                    + profile.getUserID() + "',"
                    + profile.getACT() + ","
                    + profile.getSAT() + ","
                    + profile.getPSAT_NMSQT() + ",'"
                    + universities.toString() + "','"
                    + majors.toString() + "','"
                    + profile.getImage() + "','"
                    + profile.getMixtape() + "','"
                    + profile.getEssay() + "')";

            rowCount += stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }
    
    public static Profile getProfile(String userID) {
        Profile profile = new Profile();
                DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        
        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.StudentProfile WHERE userID='" + userID + "'");

            if (rs.next()) {
                profile.setUserID(rs.getString("userid"));
                profile.setACT(rs.getInt("ACT"));
                profile.setSAT(rs.getInt("SAT"));
                profile.setPSAT_NMSQT(rs.getInt("PSAT_NMSQT"));
                
                String uniList = rs.getString("Universities");
                if (!uniList.equals(""))
                    profile.setUniversities(new ArrayList<String>(Arrays.asList(uniList.split(";"))));
                else
                    profile.setUniversities(new ArrayList<String>());
                
                String majList = rs.getString("Majors");
                if (!majList.equals(""))
                    profile.setMajors(new ArrayList<String>(Arrays.asList(majList.split(";"))));
                else
                    profile.setMajors(new ArrayList<String>());
                
                profile.setImage(rs.getString("Image"));
                profile.setMixtape(rs.getString("Mixtape"));
                profile.setEssay(rs.getString("Essay"));
            } else {
                profile = null;
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
        
        return profile;
    }
}
