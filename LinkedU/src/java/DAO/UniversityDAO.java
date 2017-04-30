/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.University;
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
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.Arrays;
import org.primefaces.model.UploadedFile;

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
     *
     * @param record - The university to be inserted.
     * @return row count
     */
    public static int createUniversity(University record) throws MalformedURLException, IOException {
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
            URL u = new URL("http://www.stolenimages.co.uk/components/com_easyblog/themes/wireframe/images/placeholder-image.png");
            InputStream i = u.openStream();
            String myDB = "jdbc:derby://localhost:1527/LinkedU";// connection string
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String insertString = "INSERT INTO LINKEDU.UNIVERSITIES VALUES(?,?,?,?,?,?,?,?,?,?)";
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
            pstmt.setBlob(10, i);
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

            insertString = "UPDATE LinkedU.Universities SET"
                    + "premium='" + record.isPremium()
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
            PreparedStatement pstmt = DBConn.prepareStatement("SELECT * FROM LinkedU.Universities WHERE upper(name) LIKE ?");

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

    public static int updateImage(University theModel, UploadedFile image) throws SQLException, IOException {
        InputStream i = image.getInputstream();
        Connection DBConn = ImageDAO.getConnection();
        int rowCount = 0;
        try {
            String updateString;
            updateString = "UPDATE LINKEDU.UNIVERSITIES SET "
                    + "profilepic = ? WHERE USERID = ?";
            PreparedStatement pstmt = DBConn.prepareStatement(updateString);
            System.out.println(updateString + " " + i + " " + theModel.getUserID());
            pstmt.setBinaryStream(1, i);
            pstmt.setString(2, theModel.getUserID());
            rowCount = pstmt.executeUpdate();
            pstmt.close();
            return rowCount;

        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowCount;
    }
}
