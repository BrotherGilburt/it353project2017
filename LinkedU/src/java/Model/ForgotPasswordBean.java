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
    private String email;
    private String question;
    private String answer;
    private String userAnsw;
    private String newPass;
    private String confNewPass;
    private String messageType;

    public ForgotPasswordBean() {
        
    }
    public ForgotPasswordBean(String email, String question, String answer) {
        this.email = email;
        this.question = question;
        this.answer = answer;
    }
    
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

    /**
     * @return the messageType
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
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