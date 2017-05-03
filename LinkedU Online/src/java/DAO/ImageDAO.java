package DAO;

import Model.Account;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.primefaces.model.UploadedFile;
/**
 *
 * @author javaknowledge
 */
public class ImageDAO {
public static int updateImage(String userId, UploadedFile image) throws SQLException, IOException, ClassNotFoundException {
        InputStream i = image.getInputstream();
        Connection DBConn = null;
        PreparedStatement pstmt = null;
        int rowCount = 0;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        try {
            String myDB = "jdbc:derby://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU";// connection string
            DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
            String updateString;
            updateString = "UPDATE LINKEDU.USERIMAGE SET "
                    + "profilepic = ? WHERE USERID = ?";
            System.out.println(updateString + userId + i);
            pstmt = DBConn.prepareStatement(updateString);
            pstmt.setBinaryStream(1, i);
            pstmt.setString(2, userId);
            rowCount += pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        } finally {
            if (DBConn != null) {
                DBConn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return rowCount;
    }
}
