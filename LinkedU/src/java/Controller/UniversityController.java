/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ImageDAO;
import DAO.StudentDAO;
import DAO.UniversityDAO;
import Model.Account;
import Model.Login;
import Model.Premium;
import Model.Student;
import Model.University;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Part;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class UniversityController implements Serializable {

    private University myUniversityModel;
    private AccountController account;
    private University viewUniversityModel;
    private UploadedFile resume;
    private Account accountModel;
    private ArrayList<University> featuredList;
    private SearchController search;
    private String name;
    

    /**
     * Creates a new instance of UniversityController
     */
    public UniversityController() {
        if (accountModel == null) {
            accountModel = new Account();
        }
        if (account == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            account = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{accountController}", AccountController.class);
        }

        if (myUniversityModel == null) {
            myUniversityModel = new University(account.getLoginModel().getUserID());
        }
        if (viewUniversityModel == null) {
            viewUniversityModel = new University();
        }

        if (search == null) {
            FacesContext facesContext1 = FacesContext.getCurrentInstance();
            search = facesContext1.getApplication().evaluateExpressionGet(facesContext1, "#{searchController}", SearchController.class);
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

    public String gotoUpdateImage() throws SQLException, IOException, ClassNotFoundException {
        UploadedFile image = getResume();
        String userid = account.getLoginModel().getUserID();
        System.out.println(userid);
        int update = ImageDAO.updateImage(account.getLoginModel().getUserID(), image);
        return "myProfile.xhtml?faces-redirect=true";
    }

    public String loadMyProfile() {
        myUniversityModel = UniversityDAO.getUniversityByID(account.getLoginModel().getUserID());
        if (myUniversityModel == null) {
            myUniversityModel = new University(account.getLoginModel().getUserID());
        }
        return "myProfile.xhtml?faces-redirect=true";
    }

    public String loadProfile() {
        FacesContext facesContext2 = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext2.getExternalContext().getRequestParameterMap();
        name = params.get("name");
        ArrayList<University> list = search.getUniversitiesList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {  
                viewUniversityModel = list.get(i);
                break;
            }
        }
        System.out.println(viewUniversityModel.getName());
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

    /**
     * @return the accountModel
     */
    public Account getAccountModel() {
        return accountModel;
    }

    /**
     * @param accountModel the accountModel to set
     */
    public void setAccountModel(Account accountModel) {
        this.accountModel = accountModel;
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

    /**
     * @return the featuredList
     */
    public ArrayList<University> getFeaturedList() {
        ArrayList featuredList = UniversityDAO.getFeature();
        return featuredList;
    }

    /**
     * @param featuredList the featuredList to set
     */
    public void setFeaturedList(ArrayList<University> featuredList) {
        this.featuredList = featuredList;
    }
}
