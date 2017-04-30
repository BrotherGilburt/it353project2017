/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.StudentDAO;
import DAO.UniversityDAO;
import Model.Student;
import Model.University;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

@Named(value = "universityController")
@SessionScoped
public class UniversityController implements Serializable {

    private University myUniversityModel;
    private AccountController account;
    private University viewUniversityModel;
    private SearchController search;
    private UploadedFile resume;
    
            

    /**
     * Creates a new instance of UniversityController
     */
    public UniversityController() {
        if (account == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            account = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{accountController}", AccountController.class);
        }
                
        if (myUniversityModel == null) {
            //myUniversityModel = new University();
            myUniversityModel = new University(account.getLoginModel().getUserID());
        }
        if (viewUniversityModel == null) {
            viewUniversityModel = new University();
        }

        if (search == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            search = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{searchController}", SearchController.class);
        }
    }

    /**
     * Called upon updating or creating a university for an account in the
     * Database.
     *
     * @return the address of the university profile.
     */
    public String setUniversity() throws IOException {
        //Call DAO to create/update a university

        UniversityDAO.createUniversity(myUniversityModel);

        return ""; //address of university profile.
    }
  public String gotoUpdateImage() throws SQLException, IOException {
        UploadedFile image = getResume();
        int update = UniversityDAO.updateImage(myUniversityModel, image);
        return "universityProfile.xhtml?faces-redirect=true";
    }
    public String loadProfile() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        String name = params.get("name");
        
        ArrayList<University> list = search.getUniversitiesList();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                viewUniversityModel = list.get(i);
                break;
            }
        }
        
        return "universityProfile.xhtml?faces-redirect=true";
    }

    public static ArrayList<University> getAllUniversities() {
        return UniversityDAO.getAllUniversities();
    }

    /**
     * Returns a list of all University names.
     *
     * @return ArrayList of names.
     */
    public static ArrayList<String> getAllUniversityNames() {
        ArrayList<String> stringList = new ArrayList();

        ArrayList<University> list = UniversityDAO.getAllUniversities();

        for (int i = 0; i < list.size(); i++) {
            if (!stringList.contains(list.get(i).getName())) {
                stringList.add(list.get(i).getName());
            }
        }

        Collections.sort(stringList);

        return stringList;
    }

    /**
     * Returns a list of all majors.
     *
     * @return ArrayList of majors.
     */
    public static ArrayList<String> getAllMajors() {
        ArrayList<String> stringList = new ArrayList();

        ArrayList<University> list = UniversityDAO.getAllUniversities();

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getMajors().size(); j++) {
                if (!stringList.contains(list.get(i).getMajors().get(j))) {
                    stringList.add(list.get(i).getMajors().get(j));
                }
            }
        }

        Collections.sort(stringList);

        return stringList;
    }

    /**
     * Returns a list of all majors for a particular university.
     *
     * @param name
     * @return
     */
    public static ArrayList<String> getUniversityMajors(String name) {
        ArrayList<String> list = UniversityDAO.getUniversityByName(name).getMajors();

        Collections.sort(list);

        return list;
    }

    public University getMyUniversityModel() {
        return myUniversityModel;
    }

    public void setMyUniversityModel(University myUniversityModel) {
        this.myUniversityModel = myUniversityModel;
    }

    public University getViewUniversityModel() {
        return viewUniversityModel;
    }

    public void setViewUniversityModel(University viewUniversityModel) {
        this.viewUniversityModel = viewUniversityModel;
    }

    /**
     * @return the resume
     */
    public UploadedFile getResume() {
        return resume;
    }

    /**
     * @param resume the resume to set
     */
    public void setResume(UploadedFile resume) {
        this.resume = resume;
    }

}
