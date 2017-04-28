/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.StudentDAO;
import Model.Student;
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

    ArrayList<Student> studentsList;
    ArrayList<University> universitiesList;
    ArrayList<String> modeOptions;
    ArrayList<String> filterOptions;
    ArrayList<String> studentOptions;
    ArrayList<String> universityOptions;
    String searchMode;
    String searchFilter;
    String searchText;

    /**
     * Creates a new instance of SearchController
     */
    public SearchController() {
        if (studentsList == null) {
            studentsList = new ArrayList<Student>();
        }
        if (universitiesList == null) {
            universitiesList = new ArrayList<University>();
        }
        this.searchMode = "none";
        this.searchFilter = "none";
        this.searchText = "";

        modeOptions = new ArrayList();
        modeOptions.add("students");
        modeOptions.add("universisties");

        filterOptions = new ArrayList();

        studentOptions = new ArrayList();
        studentOptions.add("name containing");
        studentOptions.add("ACT score >");
        studentOptions.add("SAT score >");
        studentOptions.add("PSAT/NMSQT score >");
        studentOptions.add("desired university");
        studentOptions.add("desired major");

        universityOptions = new ArrayList();
        universityOptions.add("name containing");
        universityOptions.add("available major");
    }

    /**
     * Process the search mode.
     *
     * @return
     */
    public String search() {

        if (searchMode.equals("students")) {
            return studentSearch();
        } else if (searchMode.equals("universities")) {
            return universitySearch();
        } else {
            //error
            return "error.xhtml?faces-redirect=true";
        }
    }

    /**
     * Process student search based off of selected filter.
     *
     * @return
     */
    public String studentSearch() {
        if (searchFilter.equals("name containing")) {
            studentsList = StudentDAO.getStudentsByName(searchText);
        } else if (searchFilter.equals("ACT score >")) {
            studentsList = StudentDAO.getStudentsByName(searchText); //CHANGE THIS
        } else if (searchFilter.equals("SAT score >")) {
            studentsList = StudentDAO.getStudentsByName(searchText); //CHANGE THIS
        } else if (searchFilter.equals("PSAT/NMSQT score >")) {
            studentsList = StudentDAO.getStudentsByName(searchText); //CHANGE THIS
        } else if (searchFilter.equals("desired university")) {
            studentsList = StudentDAO.getStudentsByName(searchText); //CHANGE THIS
        } else if (searchFilter.equals("desired major")) {
            studentsList = StudentDAO.getStudentsByName(searchText); //CHANGE THIS
        }

        return "studentSearch.xhtml";
    }

    /**
     * Process University search based off of selected filter.
     *
     * @return
     */
    public String universitySearch() {
        if (searchFilter.equals("name containing")) {
            ; //database call
        } else if (searchFilter.equals("available major")) {
            ; //database call
        }
        return "universitySearch.xhtml";
    }

    public boolean isACTFilter() {
        return searchFilter.equals("ACT score >");
    }

    public boolean isSATFilter() {
        return searchFilter.equals("SAT score >");
    }

    public boolean isPSAT_NMSQTFilter() {
        return searchFilter.equals("PSAT/NMSQT score >");
    }

    public void selectFilter() {
        if (searchMode.equals("none")) {
            filterOptions = new ArrayList();
        } else if (searchMode.equals("students")) {
            filterOptions = studentOptions;
        } else if (searchMode.equals("universities")) {
            filterOptions = universityOptions;
        } else //Error
        {
            filterOptions = new ArrayList();
        }
    }

    public String tableDescription() {
        StringBuilder description = new StringBuilder();

        if (searchMode.equals("students")) {
            description.append("Students with ");
            description.append(searchFilter);
            description.append(" \"");
            description.append(searchText);
            description.append("\".");

        } else if (searchMode.equals("universities")) {
            description.append("Universities with ");
            description.append(searchFilter);
            description.append(" \"");
            description.append(searchText);
            description.append("\".");
        } else {
            return "Error building table description.";
        }

        return description.toString();
    }

    public ArrayList<Student> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(ArrayList<Student> studentsList) {
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

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public ArrayList<String> getStudentOptions() {
        return studentOptions;
    }

    public void setStudentOptions(ArrayList<String> studentOptions) {
        this.studentOptions = studentOptions;
    }

    public ArrayList<String> getUniversityOptions() {
        return universityOptions;
    }

    public void setUniversityOptions(ArrayList<String> universityOptions) {
        this.universityOptions = universityOptions;
    }

    public ArrayList<String> getModeOptions() {
        return modeOptions;
    }

    public void setModeOptions(ArrayList<String> modeOptions) {
        this.modeOptions = modeOptions;
    }

    public ArrayList<String> getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(ArrayList<String> filterOptions) {
        this.filterOptions = filterOptions;
    }

}
