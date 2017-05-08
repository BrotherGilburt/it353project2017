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

    private final static String DEFAULT_IMAGE = "./Resources/images/default_student.png";
    private final static String DEFAULT_MIXTAPE = "";
    private final static String DEFAULT_ESSAY = "";

    private String userID;
    private String firstName;
    private String lastName;
    private int ACT;
    private int SAT;
    private int PSAT_NMSQT;
    private ArrayList<String> majors;
    private ArrayList<String> universities;
    private boolean image;
    private String mixtape;
    private String essay;
    private String email;

    public Student() {
        userID = "";
        firstName = "";
        lastName = "";
        ACT = -1;
        SAT = -1;
        PSAT_NMSQT = -1;
        majors = new ArrayList();
        universities = new ArrayList();
        image = false;
        mixtape = DEFAULT_MIXTAPE;
        essay = DEFAULT_ESSAY;
    }

    public Student(String userID) {
        this();
        this.userID = userID;
    }

    public String getFullName() {
        if (firstName.equals("") && lastName.equals("")) {
            return "Name Not Provided";
        }
        return firstName + " " + lastName;
    }

    public String getACTString() {
        if (ACT < 0) {
            return "N/A";
        }
        return Integer.toString(ACT);
    }

    public String getSATString() {
        if (SAT < 0) {
            return "N/A";
        }
        return Integer.toString(SAT);
    }

    public String getPSAT_NMSQTString() {
        if (PSAT_NMSQT < 0) {
            return "N/A";
        }
        return Integer.toString(PSAT_NMSQT);
    }

    public String getUniversitiesString() {
        if (universities.isEmpty()) {
            return "N/A";
        }

        StringBuilder list = new StringBuilder();

        for (int i = 0; i < universities.size(); i++) {
            list.append(universities.get(i));
            if (i != universities.size() - 1) {
                list.append(", ");
            }
        }
        return list.toString();
    }

    public String getMajorsString() {
        if (majors.isEmpty()) {
            return "N/A";
        }

        StringBuilder list = new StringBuilder();

        for (int i = 0; i < majors.size(); i++) {
            list.append(majors.get(i));
            if (i != majors.size() - 1) {
                list.append(", ");
            }
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

    public boolean getImage() {
        return image;
    }

    public void setImage(boolean image) {
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

    
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean isImage() {
        return image;
    }

    public boolean isNoImage() {
        return !image;
    }

    public String getDefaultImage() {
        return DEFAULT_IMAGE;
    }
    
    public boolean checkIfMixtape() {
        return !mixtape.equals(DEFAULT_MIXTAPE);
    }
    
    public boolean checkIfEssay() {
        return !essay.equals(DEFAULT_ESSAY);
    }
}
