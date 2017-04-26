/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ForgotPasswordDB;
import Model.ForgotPasswordBean;
import java.io.Serializable;
import java.util.Properties;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Keegan
 */
@Named(value = "forgotPasswordController")
@Dependent
public class ForgotPasswordController implements Serializable {

    private ForgotPasswordBean theModel;
    private String userName;
    private String email;
    private String errorMessage;

    /**
     * Creates a new instance of ForgotPasswordController
     */
    public ForgotPasswordController() {
        theModel = new ForgotPasswordBean();
    }

    public void sendEmail() {
        final String username = "linkeduapp@gmail.com";
        final String password = "student123";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("linkeduapp@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("keegan94@gmail.com"));
            message.setSubject("LinkedU - Password Reset");
            message.setText("Click the link to reset your password.\n\n" +
                    "<a href=\"http://localhost:8080/LinkedU/faces/forgotPassword.xhtml?userid=" + userName + "\">Reset Password</a>");

            Transport.send(message);

            System.out.println("Done");
            errorMessage = "Email send!";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return the theModel
     */
    public ForgotPasswordBean getTheModel() {
        return theModel;
    }

    /**
     * @param theModel the theModel to set
     */
    public void setTheModel(ForgotPasswordBean theModel) {
        this.theModel = theModel;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
    
    public String accountByUsername() {
        ForgotPasswordDB forgotPassword = new ForgotPasswordDB();
        theModel = forgotPassword.lostPass(userName);        

        if (theModel != null) {
            sendEmail();
            return "forgotPassword.xhtml";
        }                     
        else {
            errorMessage = "Account does not exist!";
            return "forgotPassword.xhtml";
        }
    }
    
    public String changePass() {
        ForgotPasswordDB forgotPassword = new ForgotPasswordDB(); 
        if (!theModel.getAnswer().equals(theModel.getUserAnsw())){
           errorMessage = "Incorrect answer!";
           return "";
        }
        if (!theModel.getNewPass().equals(theModel.getConfNewPass())){
            errorMessage = "New passwords do not match!";
            return "";
        }
        forgotPassword.changePass(theModel.getNewPass(), userName);        
        
        return "index.xhtml"; 
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
