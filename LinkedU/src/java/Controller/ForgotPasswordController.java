/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ForgotPassword;
import java.io.Serializable;
import java.util.Properties;
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

    public String sendEmail() {
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
            message.setSubject("Congratulations!");

            // Send the actual HTML message, as big as you like
            message.setContent("<h1>You have received an auto-generated email!</h1>",
                    "text/html");

            // Send message
            Transport.send(message);
            confirmMessage = "Email sent successfully!";
        } catch (MessagingException mex) {
            mex.printStackTrace();
            errorMessage = "Email failed to send.";
        }
        return "forgotPassword.xhtml?faces-redirect=true";
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
}
