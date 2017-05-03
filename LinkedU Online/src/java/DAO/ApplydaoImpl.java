/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Apply;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author stiwar1
 */
public class ApplydaoImpl implements ApplyDAO {
    
    @Override
    public int createProfile(Apply apply){
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
              String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "INSERT INTO LinkedU.APPLYINFO VALUES ('"
                    + apply.getUniversity()
                    + "','" + apply.getMajor()
                    + "','" + apply.getFirstname()
                    + "','" + apply.getLastname()
                    + "','" + apply.getAge()
                    + "','" + apply.getSex()
                    + "','" + apply.getCitizenship()
                    + "','" + apply.getStreet()
                    + "','" + apply.getPostalCode()
                    + "','" + apply.getCity()
                    + "','" + apply.getEmail()
                    + "','" + apply.getPhone()
                    + "','" + apply.getInfo()
                    + "','" + apply.getExam()
                    + "','" + apply.getScore()
                    + "','" + apply.getHigh_school()
                    + "','" + apply.getHs_address()
                    + "','" + apply.getHs_country()                    
                    + "')";
            
             rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            DBConn.close();
            
        }
        catch( SQLException e){
            System.err.println(e.getMessage());
        
        }
        
        return rowCount;
}
    
    
}
