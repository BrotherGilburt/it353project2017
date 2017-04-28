/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ApplydaoImpl;
import javax.faces.bean.SessionScoped;
import Model.Apply;
import java.io.IOException;
import org.primefaces.event.FlowEvent;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.mail.Part;

@ManagedBean
@SessionScoped
public class ApplyController implements Serializable {


    private ArrayList<String> majorsList;
    private String response;
    private Apply apply;

    public ApplyController() {

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
        
        //TO BE COMPLETED.
        
        return "error.xhtml";
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
            return "response.xhtml";
        } else {
            return "error.xhtml";
        }
    }
}


    //OLD CODE

    //-- dropdown start
    /*private Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();*/
 /*private String university;
    private String major;
 /*private Map<String, String> universities;
    private Map<String, String> majors;*/

/*@PostConstruct
    public void init() {
        universities = new HashMap<String, String>();
        universities.put("Princeton University", "Princeton University");
        universities.put("Harvard University", "Harvard University");
        universities.put("University of Chicago", "University of Chicago");
        universities.put("Illinois State University", "Illinois State University");
        universities.put("Stanford University", "Stanford University");

        Map<String, String> map = new HashMap<String, String>();
        map.put("Art and Archaeology", "Art and Archaeology");
        map.put("Chemical and Biological Engineering", "Chemical and Biological Engineering");
        map.put("Economics", "Economics");
        map.put("Computer Science", "Computer Science");
        data.put("Princeton University", map);

        map = new HashMap<String, String>();
        map.put("Applied mathematics", "Applied mathematics");
        map.put("Business economics", "Business economics");
        map.put("Computational Science and Engineering", "Computational Science and Engineering");
        data.put("Harvard University", map);

        map = new HashMap<String, String>();
        map.put("Biological Sciences Division", "Biological Sciences Division");
        map.put("Humanities Division", "Humanities Division");
        map.put("Physical Sciences Division", "Physical Sciences Division");
        map.put("Computer Science Division", "Computer Science Division");
        data.put("University of Chicago", map);

        map = new HashMap<String, String>();
        map.put("Anthropology - Applied Community & Economic Development", "Anthropology - Applied Community & Economic Development");
        map.put("Biological Sciences - Biomathematics", "Biological Sciences - Biomathematics");
        map.put("Chemistry - Master of Chemistry Education", "Chemistry - Master of Chemistry Education");
        map.put("Information Systems - Internet Application Development", "Information Systems - Internet Application Development");
        data.put("Illinois State University", map);

        map = new HashMap<String, String>();
        map.put("Computational and Mathematical Engineering ", "Computational and Mathematical Engineering ");
        map.put("Chemistry  ", "Chemistry  ");
        map.put("Music ", "Music ");
        map.put("Economics ", "Economics ");
        map.put("Computer Science ", "Computer Science");
        data.put("Stanford University", map);
    }*/

 /*public Map<String, Map<String, String>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String, String>> data) {
        this.data = data;
    }
    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    /*public Map<String, String> getUniversities() {
    return universities;
    }
    public void setUniversities(Map<String, String> universities) {
    this.universities = universities;
    }
    public Map<String, String> getMajors() {
    return majors;
    }
    public void setMajors(Map<String, String> majors) {
    this.majors = majors;
    }*/


    /*public void onUniversityChange() {
        if (apply.getUniversity() != null && !apply.getUniversity().equals("")) {
            majors = data.get(apply.getUniversity());
        } else {
            majors = new HashMap<String, String>();
        }
    }*/