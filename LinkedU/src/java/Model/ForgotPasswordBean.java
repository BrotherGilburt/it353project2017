/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Keegan
 */
public class ForgotPasswordBean {
    private String userAnsw;
    private String newPass;
    private String confNewPass;

    /**
     * @return the userAnsw
     */
    public String getUserAnsw() {
        return userAnsw;
    }

    /**
     * @param userAnsw the userAnsw to set
     */
    public void setUserAnsw(String userAnsw) {
        this.userAnsw = userAnsw;
    }

    /**
     * @return the newPass
     */
    public String getNewPass() {
        return newPass;
    }

    /**
     * @param newPass the newPass to set
     */
    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    /**
     * @return the confNewPass
     */
    public String getConfNewPass() {
        return confNewPass;
    }

    /**
     * @param confNewPass the confNewPass to set
     */
    public void setConfNewPass(String confNewPass) {
        this.confNewPass = confNewPass;
    }
}
