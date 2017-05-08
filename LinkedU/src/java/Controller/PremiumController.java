/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Model.Premium;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Sivanu Happy
 */
@ManagedBean
@SessionScoped
public class PremiumController implements Serializable {
    private Premium premiumModel;
    /**
     * Creates a new instance of PremiumController
     */
    public PremiumController() {
if(premiumModel == null){
    premiumModel = new Premium();
}
    }
    public String selectWeek(){
        return "weekplan.xhtml?faces-redirect=true";
    }
    public String selectMonth(){
        return "monthlyPlan.xhtml?faces-redirect=true";
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

}
