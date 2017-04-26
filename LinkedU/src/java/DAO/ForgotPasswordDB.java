/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ForgotPasswordBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class ForgotPasswordDB {

    String driverName = "org.apache.derby.jdbc.ClientDriver";
    //String connStr = "jdbc:derby://gfish2.it.ilstu.edu:1527/kssuth1_Spring2017_LinkedU;create=true";
    String connStr = "jdbc:derby://localhost:1527/LinkedU";
    
    /**
     * Creates a new instance of ForgotPasswordDAO
     */
    public ForgotPasswordDB() {
    }
    
    public ForgotPasswordBean lostPass(String user){
        Connection DBConn = null;
        ForgotPasswordBean uRes = null;
        try {
            DBHelper.loadDriver(driverName);
            DBConn = DBHelper.connect2DB(connStr, "itkstu", "student");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            String sqlStr = "SELECT SecurityQuestion, SecurityAnswer FROM LinkedU.users WHERE UserID = ?";
            PreparedStatement stmt = DBConn.prepareStatement(sqlStr);
            stmt.setString(1, user);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                uRes = new ForgotPasswordBean(rs.getString("Email"), rs.getString("SecurityQuestion"),
                        ""+rs.getString("SecurityAnswer"));
            }
                
            rs.close();
            stmt.close();
            return uRes;
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return uRes; 
    }
    
    public boolean changePass(String password, String username) {
         try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            Connection DBConn = DriverManager.getConnection(connStr, "itkstu", "student");
            String temp = "";
            String updateString;
            Statement stmt = DBConn.createStatement();
            updateString = "UPDATE LinkedU.loginInfo SET " 
                    + "Password = '" + password + "' "
                    + "WHERE UserID = '" + username + "'";
            System.out.println("updateString =" + updateString);
            rowCount = stmt.executeUpdate(updateString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount != 0;
    }
}
