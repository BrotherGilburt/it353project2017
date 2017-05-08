/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ImageDAO;
import DAO.StudentDAO;
import Model.Account;
import Model.Student;
import Utility.EmailForMoreInfo;
import Utility.EmailForSchedule;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Part;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author IT353S718
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
    private StudentDAO stuDAO;
    private Student student;
    private String message;

    /**
     * Creates a new instance of StudentProfileController
     */
    public StudentController() {
        if (accountModel == null) {
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
        stuDAO = new StudentDAO();

        resume = null;
    }

    public String loadMyProfile() {
        myProfileModel = StudentDAO.getProfile(account.getLoginModel().getUserID());
        if (myProfileModel == null) {
            myProfileModel = new Student(account.getLoginModel().getUserID());
        }

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

    public String updateImage() throws SQLException, IOException, ClassNotFoundException {

            myProfileModel.setImage(true);
            this.updateStudent();

            String userid = account.getLoginModel().getUserID();
            System.out.println(userid);
            int update = ImageDAO.updateImage(userid, resume);

        return "myProfile.xhtml?faces-redirect=true";
    }

    public void gotoUpdateMixtape() throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        int update = StudentDAO.updateMixtape(myProfileModel.getMixtape(), account.getLoginModel().getUserID());
        if (update != 0) {
            context.addMessage(null, new FacesMessage("Successful", "Mixtape Updated"));
        } else {
            context.addMessage(null, new FacesMessage("Failed", "Mixtape update failed"));
        }
    }

    public void gotoUpdateEssay() throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        int update = StudentDAO.updateEssay(myProfileModel.getEssay(), account.getLoginModel().getUserID());
        if (update != 0) {
            context.addMessage(null, new FacesMessage("Successful", "Essay Updated"));
        } else {
            context.addMessage(null, new FacesMessage("Failed", "Essay update failed"));
        }
    }

    public String updateFinished() throws SQLException {
        this.updateStudent();

        return "myProfile.xhtml?faces-redirect=true";
    }

    public void updateStudent() throws SQLException {
        StudentDAO.updateStudent(myProfileModel);
    }

    public String uploadImage() throws IOException {
        boolean status = false;

        //save image
        if (status) { //successful upload
            myProfileModel.setImage(true); //myProfileModel.getUserID() + "_profile.jpg");
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

    /**
     * @return the stuDAO
     */
    public StudentDAO getStuDAO() {
        return stuDAO;
    }

    /**
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    public void schedule() {
        EmailForSchedule email = new EmailForSchedule();
        boolean emailStatus = false;
        System.out.println(viewStudentModel.getEmail());
        viewStudentModel.setEmail(stuDAO.emailByID(viewStudentModel.getUserID()));
        System.out.println(viewStudentModel.getEmail());

        emailStatus = email.sendEmail(viewStudentModel);
        if (emailStatus) {
            message = "An email has been sent to the student to schedule an appointment!!";
        } else {
            System.out.println("not sent");
        }

    }

    public void moreInfo() {
        viewStudentModel.setEmail(stuDAO.emailByID(viewStudentModel.getUserID()));
        System.out.println(stuDAO.emailByID(viewStudentModel.getUserID()));
        EmailForMoreInfo info = new EmailForMoreInfo();
        boolean estatus = false;
        estatus = info.sendEmail(viewStudentModel);
        if (estatus) {
            message = "Email sent!";
        } else {
            message = "error!";
        }
    }
}
