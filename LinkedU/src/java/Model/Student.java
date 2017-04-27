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
public class Student {

    private final String defaultImage = "./Resources/default_student.png";
    private final String defaultMixtape = "none";
    private final String defaultEssay = "none";

    private String userID;
    private String firstName;
    private String lastName;
    private int ACT;
    private int SAT;
    private int PSAT_NMSQT;
    private ArrayList<String> majors;
    private ArrayList<String> universities;
    private String image;
    private String mixtape;
    private String essay;

    /* TO BE ADDED
     IMAGE
     ESSAY
     MIXTAPES*/
    public Student() {
        userID = "";
        ACT = -1;
        SAT = -1;
        PSAT_NMSQT = -1;
        majors = new ArrayList<String>();
        universities = new ArrayList<String>();
        image = defaultImage;
        mixtape = defaultMixtape;
        essay = defaultEssay;
    }

    public Student(String userID) {
        this();
        this.userID = userID;
    }
    
    public String getACTString() {
        if (ACT < 0) return "N/A";
        return Integer.toString(ACT);
    }
    
    public String getSATString() {
        if (SAT < 0) return "N/A";
        return Integer.toString(SAT);
    }
    
    public String getPSAT_NMSQTString() {
        if (PSAT_NMSQT < 0) return "N/A";
        return Integer.toString(PSAT_NMSQT);
    }
    
    public String getUniversitiesString()
    {
        if (universities.isEmpty()) return "N/A";
        
        StringBuilder list = new StringBuilder();
        
        for(int i = 0; i < universities.size(); i++) {
            list.append(universities.get(i));
            if (i != universities.size()-1) list.append(", ");
        }
        return list.toString();
    }
    
        public String getMajorsString()
    {
        if (majors.isEmpty()) return "N/A";
        
        StringBuilder list = new StringBuilder();
        
        for(int i = 0; i < majors.size(); i++) {
            list.append(majors.get(i));
            if (i != majors.size()-1) list.append(", ");
        }
        return list.toString();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getACT() {
        return ACT;
    }

    public void setACT(int ACT) {
        this.ACT = ACT;
    }

    public int getSAT() {
        return SAT;
    }

    public void setSAT(int SAT) {
        this.SAT = SAT;
    }

    public int getPSAT_NMSQT() {
        return PSAT_NMSQT;
    }

    public void setPSAT_NMSQT(int PSAT_NMSQT) {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
