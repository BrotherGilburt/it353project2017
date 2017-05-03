/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Account;
import Model.ForgotPassword;
import Model.Login;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Keegan
 */
@Named(value = "forgotPasswordDAO")
@RequestScoped
public class ForgotPasswordDAO {

    /**
     * Creates a new instance of ForgotPasswordDAO
     */
    public ForgotPasswordDAO() {
    }

    public static int setGenString(ForgotPassword forgotPasswordModel) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/LinkedU";// connection string
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String insertString;
            Statement stmt = DBConn.createStatement();

            insertString = "INSERT INTO LinkedU.PasswordReset VALUES ('"
                    + forgotPasswordModel.getEmail()
                    + "','" + forgotPasswordModel.getGenString()
                    + "')";

            rowCount += stmt.executeUpdate(insertString);
            stmt = DBConn.createStatement();
            DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowCount;
    }
    
    public static Login findUserID(Account accountModel, ForgotPassword forgotPasswordModel) throws ClassNotFoundException, SQLException {
        Login record = new Login();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.Accounts WHERE email='" + accountModel.getEmail() + "'");            
            
            if (rs.next()) {
                record.setUserID(rs.getString("userid"));
                forgotPasswordModel.setUserID(record.getUserID());
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
    
    public static int changePassword(ForgotPassword forgotPasswordModel) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/LinkedU";// connection string
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String updateString;
            Statement stmt = DBConn.createStatement();

            updateString = "UPDATE LinkedU.LoginInfo SET "
                    + "password ='" + forgotPasswordModel.getNewPassword() + "' "
                    + "WHERE userid = '" + forgotPasswordModel.getUserID() + "'";

            rowCount += stmt.executeUpdate(updateString);
            stmt = DBConn.createStatement();
            DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowCount;
    }
    
    public static int deleteGenString(ForgotPassword forgotPasswordModel) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/LinkedU";// connection string
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String updateString;
            Statement stmt = DBConn.createStatement();

            updateString = "DELETE FROM LinkedU.PasswordReset WHERE "
                    + "email = '" + forgotPasswordModel.getEmail() + "'";

            rowCount += stmt.executeUpdate(updateString);
            stmt = DBConn.createStatement();
            DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rowCount;
    }
    
    public static ForgotPassword findGenString(ForgotPassword forgotPasswordModel) throws ClassNotFoundException, SQLException {
        ForgotPassword record = new ForgotPassword();
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://localhost:1527/LinkedU";
        Connection DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

        try {
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.PasswordReset WHERE email='" +
                    forgotPasswordModel.getEmail() + "' AND gen_string='" +
                    forgotPasswordModel.getGenString() + "'");

            if (rs.next()) {
                record.setEmail(rs.getString("email"));
                record.setGenString(rs.getString("gen_string"));
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
}
