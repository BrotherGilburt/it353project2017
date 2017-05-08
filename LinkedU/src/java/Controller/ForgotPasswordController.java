/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ForgotPasswordDAO;
import Model.Account;
import Model.ForgotPassword;
import Model.Login;
import Model.TextSender;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.internet.InternetAddress;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Keegan
 */
@ManagedBean
@RequestScoped
public class ForgotPasswordController implements Serializable {

    private ForgotPassword forgotPasswordModel;
    private java.util.List<java.lang.String> cellPhoneCarriers;
    private String cellPhoneCarrierChosen;
    private String phone;
    private String sentStatus;
    private String passwordError;

    /**
     * Creates a new instance of ForgotPasswordController
     */
    public ForgotPasswordController() {
        if (forgotPasswordModel == null) {
            forgotPasswordModel = new ForgotPassword();
        }
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

    public List<String> getCellPhoneCarriers() {
        cellPhoneCarriers = TextSender.getCarriers();
        return cellPhoneCarriers;
    }

    public void setCellPhoneCarriers(List<String> cellPhoneCarriers) {
        this.cellPhoneCarriers = cellPhoneCarriers;
    }

    /**
     * @return the cellPhoneCarrierChosen
     */
    public String getCellPhoneCarrierChosen() {
        return cellPhoneCarrierChosen;
    }

    /**
     * @param cellPhoneCarrierChosen the cellPhoneCarrierChosen to set
     */
    public void setCellPhoneCarrierChosen(String cellPhoneCarrierChosen) {
        this.cellPhoneCarrierChosen = cellPhoneCarrierChosen;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the sentStatus
     */
    public String getSentStatus() {
        return sentStatus;
    }

    /**
     * @param sentStatus the sentStatus to set
     */
    public void setSentStatus(String sentStatus) {
        this.sentStatus = sentStatus;
    }

    /**
     * @return the passwordError
     */
    public String getPasswordError() {
        return passwordError;
    }

    /**
     * @param passwordError the passwordError to set
     */
    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
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

    public void sendEmail() throws ClassNotFoundException, SQLException {
        Account accountModel = new Account();
        accountModel.setEmail(forgotPasswordModel.getEmail());
        Login check = ForgotPasswordDAO.findUserID(accountModel, forgotPasswordModel);

        if (check != null) {
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
                        + "http://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU/faces/newPassword.xhtml?"
                        + "email=" + forgotPasswordModel.getEmail()
                        + "&genString=" + forgotPasswordModel.getGenString()
                        + "</p>",
                        "text/html");

                // Send message
                Transport.send(message);
                sentStatus = "Email sent successfully!";
            } catch (MessagingException mex) {
                mex.printStackTrace();
                sentStatus = "Email failed to send.";
                ForgotPasswordDAO.deleteGenString(forgotPasswordModel);
            }

            //Insert info into database
            ForgotPasswordDAO.setGenStringDB(forgotPasswordModel);
        } else {
            sentStatus = "Email could not be found!";
        }
    }

    public void sendSMS() throws ClassNotFoundException, SQLException {
        Account accountModel = new Account();
        accountModel.setEmail(forgotPasswordModel.getEmail());
        Login check = ForgotPasswordDAO.findUserID(accountModel, forgotPasswordModel);
        if (check != null) {
            forgotPasswordModel.setGenString(genRandomString());
            //Insert info into database
            ForgotPasswordDAO.setGenStringDB(forgotPasswordModel);
            TextSender.sendSMS(cellPhoneCarrierChosen, phone,
                    "Click the link to reset your password. -> "
                    + "http://gfish2.it.ilstu.edu/kssuth1_Sp2017_LinkedU/faces/newPassword.xhtml?"
                    + "email=" + forgotPasswordModel.getEmail()
                    + "&genString=" + forgotPasswordModel.getGenString());
            sentStatus = " Sent! (to " + phone + " on " + cellPhoneCarrierChosen + ")";
        } else {
            sentStatus = "Email could not be found!";
        }
    }

    public void changePassword() throws ClassNotFoundException, SQLException {
        if (forgotPasswordModel.getNewPassword().equals(forgotPasswordModel.getConfirmNewPassword())) {
            forgotPasswordModel.setNewPassword(Login.generateHash(forgotPasswordModel.getNewPassword()));

            Account accountModel = new Account();
            accountModel.setEmail(forgotPasswordModel.getEmail());
            Login check = ForgotPasswordDAO.findUserID(accountModel, forgotPasswordModel);
            if (check != null) {
                int query = ForgotPasswordDAO.changePassword(forgotPasswordModel);
                if (query == 0) {
                    passwordError = "Failed to change password.";
                } else {
                    passwordError = "Password change successful!";
                    ForgotPasswordDAO.deleteGenString(forgotPasswordModel);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
                    nav.performNavigation("login.xhtml?faces-redirect=true");
                }
            } else {
                    passwordError = "Email could not be found!";
            }
        } else {
            passwordError = "Password and Confirm Password do not match!";
        }
    }

    public String urlOK() throws ClassNotFoundException, SQLException {
        String navi = null;
        ForgotPassword check = ForgotPasswordDAO.findGenString(forgotPasswordModel);
        if (check == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("pageExpired.xhtml?faces-redirect=true");
        }
        return navi;
    }
}
