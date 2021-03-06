/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ApplydaoImpl;
import DAO.StudentDAO;
import javax.faces.bean.SessionScoped;
import Model.Apply;
import java.io.IOException;
import org.primefaces.event.FlowEvent;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.mail.Part;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class ApplyController implements Serializable {


    private ArrayList<String> majorsList;
    private AccountController account;
    private String response;
    private Apply apply;
    private UploadedFile resume;
    private String uploadStatus;
    private String majorSelect;
    
    
    
    public ApplyController() {
         if (account == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            account = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{accountController}", AccountController.class);
        }
        apply = new Apply();
        majorsList = new ArrayList();
    }

    /**
     * Use this to initialize values in Apply object from Student database.
     */
    public void setUp() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            majorsList = new ArrayList();
            apply = new Apply();
            /*
            Initialize apply object here?
            apply.setFirstName(...);
            apply.setLastName(...);
            ...
            */
        }
    }
    
    /**
     * Used to apply for a university from its profile.
     * @return application page name
     */
    public String loadApplicationFromProfile() {
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        String name = params.get("name");
        
        apply = new Apply(name, majorSelect);
        
        majorSelect= new String();
        
        return "Apply.xhtml?faces-redirect=true";
    }
    
    public ArrayList<String> getMajorsList() {
        return majorsList;
    }

    public void setMajorsList(ArrayList<String> majorsList) {

    }

    public void onUniversityChange() {
        if (apply.getUniversity() != null && !apply.getUniversity().equals("")) {
            majorsList = UniversityController.getUniversityMajors(apply.getUniversity());
            /*majors = data.get(apply.getUniversity());*/
        } else {
            majorsList = new ArrayList();
            /*majors = new HashMap<String, String>();*/
        }
    }

    public String displayLocation() {
//        FacesMessage msg;
//        if(major == null || university == null)
//            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "Major is not selected.");
//        else
//            msg = new FacesMessage("Selected", major + " of " + university);
//                     FacesContext.getCurrentInstance().addMessage(null, msg); 
//                     
        return "Apply.xhtml";

    }

    //---Dropdown end
    private boolean skip;

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    // File Upload 
    private Part file;
    private Part file1;
    private Part file2;
    private Part file3;

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Part getFile1() {
        return file1;
    }

    public void setFile1(Part file1) {
        this.file1 = file1;
    }

    public Part getFile2() {
        return file2;
    }

    public void setFile2(Part file2) {
        this.file2 = file2;
    }

    public Part getFile3() {
        return file3;
    }

    public void setFile3(Part file3) {
        this.file3 = file3;
    }

    public String upload() throws IOException {

        return null;

    } // upload end

    // database connection
    public String getResponse() {
        String resultStr = "";
        resultStr += "Hello " + apply.getFirstname() + "<br/>";
        resultStr += "Your Application has been submitted successfuly <br/>";
        response = resultStr;
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Apply getApply() {
        return apply;
    }

    public void setApply(Apply apply) {
        this.apply = apply;
    }

    public String createProfile() {
        ApplydaoImpl applyDAO = new ApplydaoImpl();
        int status = applyDAO.createProfile(apply);
        if (status == 1) {
            return "uploadResume.xhtml?faces-redirect=true";
        } else {
            return "error.xhtml";
        }
    }
    public String uploadResume() throws IOException, ClassNotFoundException, SQLException{
        FacesContext context = FacesContext.getCurrentInstance();  
        resume = getResume();
        String userid = account.getLoginModel().getUserID();
        System.out.println(userid);
        int update = StudentDAO.updateResume(userid,resume);
        System.out.println(update);
        if(update !=0 ){
            context.addMessage(null, new FacesMessage("Resume Uploaded:", "Success"));
        }else{
            context.addMessage(null, new FacesMessage("Resume Upload:",  "Failed") );
        }
        return "home.xhtml?faces-redirect=true";
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
     * @return the account
     */
    public AccountController getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(AccountController account) {
        this.account = account;
    }

    /**
     * @return the uploadStatus
     */
    public String getUploadStatus() {
        return uploadStatus;
    }

    /**
     * @param uploadStatus the uploadStatus to set
     */
    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getMajorSelect() {
        return majorSelect;
    }

    public void setMajorSelect(String majorSelect) {
        this.majorSelect = majorSelect;
    }
    
}