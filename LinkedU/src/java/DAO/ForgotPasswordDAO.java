/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Keegan
 */
@Named(value = "forgotPasswordDAO")
@RequestScoped
public class ForgotPasswordDAO {

    /**
     * Creates a new instance of ForgotPasswordDAO
     */
    public ForgotPasswordDAO() {
    }

}
