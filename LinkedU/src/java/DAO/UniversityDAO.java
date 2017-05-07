/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Apply;
import Model.Premium;
import Model.University;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Gile
 */
public class UniversityDAO {

    /**
     * Creates a new instance of UniversityDAO
     */
    public UniversityDAO() {
    }

    /**
     * Inserts university into database.
     *
     * @param record - The university to be inserted.
     * @return row count
     */
    public static int createUniversity(University record) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            StringBuilder majorsList = new StringBuilder();
            for (int i = 0; i < record.getMajors().size(); i++) {
                majorsList.append(record.getMajors().get(i)).append(";");
            }
            String myDB = "jdbc:derby://localhost:1527/LinkedU";// connection string
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String insertString = "INSERT INTO LINKEDU.UNIVERSITIES VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, record.getUserID());
            pstmt.setBoolean(2, record.isPremium());
            pstmt.setString(3, record.getName());
            pstmt.setString(4, majorsList.toString());
            pstmt.setString(5, record.getStreet());
            pstmt.setString(6, record.getCity());
            pstmt.setString(7, record.getState());
            pstmt.setString(8, record.getZip());
            pstmt.setString(9, record.getImage());
            System.out.println("insert string =" + insertString);

            rowCount += pstmt.executeUpdate();
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }

    public static int updateUniversity(University record) {
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

            insertString = "UPDATE LinkedU.Universities SET "
                    + "premium=" + record.isPremium()
                    + ", name='" + record.getName()
                    + "', majors='" + majorsList.toString()
                    + "', street='" + record.getStreet()
                    + "', city='" + record.getCity()
                    + "', state='" + record.getState()
                    + "', zip='" + record.getZip()
                    + "', image='" + record.getImage()
                    + "' WHERE userid='" + record.getUserID() + "'";

            rowCount += stmt.executeUpdate(insertString);
            System.out.println("update string =" + insertString);
            DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }

    /**
     * Finds a university in the database by user ID.
     *
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
     *
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
     *
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
                    record.setMajors(new ArrayList(Arrays.asList(majorsList.split(";"))));
                } else {
                    record.setMajors(new ArrayList());
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

    /**
     * Finds all universities in the database.
     *
     * @param searchText
     * @return
     */
    public static ArrayList<University> getUniversitiesByNameContaining(String searchText) {
        ArrayList<University> recordsList = new ArrayList();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            String[] words = searchText.split(" ");
            StringBuilder prepared = new StringBuilder("SELECT * FROM LinkedU.Universities WHERE upper(name) LIKE ?");
            
            for (int i = 1; i < words.length; i++) {
                prepared.append(" AND upper(name) LIKE ?");
            }
            
            PreparedStatement pstmt = DBConn.prepareStatement(prepared.toString());
            
            for (int i = 0; i < words.length; i++) {
                pstmt.setString((i+1), "%" + words[i].toUpperCase() + "%");
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                University record = new University();
                recordsList.add(record);
                record.setUserID(rs.getString("userid"));
                record.setPremium(rs.getBoolean("premium"));
                record.setName(rs.getString("name"));

                String majorsList = rs.getString("majors");
                if (!majorsList.equals("")) {
                    record.setMajors(new ArrayList(Arrays.asList(majorsList.split(";"))));
                } else {
                    record.setMajors(new ArrayList());
                }

                record.setStreet(rs.getString("street"));
                record.setCity(rs.getString("city"));
                record.setState(rs.getString("state"));
                record.setZip(rs.getString("zip"));
                record.setImage(rs.getString("image"));
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

    public static ArrayList<University> getUniversitiesByMajor(String searchText) {
        ArrayList<University> recordsList = new ArrayList();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            PreparedStatement pstmt = DBConn.prepareStatement("SELECT * FROM LinkedU.Universities WHERE upper(majors) LIKE ?");

            pstmt.setString(1, "%" + searchText.toUpperCase() + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                University record = new University();
                recordsList.add(record);
                record.setUserID(rs.getString("userid"));
                record.setPremium(rs.getBoolean("premium"));
                record.setName(rs.getString("name"));

                String majorsList = rs.getString("majors");
                if (!majorsList.equals("")) {
                    record.setMajors(new ArrayList(Arrays.asList(majorsList.split(";"))));
                } else {
                    record.setMajors(new ArrayList());
                }

                record.setStreet(rs.getString("street"));
                record.setCity(rs.getString("city"));
                record.setState(rs.getString("state"));
                record.setZip(rs.getString("zip"));
                record.setImage(rs.getString("image"));
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

    public static ArrayList getFeature() {
        University record = null;
        ArrayList<University> featuredList = null;
        Connection DBConn = null;
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/LINKEDU";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
            Statement stmt = DBConn.createStatement();
            String getString = "select * from LINKEDU.UNIVERSITIES WHERE PREMIUM = TRUE";
            System.out.println(getString);
            ResultSet rs = stmt.executeQuery(getString);
            while (rs.next()) {
                if (featuredList == null) {
                    featuredList = new ArrayList();
                }
                record = new University();
                featuredList.add(record);
                record.setUserID(rs.getString("USERID"));
                record.setName(rs.getString("NAME"));
                String majorsList = rs.getString("majors");
                if (!majorsList.equals("")) {
                    record.setMajors(new ArrayList(Arrays.asList(majorsList.split(";"))));
                } else {
                    record.setMajors(new ArrayList());
                }
                record.setStreet(rs.getString("street"));
                record.setCity(rs.getString("city"));
                record.setState(rs.getString("state"));
                record.setZip(rs.getString("zip"));
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
        return featuredList;
    }
    
    public static ArrayList getApply(String userName) {
        Apply record = null;
        ArrayList<Apply> applyList = null;
        Connection DBConn = null;
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/LINKEDU";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
            Statement stmt = DBConn.createStatement();
            String getString = "select * from LINKEDU.APPLYINFO WHERE UNIVERSITY IN "
                    + "(select name from  LINKEDU.UNIVERSITIES where USERID = "
                    + "'" + userName + "')";
            System.out.println(getString);
            ResultSet rs = stmt.executeQuery(getString);
            while (rs.next()) {
                if (applyList == null) {
                    applyList = new ArrayList();
                }
                record = new Apply();
                applyList.add(record);
                record.setFirstname(rs.getString("FIRSTNAME"));
                record.setMajor(rs.getString("MAJOR"));
                record.setExam(rs.getString("EXAM"));
                record.setScore(rs.getInt("SCORE"));
                record.setEmail(rs.getString("EMAIL"));
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
        return applyList;
    }

}
