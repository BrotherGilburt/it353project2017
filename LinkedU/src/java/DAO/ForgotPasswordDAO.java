/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ForgotPassword;
import java.sql.Connection;
import java.sql.DriverManager;
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
    
    public static int findUserID(ForgotPassword forgotPasswordModel) throws ClassNotFoundException, SQLException {
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
    
    public static int changePassword(ForgotPassword forgotPasswordModel) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/LinkedU";// connection string
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String updateString;
            Statement stmt = DBConn.createStatement();

            updateString = "UPDATE LinkedU.LoginInfo SET "
                    + "password ='" + forgotPasswordModel.getNewPassword() + "'"
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

            updateString = "DELETE FROM LinkedU.LoginInfo WHERE"
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
}
