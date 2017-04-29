/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Login;
import Model.Account;
import Model.Student;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author IT353S710
 */
@ManagedBean
@RequestScoped
public class AccountDB {

    /**
     * Creates a new instance of AccountDB
     */
    public AccountDB() {
    }

    public static int createAccount(Account user, Login login) throws FileNotFoundException, URISyntaxException {
        File file = new File("W:/Information Systems/Second Semester/IT353-Web Development Technologies/NetBeans Project/WebApplication1/web/Resources/default_student.png");
        InputStream i = new FileInputStream(file);
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

            insertString = "INSERT INTO LinkedU.LoginInfo VALUES ('"
                    + login.getUserID()
                    + "','" + login.getPassword()
                    + "')";

            rowCount += stmt.executeUpdate(insertString);

            stmt = DBConn.createStatement();

            insertString = "INSERT INTO LinkedU.Accounts VALUES ('"
                    + login.getUserID()
                    + "','" + user.getEmail()
                    + "','" + user.getAccountType()
                    + "','" + user.getSecurityQuestion()
                    + "','" + user.getSecurityAnswer()
                    + "')";

            rowCount += stmt.executeUpdate(insertString);

            stmt = DBConn.createStatement();
            Student myProfileDB = new Student();
            /*insertString = "INSERT INTO LinkedU.Students VALUES ('"
                    + login.getUserID()
                    + "','" + myProfileDB.getFirstName()
                    + "','" + myProfileDB.getLastName()
                    + "'," + myProfileDB.getACT()
                    + "," + myProfileDB.getSAT()
                    + "," + myProfileDB.getPSAT_NMSQT()
                    + ",'" + myProfileDB.getUniversities()
                    + "','" + myProfileDB.getMajors()
                    + "','" + myProfileDB.getImage()
                    + "','" + myProfileDB.getMixtape()
                    + "','" + myProfileDB.getEssay()
                    + "'," + i
                    + ")";*/
            insertString = "INSERT INTO LINKEDU.STUDENTS VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, login.getUserID());
            pstmt.setString(2, myProfileDB.getFirstName());
            pstmt.setString(3, myProfileDB.getLastName());
            pstmt.setInt(4, myProfileDB.getACT());
            pstmt.setInt(5, myProfileDB.getSAT());
            pstmt.setInt(6, myProfileDB.getPSAT_NMSQT());
            String universities = "";
            for (int j = 0; j < myProfileDB.getUniversities().size(); j++) {
                universities += myProfileDB.getUniversities().get(j) + ";";
            }
            String majors = "";
            for (int j = 0; j < myProfileDB.getMajors().size(); j++) {
                majors += myProfileDB.getMajors().get(j) + ";";
            }
            pstmt.setString(7, universities);
            pstmt.setString(8, majors);
            pstmt.setString(9, myProfileDB.getImage());
            pstmt.setString(10, myProfileDB.getMixtape());
            pstmt.setString(11, myProfileDB.getEssay());
            pstmt.setBlob(12, i);
            System.out.println("insert string =" + insertString);

            rowCount += pstmt.executeUpdate();
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }

    public static Login getLogin(Login login) {
        Login record = new Login();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.LoginInfo WHERE userID='" + login.getUserID() + "'");

            if (rs.next()) {
                record.setUserID(rs.getString("userid"));
                record.assignPassword(rs.getString("password"));
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

    public static Account getAccount(String userID) {
        Account record = new Account();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.Accounts WHERE userID='" + userID + "'");

            if (rs.next()) {
                record.setUserID(rs.getString("userid"));
                /*record.setFirstName(rs.getString("firstname"));
                record.setLastName(rs.getString("lastname"));*/
                record.setEmail(rs.getString("email"));
                record.setAccountType(rs.getString("accounttype"));
                record.setSecurityQuestion(rs.getString("securityquestion"));
                record.setSecurityAnswer(rs.getString("securityanswer"));
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

    public static boolean updateUser(Account user, Login login) {
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

            String updateString;
            Statement stmt = DBConn.createStatement();

            updateString = "UPDATE LinkedU.LoginInfo SET "
                    + "password ='" + login.getPassword() + "'"
                    + "WHERE userid = '" + login.getUserID() + "'";

            rowCount += stmt.executeUpdate(updateString);

            stmt = DBConn.createStatement();

            updateString = "UPDATE LinkedU.Accounts SET "
                    //+ " userid = '" + login.getUserID() + "'"
                    /*+ "firstname = '" + user.getFirstName() + "', "
                    + "lastname = '" + user.getLastName() + "', "*/
                    + "email = '" + user.getEmail() + "', "
                    + "securityquestion = '" + user.getSecurityQuestion() + "', "
                    + "securityanswer = '" + user.getSecurityAnswer() + "'"
                    + "WHERE userid = '" + login.getUserID() + "'";

            rowCount += stmt.executeUpdate(updateString);
            System.out.println("insert string =" + updateString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean isUserID(String userID) {
        boolean found = true;
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.Accounts WHERE userID='" + userID + "'");

            if (rs.next()) {
                found = true;
            } else {
                found = false;
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

        return found;
    }

}
