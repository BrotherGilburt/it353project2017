/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.PaymentDAO;
import Model.Premium;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Sivanu Happy
 */
@ManagedBean
@SessionScoped
public class AdminController implements Serializable {
private Premium premiumModel;
 ArrayList<Premium> premiumList;
 private ArrayList<Premium> expireList;
 private List<Premium> selectedPremium;


    /**
     * Creates a new instance of AdminController
     */
    public AdminController() {
        if(premiumModel == null){
            premiumModel = new Premium();
        }
    }
    public String login(){
        return "adminLogin.xhtml?faces-redirect=true";
    }
    public String search() throws SQLException{
      String resultPage = "";  
        if (premiumModel.getPremiumStatus().equals("P")){
            premiumList = PaymentDAO.getRecords("P");
            resultPage = "premiumSearch.xhtml?faces-redirect=true";
            //premiumModel = (Premium) record.get(0);
        }
        else if(premiumModel.getPremiumStatus().equals("S"))
                {
            premiumList = PaymentDAO.getRecords("S");
            resultPage = "showcaseSearch.xhtml?faces-redirect=true";
        }
        
        return resultPage;
    }
  
    public String checkExpire() throws ParseException{
         expireList = PaymentDAO.getExpireRecords();
         return "expireList.xhtml?faces-redirect=true";
    }
    /**
     * @return the premiumModel
     */
    public Premium getPremiumModel() {
        return premiumModel;
    }

    /**
     * @param premiumModel the premiumModel to set
     */
    public void setPremiumModel(Premium premiumModel) {
        this.premiumModel = premiumModel;
    }

    /**
     * @return the premiumList
     */
    public ArrayList<Premium> getPremiumList() {
        return premiumList;
    }

    /**
     * @param premiumList the premiumList to set
     */
    public void setPremiumList(ArrayList<Premium> premiumList) {
        this.premiumList = premiumList;
    }

    /**
     * @return the selectedPremium
     */
    public List<Premium> getSelectedPremium() {
        return selectedPremium;
    }

    /**
     * @param selectedPremium the selectedPremium to set
     */
    public void setSelectedPremium(List<Premium> selectedPremium) {
        this.selectedPremium = selectedPremium;
    }
   public void updatePremium() throws SQLException{
        FacesContext context = FacesContext.getCurrentInstance();    
        String update = PaymentDAO.updateRecord(selectedPremium);
        System.out.println(update);
        if (update.equals("(")) {
            context.addMessage(null, new FacesMessage("Failed",  "Please try again") );
        } else {
             context.addMessage(null, new FacesMessage("Successfully Showcased", update));
        }
   }
   public void deleteExpire() throws SQLException{
       FacesContext context = FacesContext.getCurrentInstance();    
        String delete = PaymentDAO.deleteRecord();
        System.out.println(delete);
        if (delete.equals("Records deleted")) {
           context.addMessage(null, new FacesMessage("Successfully ", delete));
        } else {
             context.addMessage(null, new FacesMessage("Failed",  "No Record Found") );
             
        }
   }

    /**
     * @return the expireList
     */
    public ArrayList<Premium> getExpireList() {
        return expireList;
    }

    /**
     * @param expireList the expireList to set
     */
    public void setExpireList(ArrayList<Premium> expireList) {
        this.expireList = expireList;
    }
}
