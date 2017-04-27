/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.UniversityDAO;
import Model.University;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

@Named(value = "universityController")
@SessionScoped
public class UniversityController implements Serializable {

    private University myUniversityModel;
    private University viewUniversityModel;

    /**
     * Creates a new instance of UniversityController
     */
    public UniversityController() {
        if (myUniversityModel == null) myUniversityModel = new University();
        if (viewUniversityModel == null) viewUniversityModel = new University();
    }
    
    /**
     * Called upon updating or creating a university for an account.
     * @return the address of the university profile.
     */
    public String updateUniversity() {
        //Call DAO to create/update a university
        
        UniversityDAO.setUniversity(myUniversityModel);
        
        return ""; //address of university profile.
    }
    
    public ArrayList<String> getAllUniversities() {
        ArrayList<String> stringList = new ArrayList();

        ArrayList<University> list = UniversityDAO.getAllUniversities();

        for (int i = 0; i < list.size(); i++) {
            if (!stringList.contains(list.get(i).getName()))
                stringList.add(list.get(i).getName());
        }
        
        Collections.sort(stringList);
        
        return stringList;
    }
    
    public ArrayList<String> getAllMajors() {
        ArrayList<String> stringList = new ArrayList();
        
        ArrayList<University> list = UniversityDAO.getAllUniversities();
        
        for (int i  = 0; i < list.size(); i++)
            for (int j  = 0; j < list.get(i).getMajors().size(); j++) {
                if (!stringList.contains(list.get(i).getMajors().get(j)))
                    stringList.add(list.get(i).getMajors().get(j));
            }
        
        Collections.sort(stringList);
        
        return stringList;
    }
    
}
