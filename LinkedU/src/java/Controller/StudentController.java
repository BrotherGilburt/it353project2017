/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ImageDAO;
import DAO.StudentDAO;
import Model.Account;
import Model.Login;
import Model.Student;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Part;
import org.primefaces.model.UploadedFile;

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
    private UploadedFile resume;
    private Account accountModel;
    /**
     * Creates a new instance of StudentProfileController
     */
    public StudentController() {
        if(accountModel == null){
            accountModel = new Account();
        }
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
    
    public String gotoUpdateImage() throws SQLException, IOException, ClassNotFoundException {
        UploadedFile image = getResume();
        String userid = account.getLoginModel().getUserID();
        System.out.println(userid);
        int update = ImageDAO.updateImage(userid, image);
        return "myProfile.xhtml?faces-redirect=true";
    }
    
    public String gotoUpdateMixtape() {
        return "myProfile.xhtml?faces-redirect=true";
    }
    
    public String gotoUpdateEssay() {
        return "myProfile.xhtml?faces-redirect=true";
    }

    public String updateFinished() throws SQLException {
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

}
