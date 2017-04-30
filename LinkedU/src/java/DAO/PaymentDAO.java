/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Model.Premium;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author Sivanu Happy
 */
public class PaymentDAO {
     
    public static int setRecord(String userid,double payamount) throws SQLException{
        Premium preModel = new Premium();
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
         c.setTime(currentDate);
         c.add(Calendar.DATE,7);
         Date endDate = c.getTime();
        //java.sql.Date currentDate = new java.sql.Date(c.getTime().getTime());
        //Date endDate = calendar.add(Calendar.DATE, 7);
        
        
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
            String insertString = "INSERT INTO LINKEDU.PREMIUM  VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = DBConn.prepareStatement(insertString);
            ps.setString(1, userid);
            ps.setString(2, "P");
            ps.setString(3, "W");
            ps.setDouble(4, payamount);
            java.sql.Date currentsqlDate = new java.sql.Date(currentDate.getTime());
            ps.setDate(5, currentsqlDate);
            java.sql.Date endsqlDate = new java.sql.Date(endDate.getTime());
            ps.setDate(6, endsqlDate);
            System.out.println(insertString + " " + preModel.getWeekamount() + " " + currentDate + " " + userid );
            rowCount = ps.executeUpdate();
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }
}
