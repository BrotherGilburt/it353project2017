/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.AccountController;
import DAO.AccountDB;
import DAO.PaymentDAO;
import java.sql.Date;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Sivanu Happy
 */
@ManagedBean(name = "Premium")
@SessionScoped
public class Premium {

    private String userId;
    private AccountController account;
    private String premiumStatus = "";
    private String paymentType;
    private double amount;
    private Date subdate;
    private Date expdate;
    private double weekamount = 10.00;
    private double monthamount = 25.00;

    public Premium() {
         if (account == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            account = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{accountController}", AccountController.class);
        }
    }

    public Premium(String userId, String premiumStatus, String paymentType, Double amount, Date subdate, Date expdate) {
        this.userId = userId;
        this.premiumStatus = premiumStatus;
        this.paymentType = paymentType;
        this.amount = amount;
        this.subdate = subdate;
        this.expdate = expdate;
    }

    public String getPremiumStatus() {
        return premiumStatus;
    }

    public void setPremiumStatus(String premiumStatus) {
        this.premiumStatus = premiumStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getSubdate() {
        return subdate;
    }

    public void setSubdate(Date subdate) {
        this.subdate = subdate;
    }

    public Date getExpdate() {
        return expdate;
    }

    public void setExpdate(Date expdate) {
        this.expdate = expdate;
    }

    /**
     * @return the weekamount
     */
    public double getWeekamount() {
        return weekamount;
    }

    /**
     * @param weekamount the weekamount to set
     */
    public void setWeekamount(double weekamount) {
        this.weekamount = weekamount;
    }

    /**
     * @return the monthamount
     */
    public double getMonthamount() {
        return monthamount;
    }

    /**
     * @param monthamount the monthamount to set
     */
    public void setMonthamount(double monthamount) {
        this.monthamount = monthamount;
    }

    public String pay() throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();  
        if (PaymentDAO.isUserID(account.getLoginModel().getUserID())) {      
           context.addMessage(null, new FacesMessage("Unsuccessful:",  "You have already subscribed") );
             return "home.xhtml?faces-redirect=true";
        }
       
        String userIds = account.getLoginModel().getUserID();
        System.out.println(getUserId());   
        int pay = PaymentDAO.setRecord(userIds, weekamount, "W");
        if (pay != 0) {
            context.addMessage(null, new FacesMessage("Successful",  "Payment is successful!!") );
        } else {
             context.addMessage(null, new FacesMessage("Payment failed", "Please try again!!!"));
        }
        return "home.xhtml?faces-redirect=true";
    }

    public String mpay() throws SQLException {
         FacesContext context = FacesContext.getCurrentInstance();  
        if (PaymentDAO.isUserID(account.getLoginModel().getUserID())) {      
           context.addMessage(null, new FacesMessage("Unsuccessful:",  "You have already subscribed") );
            return "home.xhtml?faces-redirect=true";
        }
        String userIds = account.getLoginModel().getUserID();
        int pay = PaymentDAO.setRecord(userIds, monthamount, "M");
        System.out.println(pay);
         if (pay != 0) {
            context.addMessage(null, new FacesMessage("Successful",  "Payment is successful!!") );
        } else {
            context.addMessage(null, new FacesMessage("Payment failed", "Please try again!!!"));
        }
         return "home.xhtml?faces-redirect=true";
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
