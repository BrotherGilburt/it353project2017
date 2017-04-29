/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.StudentDAO;
import Model.Student;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Part;

/**
 *
 * @author IT353S710
 */
@ManagedBean
@SessionScoped
public class StudentController implements Serializable {

    private Student myProfileModel; //The user.
    private Student viewStudentModel; //Other Student
    private Part myImage;
    private AccountController account;
    private SearchController search;
    private String errorMessage;

    /**
     * Creates a new instance of StudentProfileController
     */
    public StudentController() {
        if (account == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            account = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{accountController}", AccountController.class);
        }
        if (myProfileModel == null) {
            myProfileModel = new Student(account.getLoginModel().getUserID());
        }
        if (viewStudentModel == null) {
            viewStudentModel = new Student();
        }
        if (search == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            search = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{searchController}", SearchController.class);
        }
    }

    public String loadMyProfile() {
        /*DATABASE ACCESS*/

        myProfileModel = StudentDAO.getProfile(account.getLoginModel().getUserID());
        if (myProfileModel == null) myProfileModel = new Student(account.getLoginModel().getUserID());
        
        return "myProfile.xhtml?faces-redirect=true";
    }

    public String loadProfile() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        String userID = params.get("userid");

        ArrayList<Student> studentsList = search.getStudentsList();

        for (int i = 0; i < studentsList.size(); i++) {
            if (studentsList.get(i).getUserID().equals(userID)) {
                viewStudentModel = studentsList.get(i);
                break;
            }
        }
        
        return "studentProfile.xhtml?faces-redirect=true";
    }

    public String gotoUpdateGeneral() {

        return "updateStudentProfile.xhtml?faces-redirect=true";
    }
    
    public String gotoUpdateImage() {
        return "myProfile.xhtml?faces-redirect=true";
    }
    
    public String gotoUpdateMixtape() {
        return "myProfile.xhtml?faces-redirect=true";
    }
    
    public String gotoUpdateEssay() {
        return "myProfile.xhtml?faces-redirect=true";
    }

    public String updateFinished() {
        /*DATA VALIDATION*/
        
        StudentDAO.updateStudent(myProfileModel);
        
        return "myProfile.xhtml?faces-redirect=true";
    }

    public String uploadImage() throws IOException {
        boolean status = false;

        //save image
        
        if (status) { //successful upload
            myProfileModel.setImage(myProfileModel.getUserID() + "_profile.jpg");
        } else { //failed/no upload
            //Error Message?
        }
        return null;
    }

    public Student getMyProfileModel() {
        return myProfileModel;
    }

    public void setMyProfileModel(Student myProfileModel) {
        this.myProfileModel = myProfileModel;
    }

    public Part getMyImage() {
        return myImage;
    }

    public void setMyImage(Part myImage) {
        this.myImage = myImage;
    }

    public Student getViewStudentModel() {
        return viewStudentModel;
    }

    public void setViewStudentModel(Student viewStudentModel) {
        this.viewStudentModel = viewStudentModel;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
