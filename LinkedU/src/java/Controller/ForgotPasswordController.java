/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AccountDB;
import DAO.ForgotPasswordDAO;
import Model.ForgotPassword;
import Model.Login;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.internet.InternetAddress;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Keegan
 */
@ManagedBean
@SessionScoped
public class ForgotPasswordController implements Serializable {

    private ForgotPassword forgotPasswordModel;
    private String errorMessage = "";
    private String confirmMessage = "";

    /**
     * Creates a new instance of ForgotPasswordController
     */
    public ForgotPasswordController() {
        if (forgotPasswordModel == null) {
            forgotPasswordModel = new ForgotPassword();
        }
        errorMessage = "";
        confirmMessage = "";
    }

    /**
     * @return the forgotPasswordModel
     */
    public ForgotPassword getForgotPasswordModel() {
        return forgotPasswordModel;
    }

    /**
     * @param forgotPasswordModel the forgotPasswordModel to set
     */
    public void setForgotPasswordModel(ForgotPassword forgotPasswordModel) {
        this.forgotPasswordModel = forgotPasswordModel;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the confirmMessage
     */
    public String getConfirmMessage() {
        return confirmMessage;
    }

    /**
     * @param confirmMessage the confirmMessage to set
     */
    public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
    }

    public String genRandomString() {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder genString = new StringBuilder();
        Random rnd = new Random();
        while (genString.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * CHARS.length());
            genString.append(CHARS.charAt(index));
        }
        return genString.toString();
    }

    public String sendEmail() throws ClassNotFoundException, SQLException {
        Login loginModel = new Login();
        Login check = AccountDB.getLogin(loginModel);

        if (check == null) {
            errorMessage = "Email could not be found!";
        } else {
            errorMessage = "";
            // Recipient's email ID needs to be mentioned.
            String to = forgotPasswordModel.getEmail();

            // Sender's email ID needs to be mentioned
            String from = "kssuth1@ilstu.edu";

            // Assuming you are sending email from this host
            String host = "outlook.office365.com";

            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.port", "587");
            // Get the default Session object.
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("kssuth1@ilstu.edu", "230894Ksuth");
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
                message.setSubject("LinkedU Reset Password");

                // Send the actual HTML message, as big as you like
                forgotPasswordModel.setGenString(genRandomString());
                message.setContent("<p>Click the link to reset your password. -> "
                        + "http://localhost:8080/LinkedU/faces/newPassword.xhtml?"
                        + "email=" + forgotPasswordModel.getEmail()
                        + "&genString=" + forgotPasswordModel.getGenString()
                        + "</p>",
                        "text/html");

                // Send message
                Transport.send(message);
                confirmMessage = "Email sent successfully!";
                errorMessage = "";
            } catch (MessagingException mex) {
                mex.printStackTrace();
                errorMessage = "Email failed to send.";
                confirmMessage = "";
                ForgotPasswordDAO.deleteGenString(forgotPasswordModel);
            }

            //Insert info into database
            ForgotPasswordDAO.setGenString(forgotPasswordModel);
        }
        return "forgotPassword.xhtml?faces-redirect=true";
    }

    public String changePassword() throws ClassNotFoundException, SQLException {
        confirmMessage = "";
        errorMessage = "";
        if (forgotPasswordModel.getNewPassword().equals(forgotPasswordModel.getConfirmNewPassword())) {
            int query = ForgotPasswordDAO.changePassword(forgotPasswordModel);
            if (query == 0) {
                errorMessage = "Failed to change password.";
            } else if (query != 0) {
                confirmMessage = "Password change successful!";

                return "login.xhtml?faces-redirect=true";
            } else {
                confirmMessage = "";
                errorMessage = "";
            }
        } else {
            errorMessage = "Password and Confirm Password do not match!";
        }
        return "newPassword.xhtml?faces-redirect=true";
    }
}
