/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ForgotPassword;
import java.io.Serializable;
import java.util.Properties;
import javax.annotation.ManagedBean;
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
public class ForgotPasswordController implements Serializable{

    private ForgotPassword forgotPasswordModel;
    private String errorMessage;
    private String confirmMessage;
    
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
        String host = "smtp.ilstu.edu";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.user", "kssuth1"); // if needed
        properties.setProperty("mail.password", "230894Ksuth"); // if needed

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("LinkedU Password Reset");

            // Send the actual HTML message, as big as you like
            message.setContent("<h1>Click the link below to reset your password.</h1>",
                    "text/html");

            // Send message
            Transport.send(message);
            confirmMessage = "Email sent successfully!";
        } catch (MessagingException mex) {
            mex.printStackTrace();
            errorMessage = "Email failed to send.";
        }
        return "forgotPassword.xhtml";
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
