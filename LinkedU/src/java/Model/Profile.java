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

    private String userID;
    private String ACT;
    private String SAT;
    private String PSAT_NMSQT;
    private ArrayList<String> majors;
    private ArrayList<String> universities;
    private boolean image;

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
        image = false;
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

    public boolean getImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

}
