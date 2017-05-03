/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Student;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
     public String emailByID(String ID){
        String retVal = null;
        Student record = new Student();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            Statement stmt = DBConn.createStatement();
            String query = "select email from LINKEDU.ACCOUNTS a join linkedu.students s  using(userid) where userid='"+ID+"'";

            
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                retVal = rs.getString("email");
                
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

        return retVal;
    }
        
    

    public static int createStudent(Student record) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {

            String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";// connection string
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String insertString = "INSERT INTO LINKEDU.STUDENTS VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, record.getUserID());
            pstmt.setString(2, record.getFirstName());
            pstmt.setString(3, record.getLastName());
            pstmt.setInt(4, record.getACT());
            pstmt.setInt(5, record.getSAT());
            pstmt.setInt(6, record.getPSAT_NMSQT());
            String universities = "";
            for (int j = 0; j < record.getUniversities().size(); j++) {
                universities += record.getUniversities().get(j) + ";";
            }
            String majors = "";
            for (int j = 0; j < record.getMajors().size(); j++) {
                majors += record.getMajors().get(j) + ";";
            }
            pstmt.setString(7, universities);
            pstmt.setString(8, majors);
            pstmt.setString(9, record.getImage());
            pstmt.setString(10, record.getMixtape());
            pstmt.setString(11, record.getEssay());
            System.out.println("insert string =" + insertString);
            rowCount += pstmt.executeUpdate();
            
            insertString = "INSERT INTO LinkedU.STUDENTDOC (USERID) VALUES (?)";
            pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, record.getUserID());
            System.out.println("insert string =" + insertString);
            rowCount += pstmt.executeUpdate();
            DBConn.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }

    public static int updateStudent(Student record) throws SQLException {
        Connection DBConn = null;
        Statement stmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";// connection string
            DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            ArrayList<String> uniList = record.getUniversities();
            StringBuilder universities = new StringBuilder();
            for (int i = 0; i < uniList.size(); i++) {
                universities.append(uniList.get(i)).append(";");
            }

            ArrayList<String> majList = record.getMajors();
            StringBuilder majors = new StringBuilder();
            for (int i = 0; i < majList.size(); i++) {
                majors.append(majList.get(i)).append(";");
            }

            String updateString;
            stmt = DBConn.createStatement();

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
                    + " WHERE userid = '" + record.getUserID() + "'";
            System.out.println("update string =" + updateString);
            rowCount += stmt.executeUpdate(updateString);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {

            if (stmt != null) {
                stmt.close();
            }

            if (DBConn != null) {
                DBConn.close();
            }
        }

        return rowCount;
    }

    public static Student getProfile(String userID) {
        Student record = new Student();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";
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
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";
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
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";
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
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";
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
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";
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
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";
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
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";
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
    public static int updateResume(String userid, UploadedFile resume) throws IOException, ClassNotFoundException, SQLException{
    InputStream i = resume.getInputstream();
        Connection DBConn = null;
        PreparedStatement pstmt = null;
        int rowCount = 0;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        try {
            String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";// connection string
            DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            String updateString;
            updateString = "UPDATE LINKEDU.STUDENTDOC SET "
                    + "RESUME = ? WHERE USERID = ?";
            System.out.println(updateString + userid + i);
            pstmt = DBConn.prepareStatement(updateString);
            pstmt.setBinaryStream(1, i);
            pstmt.setString(2, userid);
            System.out.println(updateString + " " + userid + " " + i);
            rowCount += pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
        } finally {
            if (DBConn != null) {
                DBConn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return rowCount;
    }

    public static int updateMixtape(String mixtape, String userid) throws SQLException {
        Connection DBConn = null;
        Statement stmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";// connection string
            DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            String updateString;
            stmt = DBConn.createStatement();

            updateString = "UPDATE LinkedU.Students SET MIXTAPE = '"
                    + mixtape + "' WHERE USERID = '" + userid + "'";
            System.out.println(updateString);
            rowCount += stmt.executeUpdate(updateString);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {

            if (stmt != null) {
                stmt.close();
            }

            if (DBConn != null) {
                DBConn.close();
            }
        }

        return rowCount;

    }

    public static int updateEssay(String essay, String userid) throws SQLException {
        Connection DBConn = null;
        Statement stmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";// connection string
            DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            String updateString;
            stmt = DBConn.createStatement();

            updateString = "UPDATE LinkedU.Students SET ESSAY = '"
                    + essay + "' WHERE USERID = '" + userid + "'";
            System.out.println(updateString);
            rowCount += stmt.executeUpdate(updateString);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {

            if (stmt != null) {
                stmt.close();
            }

            if (DBConn != null) {
                DBConn.close();
            }
        }

        return rowCount;

    }

}
