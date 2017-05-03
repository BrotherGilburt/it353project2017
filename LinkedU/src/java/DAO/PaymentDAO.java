/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Premium;
import Model.University;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sivanu Happy
 */
public class PaymentDAO {
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM LinkedU.Premium WHERE userID='" + userID + "'");

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

    public static int setRecord(String userid, double payamount, String type) throws SQLException {
        Date endDate = null, currentDate = null;
        if (type.equals("W")) {
            currentDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.DATE, 7);
            endDate = c.getTime();
        } else if (type.equals("M")) {
            currentDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.DATE, 30);
            endDate = c.getTime();
        }
        Premium preModel = new Premium();
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
            ps.setString(3, type);
            ps.setDouble(4, payamount);
            java.sql.Date currentsqlDate = new java.sql.Date(currentDate.getTime());
            ps.setDate(5, currentsqlDate);
            java.sql.Date endsqlDate = new java.sql.Date(endDate.getTime());
            ps.setDate(6, endsqlDate);
            System.out.println(insertString + " " + preModel.getWeekamount() + " " + currentDate + " " + userid);
            rowCount = ps.executeUpdate();

            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }

    public static ArrayList getRecords(String status) throws SQLException {
        Premium record = null;
        ArrayList<Premium> recordsList = null;
        Connection DBConn = null;

        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/LINKEDU";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
            Statement stmt = DBConn.createStatement();
            String selectQuery = "select * from LINKEDU.PREMIUM WHERE PREMIUMSTATUS = '" + status + "'";

            System.out.println(selectQuery);
            ResultSet rs = stmt.executeQuery(selectQuery);
            while (rs.next()) {
                if (recordsList == null) {
                    recordsList = new ArrayList();
                }
                record = new Premium();
                recordsList.add(record);
                record.setUserId(rs.getString("USERID"));
                record.setPremiumStatus(rs.getString("PREMIUMSTATUS"));
                record.setPaymentType(rs.getString("PAYMENTTYPE"));
                record.setAmount(rs.getDouble("AMOUNT"));
                record.setSubdate(rs.getDate("SUBDATE"));
                record.setExpdate(rs.getDate("EXPDATE"));
                System.out.println(rs.getString("USERID"));
                //theModel = new Premium(userid, premiumstatus, paymenttype, amount, subdate, expdate);
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

    public static String updateRecord(List<Premium> selectedPremium) throws SQLException {
        Connection DBConn = null;
        String useridList = "(";
        int rowCount = 0;
        for (int i = 0; i < selectedPremium.size(); i++) {
            if (i == selectedPremium.size() - 1) {
                useridList += "'" + selectedPremium.get(i).getUserId() + "')";
            } else {
                useridList += "'" + selectedPremium.get(i).getUserId() + "',";
            }
        }
        System.out.println(useridList);
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/LINKEDU";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
            Statement stmt = DBConn.createStatement();
            String updateString = "UPDATE LINKEDU.PREMIUM SET PREMIUMSTATUS = 'S' WHERE USERID IN " + useridList;
            System.out.println(updateString + " " + "S" + updateString);
            rowCount += stmt.executeUpdate(updateString);
            /*-------------------------------*/
            stmt = DBConn.createStatement();
            updateString = "UPDATE LinkedU.Universities SET "
                    + "Premium = TRUE WHERE USERID IN " + useridList;
            rowCount += stmt.executeUpdate(updateString);
            System.out.println(updateString + " " + "S" + updateString);
            /*-----------------------------------*/
            DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return useridList;
    }

    public static ArrayList getExpireRecords() throws ParseException {
        Premium record = null;
        ArrayList<Premium> recordsList = null;
        Connection DBConn = null;
        Date today = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = DATE_FORMAT.format(today);

        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/LINKEDU";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
            Statement stmt = DBConn.createStatement();
            String getString = "select * from LINKEDU.PREMIUM WHERE EXPDATE < '" + currentDate + "'";
            System.out.println(getString);
            ResultSet rs = stmt.executeQuery(getString);
            while (rs.next()) {
                if (recordsList == null) {
                    recordsList = new ArrayList();
                }
                record = new Premium();
                recordsList.add(record);
                record.setUserId(rs.getString("USERID"));
                record.setPremiumStatus(rs.getString("PREMIUMSTATUS"));
                record.setPaymentType(rs.getString("PAYMENTTYPE"));
                record.setAmount(rs.getDouble("AMOUNT"));
                record.setSubdate(rs.getDate("SUBDATE"));
                record.setExpdate(rs.getDate("EXPDATE"));
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

    public static String deleteRecord() throws SQLException {
        int rowCount = 0;
        Connection DBConn = null;
        Date today = new Date();
        String returnString;
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = DATE_FORMAT.format(today);
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/LINKEDU";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");
            Statement stmt = DBConn.createStatement();
            String updateString = "UPDATE LINKEDU.UNIVERSITIES SET PREMIUM = FALSE WHERE USERID IN ("
                    + "SELECT USERID FROM LINKEDU.PREMIUM WHERE EXPDATE < '" + currentDate + "')";
            System.out.println(updateString);
            rowCount += stmt.executeUpdate(updateString);
            String delString = "DELETE FROM LINKEDU.PREMIUM WHERE EXPDATE < '" + currentDate + "'";
            System.out.println(delString);
            rowCount += stmt.executeUpdate(delString);
            DBConn.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if (rowCount == 1) {
            returnString = "Records deleted";
        } else {
            returnString = "Delete Failed";
        }

        return returnString;
    }
}
