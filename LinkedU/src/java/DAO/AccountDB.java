/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Login;
import Model.Account;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

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

    public static int createAccount(Account user, Login login) throws FileNotFoundException, URISyntaxException, ClassNotFoundException, MalformedURLException, IOException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/kssuth1_Sp2017_LinkedU";// connection string
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
            String url="";

            if(user.getAccountType().equals("Student")){
                url = "http://www.racialjusticenetwork.co.uk/wp-content/uploads/2016/12/default-profile-picture-2.png";
            }else if (user.getAccountType().equals("University")){
                url = "http://www.stolenimages.co.uk/components/com_easyblog/themes/wireframe/images/placeholder-image.png";
            }
            System.out.println(url);
            /*URL u = new URL(url);
            InputStream i = u.openStream();
            insertString = "INSERT INTO LinkedU.USERIMAGE VALUES(?,?)";
            PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            pstmt.setString(1, login.getUserID());
            pstmt.setBinaryStream(2,i);
            rowCount += pstmt.executeUpdate();*/
            DBConn.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }

    public static Login getLogin(Login login) {
        Login record = new Login();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/kssuth1_Sp2017_LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
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
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/kssuth1_Sp2017_LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
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

    public static boolean updateAccount(Account user, Login login) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/kssuth1_Sp2017_LinkedU";// connection string
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String updateString;
            Statement stmt = DBConn.createStatement();

            updateString = "UPDATE LinkedU.LoginInfo SET "
                    + "password ='" + login.getPassword() + "'"
                    + "WHERE userid = '" + login.getUserID() + "'";

            rowCount += stmt.executeUpdate(updateString);

            stmt = DBConn.createStatement();

            updateString = "UPDATE LinkedU.Accounts SET "
                    + "email = '" + user.getEmail() + "', "
                    + "securityquestion = '" + user.getSecurityQuestion() + "', "
                    + "securityanswer = '" + user.getSecurityAnswer() + "'"
                    + "WHERE userid = '" + login.getUserID() + "'";

            rowCount += stmt.executeUpdate(updateString);
            System.out.println("update string =" + updateString);
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
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/kssuth1_Sp2017_LinkedU";
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
