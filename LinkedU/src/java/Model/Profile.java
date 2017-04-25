/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author IT353S710
 */
public class Profile {
    private final String defaultImage = "./Resources/default_student.png";
    
    private String userID;
    private String ACT;
    private String SAT;
    private String PSAT_NMSQT;
    private ArrayList<String> majors;
    private ArrayList<String> universities;
    private String image;
    private String mixtape;
    private String essay;

    /* TO BE ADDED
     IMAGE
     ESSAY
     MIXTAPES*/
    
    public Profile() {
        userID = "";
        ACT = "N/A";
        SAT = "N/A";
        PSAT_NMSQT = "N/A";
        majors = new ArrayList<String>();
        universities = new ArrayList<String>();
        image = defaultImage;
        mixtape = null;
        essay = null;
    }
    
    public Profile(String userID) {
        this();
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getACT() {
        return ACT;
    }

    public void setACT(String ACT) {
        this.ACT = ACT;
    }

    public String getSAT() {
        return SAT;
    }

    public void setSAT(String SAT) {
        this.SAT = SAT;
    }

    public String getPSAT_NMSQT() {
        return PSAT_NMSQT;
    }

    public void setPSAT_NMSQT(String PSAT_NMSQT) {
        this.PSAT_NMSQT = PSAT_NMSQT;
    }

    public ArrayList<String> getMajors() {
        return majors;
    }

    public void setMajors(ArrayList<String> Majors) {
        this.majors = Majors;
    }

    public ArrayList<String> getUniversities() {
        return universities;
    }

    public void setUniversities(ArrayList<String> Universities) {
        this.universities = Universities;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMixtape() {
        return mixtape;
    }

    public void setMixtape(String mixtape) {
        this.mixtape = mixtape;
    }

    public String getEssay() {
        return essay;
    }

    public void setEssay(String essay) {
        this.essay = essay;
    }

}
