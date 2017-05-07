/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ImageDAO;
import DAO.UniversityDAO;
import Model.Account;
import Model.Apply;
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
import javax.faces.event.ValueChangeEvent;
import javax.mail.Part;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class UniversityController implements Serializable {

    /**
     * @return the nameUniv2
     */
    public University getNameUniv2() {
        return nameUniv2;
    }

    /**
     * @param nameUniv2 the nameUniv2 to set
     */
    public void setNameUniv2(University nameUniv2) {
        this.nameUniv2 = nameUniv2;
    }

    private University myUniversityModel;
    private AccountController account;
    private University viewUniversityModel;
    private UploadedFile resume;
    private Account accountModel;
    private ArrayList<University> featuredList;
    private SearchController search;
    private String major;
    private ArrayList<Apply> appliedList;
    private String selectedUniv;
    private String selectedUniv2;
    private University nameUniv;
    private University nameUniv2;

    public UniversityController(University myUniversityModel, AccountController account, University viewUniversityModel, UploadedFile resume, Account accountModel, ArrayList<University> featuredList, SearchController search, String major, ArrayList<Apply> appliedList) {
        this.myUniversityModel = myUniversityModel;
        this.account = account;
        this.viewUniversityModel = viewUniversityModel;
        this.resume = resume;
        this.accountModel = accountModel;
        this.featuredList = featuredList;
        this.search = search;
        this.major = major;
        this.appliedList = appliedList;
    }

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
        
        if (major == null) major = "";
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
    
    public void loadMyProfile1() {
       // myUniversityModel = UniversityDAO.getUniversityByID(account.getLoginModel().getUserID());
        nameUniv = UniversityDAO.getUniversityByName(selectedUniv);
        System.out.println("I am from " + nameUniv.getCity());
        if (nameUniv == null) {
            nameUniv = new University(selectedUniv);
        }
    }
       public void loadMyProfile2() {
       // myUniversityModel = UniversityDAO.getUniversityByID(account.getLoginModel().getUserID());
        nameUniv2 = UniversityDAO.getUniversityByName(selectedUniv2);
        System.out.println("I am from " + getNameUniv2().getCity());
        if (getNameUniv2() == null) {
            nameUniv2 = new University(selectedUniv2);
        }
    }

    public String loadProfile() {
        FacesContext facesContext2 = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext2.getExternalContext().getRequestParameterMap();
        String name = params.get("name");

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
    
    public void addMajor() {
        if(major.equals("")) return;
        
        myUniversityModel.addMajor(major);
        
        major = "";
    }
    
    public void removeMajor() {
        
        myUniversityModel.removeMajor(major);
        
        major = "";
    }
    
    public void removeLastMajor() {
        myUniversityModel.removeLastMajor();
    }
    
    public String gotoUpdate() {
        return "updateUniversityProfile.xhtml?faces-redirect=true";
    }
    
    public String updateUniversity() {
        
        UniversityDAO.updateUniversity(myUniversityModel);
        
        return "myProfile.xhtml?faces-redirect=true";
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

   /**
     * @return the appliedList
     */
    public ArrayList<Apply> getAppliedList() {
        System.out.println(accountModel.getAccountType());
           appliedList = UniversityDAO.getApply(account.getLoginModel().getUserID()); 
        return appliedList;
    }

    /**
     * @param appliedList the appliedList to set
     */
    public void setAppliedList(ArrayList<Apply> appliedList) {
        this.appliedList = appliedList;
    }
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }    
    
    /**
     * @return the selecterUniv
     */
    public String getSelectedUniv() {  
        return selectedUniv;
    }

    /**
     * @param selectedUniv the selecterUniv to set
     */
    public void setSelectedUniv(String selectedUniv) {
        this.selectedUniv = selectedUniv;      
    }

    /**
     * @return the nameUniv
     */
    public University getNameUniv() {
        return nameUniv;
    }

    /**
     * @param nameUniv the nameUniv to set
     */
    public void setNameUniv(University nameUniv) {
        this.nameUniv = nameUniv;
    }
    
     public void localeChanged(ValueChangeEvent e) {
         selectedUniv = e.getNewValue().toString();
         if(selectedUniv != null){
             System.out.println("Not empty");
             loadMyProfile1();
         }else{
             System.out.println("Selection empty");
         }
     
   }
     
          public void localeChanged2(ValueChangeEvent e) {
         selectedUniv2 = e.getNewValue().toString();
         if(selectedUniv2 != null){
             System.out.println("Not empty");
             loadMyProfile2();
         }else{
             System.out.println("Selection empty");
         }
     
   }

    /**
     * @return the selectedUniv2
     */
    public String getSelectedUniv2() {
        return selectedUniv2;
    }

    /**
     * @param selectedUniv2 the selectedUniv2 to set
     */
    public void setSelectedUniv2(String selectedUniv2) {
        this.selectedUniv2 = selectedUniv2;
    }
}
