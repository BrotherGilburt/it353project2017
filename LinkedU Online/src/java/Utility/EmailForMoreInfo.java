/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utility;

import Model.Login;
import Model.Student;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Shivangi
 */
public class EmailForMoreInfo {
    
     public boolean sendEmail(Student studentModel) {
           Login login = new Login();
         
              
        // Recipient's email ID needs to be mentioned.
          String to = studentModel.getEmail();
     System.out.println(to);
//
        // Sender's email ID needs to be mentioned
        String from = "stiwar1@ilstu.edu";

        // Assuming you are sending email from this host
        String host = "m.outlook.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", "m.outlook.com");
       properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587"); // if needed
         properties.setProperty("mail.smtp.auth", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
         session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("stiwar1@ilstu.edu",
                    "myIlstu@1811");
        }
    });

        

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("LINKEDU: Request for more information about you");

            // Send the actual HTML message, as big as you like
            message.setContent("Hi "+studentModel.getFullName()+" A request has been made in LinkedU by "+login.getUserID()+" to have more information about you.<br/>"
                    + "Please check your LinkedU account for more details! <br/><br/>"
                    + "Best Regards,<br/>"
                    + "LinkedU.",
                    "text/html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return true;
    }
       
    
}
