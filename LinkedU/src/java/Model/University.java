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
public class University {
    String userID;
    boolean premium; //denotes that university has payed extra to be featured on home page.
    String name;
    ArrayList<String> majors;
    String street;
    String city;
    String state;
    String zip;
    String image;

    public University() {
        this.userID = "";
        this.premium = false;
        this.name = "N/A";
        this.majors = new ArrayList();
        this.street = "N/A";
        this.city = "N/A";
        this.state = "N/A";
        this.zip = "N/A";
        this.image = "./Resources/default_university.png";
    }
    
    public University(String userID) {
        this();
        this.userID = userID;
    }
    
    /**
     * Formats the address into a single string.
     * @return formatted address
     */
    public String getFullAddress() {
        if (street.equals("N/A") || city.equals("N/A") || state.equals("N/A") || zip.equals("N/A"))
            return "Not Provided";
        return street + ", " + city + ", " + state + " " + zip;
    }
    
    public String getMajorsString() {
        if (majors.isEmpty()) return "N/A";
        
        StringBuilder list = new StringBuilder();
        
        for (int i = 0; i < majors.size(); i++) {
            list.append(majors.get(i));
            if (i + 1 < majors.size()) list.append(", ");
        }
        
        return list.toString();
    }
    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getName() {
        System.out.println("GET: " + name);
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getMajors() {
        return majors;
    }

    public void setMajors(ArrayList<String> majors) {
        this.majors = majors;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public void addMajor(String major) {
        majors.add(major);
    }
    
}
