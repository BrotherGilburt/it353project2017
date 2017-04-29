/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Student;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import javax.inject.Named;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Gile
 */
@Named(value = "studentProfileDAO")
@RequestScoped
public class StudentDAO {

    /**
     * Creates a new instance of StudentProfileDAO
     */
    public StudentDAO() {
    }

    public static int setProfile(Student record) {
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

            String updateString;
            Statement stmt = DBConn.createStatement();

            updateString = "UPDATE LinkedU.Students SET FirstName='"
                    + record.getFirstName() + "', LastName='"
                    + record.getLastName() + "', ACT="
                    + record.getACT() + ", SAT="
                    + record.getSAT() + ", PSAT_NMSQT="
                    + record.getPSAT_NMSQT() + ", Universities='"
                    + universities.toString() + "', Majors='"
                    + majors.toString() + "', Image='"
                    + record.getImage() + "', Mixtape='"
                    + record.getMixtape() + "', Essay='"
                    + record.getEssay() + "'"
                    + " WHERE USERID = '" + record.getUserID() + "'";
            System.out.println("insert string =" + updateString);
            rowCount += stmt.executeUpdate(updateString);

            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }

    public static Student getProfile(String userID) {
        Student record = new Student();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.Students WHERE userID='" + userID + "'");

            if (rs.next()) {
                record.setUserID(rs.getString("userid"));
                record.setFirstName(rs.getString("FirstName"));
                record.setLastName(rs.getString("LastName"));
                record.setACT(rs.getInt("ACT"));
                record.setSAT(rs.getInt("SAT"));
                record.setPSAT_NMSQT(rs.getInt("PSAT_NMSQT"));

                String uniList = rs.getString("Universities");
                if (!uniList.equals("")) {
                    record.setUniversities(new ArrayList<String>(Arrays.asList(uniList.split(";"))));
                } else {
                    record.setUniversities(new ArrayList<String>());
                }

                String majList = rs.getString("Majors");
                if (!majList.equals("")) {
                    record.setMajors(new ArrayList<String>(Arrays.asList(majList.split(";"))));
                } else {
                    record.setMajors(new ArrayList<String>());
                }

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

    public static ArrayList getStudentsByNameContaining(String searchText) {
        searchText = searchText.toUpperCase();
        Student record = null;
        ArrayList<Student> recordsList = null;
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.Students "
                    + "WHERE upper(firstname) LIKE '%" + searchText
                    + "%' OR upper(lastname) LIKE '%" + searchText + "%'");

            while (rs.next()) {
                if (recordsList == null) {
                    recordsList = new ArrayList();
                }
                record = new Student();
                recordsList.add(record);
                record.setUserID(rs.getString("userid"));
                record.setFirstName(rs.getString("firstname"));
                record.setLastName(rs.getString("lastname"));
                record.setACT(rs.getInt("ACT"));
                record.setSAT(rs.getInt("SAT"));
                record.setPSAT_NMSQT(rs.getInt("PSAT_NMSQT"));

                String uniList = rs.getString("Universities");
                if (!uniList.equals("")) {
                    record.setUniversities(new ArrayList(Arrays.asList(uniList.split(";"))));
                } else {
                    record.setUniversities(new ArrayList());
                }

                String majList = rs.getString("Majors");
                if (!majList.equals("")) {
                    record.setMajors(new ArrayList<String>(Arrays.asList(majList.split(";"))));
                } else {
                    record.setMajors(new ArrayList<String>());
                }

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

    public static ArrayList getStudentsByACTScoreGreaterThan(int searchInt) {
        ArrayList<Student> recordsList = new ArrayList();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            PreparedStatement pstmt = DBConn.prepareStatement("SELECT * FROM LinkedU.Students WHERE ACT > ?");

            pstmt.setInt(1, searchInt);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student record = new Student();
                recordsList.add(record);
                record.setUserID(rs.getString("userid"));
                record.setFirstName(rs.getString("firstname"));
                record.setLastName(rs.getString("lastname"));
                record.setACT(rs.getInt("ACT"));
                record.setSAT(rs.getInt("SAT"));
                record.setPSAT_NMSQT(rs.getInt("PSAT_NMSQT"));

                String uniList = rs.getString("Universities");
                if (!uniList.equals("")) {
                    record.setUniversities(new ArrayList(Arrays.asList(uniList.split(";"))));
                } else {
                    record.setUniversities(new ArrayList());
                }

                String majList = rs.getString("Majors");
                if (!majList.equals("")) {
                    record.setMajors(new ArrayList(Arrays.asList(majList.split(";"))));
                } else {
                    record.setMajors(new ArrayList());
                }

                record.setImage(rs.getString("Image"));
                record.setMixtape(rs.getString("Mixtape"));
                record.setEssay(rs.getString("Essay"));
            }
            rs.close();
            pstmt.close();
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

    public static ArrayList getStudentsBySATScoreGreaterThan(int searchInt) {
        ArrayList<Student> recordsList = new ArrayList();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            PreparedStatement pstmt = DBConn.prepareStatement("SELECT * FROM LinkedU.Students WHERE SAT > ?");

            pstmt.setInt(1, searchInt);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student record = new Student();
                recordsList.add(record);
                record.setUserID(rs.getString("userid"));
                record.setFirstName(rs.getString("firstname"));
                record.setLastName(rs.getString("lastname"));
                record.setACT(rs.getInt("ACT"));
                record.setSAT(rs.getInt("SAT"));
                record.setPSAT_NMSQT(rs.getInt("PSAT_NMSQT"));

                String uniList = rs.getString("Universities");
                if (!uniList.equals("")) {
                    record.setUniversities(new ArrayList(Arrays.asList(uniList.split(";"))));
                } else {
                    record.setUniversities(new ArrayList());
                }

                String majList = rs.getString("Majors");
                if (!majList.equals("")) {
                    record.setMajors(new ArrayList(Arrays.asList(majList.split(";"))));
                } else {
                    record.setMajors(new ArrayList());
                }

                record.setImage(rs.getString("Image"));
                record.setMixtape(rs.getString("Mixtape"));
                record.setEssay(rs.getString("Essay"));
            }
            rs.close();
            pstmt.close();
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

    public static ArrayList getStudentsByPSAT_NMSQTScoreGreaterThan(int searchInt) {
        ArrayList<Student> recordsList = new ArrayList();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            PreparedStatement pstmt = DBConn.prepareStatement("SELECT * FROM LinkedU.Students WHERE PSAT_NMSQT > ?");

            pstmt.setInt(1, searchInt);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student record = new Student();
                recordsList.add(record);
                record.setUserID(rs.getString("userid"));
                record.setFirstName(rs.getString("firstname"));
                record.setLastName(rs.getString("lastname"));
                record.setACT(rs.getInt("ACT"));
                record.setSAT(rs.getInt("SAT"));
                record.setPSAT_NMSQT(rs.getInt("PSAT_NMSQT"));

                String uniList = rs.getString("Universities");
                if (!uniList.equals("")) {
                    record.setUniversities(new ArrayList(Arrays.asList(uniList.split(";"))));
                } else {
                    record.setUniversities(new ArrayList());
                }

                String majList = rs.getString("Majors");
                if (!majList.equals("")) {
                    record.setMajors(new ArrayList(Arrays.asList(majList.split(";"))));
                } else {
                    record.setMajors(new ArrayList());
                }

                record.setImage(rs.getString("Image"));
                record.setMixtape(rs.getString("Mixtape"));
                record.setEssay(rs.getString("Essay"));
            }
            rs.close();
            pstmt.close();
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

    public static ArrayList getStudentsByUniversity(String searchText) {
        ArrayList<Student> recordsList = new ArrayList();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            PreparedStatement pstmt = DBConn.prepareStatement("SELECT * FROM LinkedU.Students WHERE UPPER(Universities) LIKE ?");

            pstmt.setString(1, "%" + searchText.toUpperCase() + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student record = new Student();
                recordsList.add(record);
                record.setUserID(rs.getString("userid"));
                record.setFirstName(rs.getString("firstname"));
                record.setLastName(rs.getString("lastname"));
                record.setACT(rs.getInt("ACT"));
                record.setSAT(rs.getInt("SAT"));
                record.setPSAT_NMSQT(rs.getInt("PSAT_NMSQT"));

                String uniList = rs.getString("Universities");
                if (!uniList.equals("")) {
                    record.setUniversities(new ArrayList(Arrays.asList(uniList.split(";"))));
                } else {
                    record.setUniversities(new ArrayList());
                }

                String majList = rs.getString("Majors");
                if (!majList.equals("")) {
                    record.setMajors(new ArrayList(Arrays.asList(majList.split(";"))));
                } else {
                    record.setMajors(new ArrayList());
                }

                record.setImage(rs.getString("Image"));
                record.setMixtape(rs.getString("Mixtape"));
                record.setEssay(rs.getString("Essay"));
            }
            rs.close();
            pstmt.close();
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

    public static ArrayList getStudentsByMajor(String searchText) {
        ArrayList<Student> recordsList = new ArrayList();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            PreparedStatement pstmt = DBConn.prepareStatement("SELECT * FROM LinkedU.Students WHERE UPPER(Majors) LIKE ?");

            pstmt.setString(1, "%" + searchText.toUpperCase() + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student record = new Student();
                recordsList.add(record);
                record.setUserID(rs.getString("userid"));
                record.setFirstName(rs.getString("firstname"));
                record.setLastName(rs.getString("lastname"));
                record.setACT(rs.getInt("ACT"));
                record.setSAT(rs.getInt("SAT"));
                record.setPSAT_NMSQT(rs.getInt("PSAT_NMSQT"));

                String uniList = rs.getString("Universities");
                if (!uniList.equals("")) {
                    record.setUniversities(new ArrayList(Arrays.asList(uniList.split(";"))));
                } else {
                    record.setUniversities(new ArrayList());
                }

                String majList = rs.getString("Majors");
                if (!majList.equals("")) {
                    record.setMajors(new ArrayList(Arrays.asList(majList.split(";"))));
                } else {
                    record.setMajors(new ArrayList());
                }

                record.setImage(rs.getString("Image"));
                record.setMixtape(rs.getString("Mixtape"));
                record.setEssay(rs.getString("Essay"));
            }
            rs.close();
            pstmt.close();
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

    public static int updateImage(Student theModel,UploadedFile image) throws SQLException, IOException {
        InputStream i = image.getInputstream();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
        int rowCount = 0;  
        try {
            String updateString;
            updateString = "UPDATE LINKEDU.STUDENTS SET "
                    + "profilepic = ? WHERE USERID = ?";
            PreparedStatement pstmt = DBConn.prepareStatement(updateString);
                System.out.println(updateString + " " + i + " " + theModel.getUserID());
                pstmt.setBinaryStream(1,i);
                pstmt.setString(2, theModel.getUserID());
                rowCount = pstmt.executeUpdate();
                pstmt.close();
            return rowCount;

        } catch (Exception e) {
            DBConn.close();
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        DBConn.close();
return rowCount;
    }
}