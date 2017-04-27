/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AccountDB;
import Model.Login;
import Model.Account;
import java.io.Serializable;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author IT353S710
 */
@ManagedBean(name = "accountController")
@SessionScoped
public class AccountController implements Serializable {

    private Login loginModel;
    private Account accountModel;

    private Login loginUpdateModel;
    private Account userUpdateModel;

    private String errorMessage;
    private int attempts;
    private String confirm;

    /**
     * Creates a new instance of AccountController
     */
    public AccountController() {
        if (loginModel == null) {
            loginModel = new Login();
        }
        if (accountModel == null) {
            accountModel = new Account();
        }
        if (loginUpdateModel == null) {
            loginUpdateModel = new Login();
        }
        if (userUpdateModel == null) {
            userUpdateModel = new Account();
        }
        errorMessage = " ";
        if (attempts <= 0) {
            attempts = 0;
        }
        confirm = "";
    }

    public void reset() {
        loginModel = new Login();
        accountModel = new Account();
        errorMessage = "";
        confirm = "";
    }

    public String gotoSignUp() {
        reset();
        return "signUp.xhtml?faces-redirect=true";
    }

    public String gotoLogin() {
        reset();
        return "login.xhtml?faces-redirect=true";
    }

    public String gotoUpdate() {

        loginUpdateModel = new Login();
        userUpdateModel = new Account();
        loginUpdateModel.setUserID(loginModel.getUserID());
        loginUpdateModel.setPassword("");
        userUpdateModel.setAccountType(accountModel.getAccountType());
        userUpdateModel.setEmail(accountModel.getEmail());
        userUpdateModel.setSecurityQuestion(accountModel.getSecurityQuestion());
        userUpdateModel.setSecurityAnswer(accountModel.getSecurityAnswer());

        return "update.xhtml?faces-redirect=true";
    }

    public String gotoLoginGood() {
        return "loginGood.xhtml?faces-redirect=true";
    }

    public String logOut() {
        reset();
        return "index.xhtml?faces-redirect=true";
    }

    public String logIn() {

        //Confirm login information correct.
        if (!checkLogin()) {
            return "loginBad.xhtml?faces-redirect=true";
        }

        //Get user information.
        accountModel = AccountDB.getUser(loginModel.getUserID());

        return "home.xhtml?faces-redirect=true";
    }

    /**
     * Should only be called by login method to check if new login attempt
     * valid...
     *
     * @return
     */
    public boolean checkLogin() {
        if (getRemainingAttempts() == 0) {
            errorMessage = "Error. Maximum log in attempts reached. Please try again later.";
            return false;
        } else {
            ++attempts;
        }

        Login check = AccountDB.getLogin(loginModel);

        if (check == null) {
            errorMessage = "Error. UserID not found.";
            return false;
        } else if (!loginModel.saltPassword().equals(check.getPassword())) {
            errorMessage = "Error. Password is invalid.";
            return false;
        } else {
            eraseErrorMessage();
            attempts = 0;
        }

        return true;
    }

    /**
    * Redirects to home page if not logged in.
    */
    public String isLoggedOn() {
        String navi = null;

        Login check = AccountDB.getLogin(loginModel);

        boolean loggedIn;
        
        if (check == null) {
            errorMessage = "Error. UserID not found.";
            loggedIn = false;
        } else if (!loginModel.getPassword().equals(check.getPassword())) {
            errorMessage = "Error. Password is invalid.";
            loggedIn = false;
        } else {
            loggedIn = true;
        }

        if (!loggedIn) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("index.xhtml?faces-redirect=true");
            errorMessage = "Error. Must log in to update account information.";
        }

        return navi;
    }

    public String signUp() {
        eraseErrorMessage();

        //Check password matches confirm.
        if (!loginModel.getPassword().equals(confirm)) {
            errorMessage = "Error. Passwords do not match. Please confirm password.";
            return "signUp.xhtml?faces-redirect=true";
        }

        //See if userID taken.
        if (AccountDB.isUserID(loginModel.getUserID())) {
            errorMessage = "Error. UserID not available. Please enter a new User ID.";
            return "signUp.xhtml?faces-redirect=true";
        }

        loginModel.saltPassword();

        //Insert information into database.
        AccountDB.createUser(accountModel, loginModel);

        //Send confirmation Email.
        confirmationEmail(accountModel.getEmail());

        return "account.xhtml?faces-redirect=true";
    }

    public String update() {
        eraseErrorMessage();

        //Check password matches confirm.
        if (!(loginUpdateModel.getPassword().equals(confirm))) {
            errorMessage = "Error. Passwords do not match. Please confirm password.";
            return "update.xhtml?faces-redirect=true";
        }
        
        if (loginUpdateModel.getPassword().length() != 0 && (loginUpdateModel.getPassword().length() < 2 || loginUpdateModel.getPassword().length() > 24)) {
            errorMessage = "Error. Password must be 2-24 characters in length.";
            return "update.xhtml?faces-redirect=true";
        }
        
        if (!loginUpdateModel.getPassword().equals("") && !loginUpdateModel.getPassword().equals(loginModel.getPassword())) {
            loginModel.setPassword(loginUpdateModel.saltPassword());
        }
        if (!userUpdateModel.getEmail().equals("") && !userUpdateModel.getEmail().equals(accountModel.getEmail())) {
            accountModel.setEmail(userUpdateModel.getEmail());
        }
        if (!userUpdateModel.getSecurityQuestion().equals("") && !userUpdateModel.getSecurityQuestion().equals(accountModel.getSecurityQuestion())) {
            accountModel.setSecurityQuestion(userUpdateModel.getSecurityQuestion());
        }
        if (!userUpdateModel.getSecurityAnswer().equals("") && !userUpdateModel.getSecurityAnswer().equals(accountModel.getSecurityAnswer())) {
            accountModel.setSecurityAnswer(userUpdateModel.getSecurityAnswer());
        }
        
        if (!AccountDB.updateUser(accountModel, loginModel)) {
            errorMessage = "Error. Record could not be updated. Please try again.";
            return "update.xhtml?faces-redirect=true";
        }

        return "account.xhtml?faces-redirect=true";
    }

    private void confirmationEmail(String email) {
        // Recipient's email ID needs to be mentioned.
        String to = email;

        // Sender's email ID needs to be mentioned
        String from = "pdkaufm@ilstu.edu";

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
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("pdkaufm@ilstu.edu", "Gilid1991x");
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
            message.setSubject("Account Created!");

            BodyPart messageBodyPart = new MimeBodyPart();
            MimeMultipart multipart = new MimeMultipart("related");

            // Send the actual HTML message, as big as you like
            String contentString = "<h2>Congratulations!</h2> <b>Your account with"
                    + " the following information has been created successfully:</b><br/>"
                    + getList() + "<br/><br/><img src=\"cid:image\">";
            messageBodyPart.setContent(contentString, "text/html");
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource("Web\\Resources\\logo.jpg");

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");

            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

    private void eraseErrorMessage() {
        errorMessage = " ";
    }

    public String getEcho() {
        return "<h3>Thanks " + "! Your account has been created/updated successfully.</h3>" + getList();
    }

    public String getList() {
        return "<br/><b>UserID:</b> " + loginModel.getUserID()
                + "<br/><b>Email:</b> " + accountModel.getEmail()
                + "<br/><b>Account Type:</b> " + accountModel.getAccountType()
                + "<br/><b>Security Question:</b> " + accountModel.getSecurityQuestion()
                + "<br/><b>Security Answer:</b> " + accountModel.getSecurityAnswer();
    }

    public Login getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(Login loginModel) {
        this.loginModel = loginModel;
    }

    public Account getAccountModel() {
        return accountModel;
    }

    public void setAccountModel(Account accountModel) {
        this.accountModel = accountModel;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public int getRemainingAttempts() {
        return 3 - attempts;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public Login getLoginUpdateModel() {
        return loginUpdateModel;
    }

    public void setLoginUpdateModel(Login loginUpdateModel) {
        this.loginUpdateModel = loginUpdateModel;
    }

    public Account getUserUpdateModel() {
        return userUpdateModel;
    }

    public void setUserUpdateModel(Account userUpdateModel) {
        this.userUpdateModel = userUpdateModel;
    }

}
