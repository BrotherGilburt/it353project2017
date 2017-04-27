/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.StudentProfileDAO;
import Model.Profile;
import Model.University;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author IT353S710
 */
@ManagedBean
@SessionScoped
public class SearchController {

    ArrayList<Profile> studentsList;
    ArrayList<University> universitiesList;
    String searchMode;
    String searchText;

    public ArrayList<Profile> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(ArrayList<Profile> studentsList) {
        this.studentsList = studentsList;
    }

    public ArrayList<University> getUniversitiesList() {
        return universitiesList;
    }

    public void setUniversitiesList(ArrayList<University> universitiesList) {
        this.universitiesList = universitiesList;
    }

    public String getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
    
    /**
     * Creates a new instance of SearchController
     */
    public SearchController() {
        if (studentsList == null) {
            studentsList = new ArrayList<Profile>();
        }
        if (universitiesList == null) {
            universitiesList = new ArrayList<University>();
        }
        String searchMode = "none";
        String searchText = "";
    }

    public String search() {

        if (searchMode.equals("student")) {
            return studentSearch();
        } else {
            return universitySearch();
        }
    }

    public String studentSearch() {
        studentsList = StudentProfileDAO.getStudentsByName(searchText);
        return "studentSearch.xhtml";
    }

    public String universitySearch() {
        //db stuff..
        return "universitySearch.xhtml";
    }
}
