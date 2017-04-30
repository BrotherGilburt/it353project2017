/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.PaymentDAO;
import java.sql.Date;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Sivanu Happy
 */
@ManagedBean(name = "Premium")
@SessionScoped
public class Premium {
    private String userId;
    private University theModel;
    private String premiumStatus = "";
    private String paymentType;
    private double amount;
    private Date subdate;
    private Date expdate;
    private double weekamount = 10.00;
    private double monthamount = 25.00;
    

    public Premium() {
       if (theModel == null) {
            theModel = new University();
        }
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

     public String pay() throws SQLException{
        int pay = PaymentDAO.setRecord(userId,weekamount);
        return "premium.xhtml?faces-redirect=true";
    }
     
    public String mpay() throws SQLException{
        int pay = PaymentDAO.setRecord(userId,monthamount);
        return "premium.xhtml?faces-redirect=true";
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
