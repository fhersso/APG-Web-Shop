/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apgshop.view;

import apgshop.controller.WebShopFacade;
import apgshop.model.GnomeDTO;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Fhersso
 */
@ManagedBean(name = "administratorPageManager")
@SessionScoped
public class AdministratorPageManager implements Serializable {

    private static final long serialVersionUID = 16247164404L;
    @EJB
    private WebShopFacade webShopFacade;
    private Boolean logIn = false;
    private String userName;
    private String currentUserBanned;
    private ArrayList<GnomeDTO> gnomes;
    private String currentType;
    private Integer currentQuantity;
    private double currentPrice;
    private String error = null;
    private Exception transactionFailure;
    @Inject
    private Conversation conversation;

    /**
     * Admin logout
     */
    public void logOut() {
        startConversation();
        this.userName = null;
        this.logIn = false;
    }

    /**
     * Update amount of gnomes of a given type
     */
    public void updateGnomeType() {
        startConversation();
        error = webShopFacade.updateGnomes(currentType, currentQuantity);
        gnomes = webShopFacade.getGnomes();

    }
    
    /**
     * Remove a certain type of gnomes
     */
    public void removeGnomeType() {
        startConversation();
        error = webShopFacade.removeGnomeType(currentType);
        gnomes = webShopFacade.getGnomes();

    }
    
    /**
     * Add a certain type of gnomes
     */
    public void addGnomeType() {
        startConversation();
        error = webShopFacade.addGnomeType(currentType, currentPrice);
        gnomes = webShopFacade.getGnomes();

    }

    /**
     * Ban a user
     */
    public void banUser() {
        startConversation();
        error = webShopFacade.banUser(currentUserBanned);
    }

    //getters and setters
    public String getCurrentUserBanned() {
        return currentUserBanned;
    }

    public void setCurrentUserBanned(String currentUserBanned) {
        this.currentUserBanned = currentUserBanned;
    }

    public String getCurrentType() {
        return currentType;
    }

    public void setCurrentType(String currentType) {
        this.currentType = currentType;
    }

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setLogIn() {
        this.logIn = true;
    }

    public boolean getLogIn() {
        return logIn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGnomes(ArrayList<GnomeDTO> gnomes) {
        this.gnomes = gnomes;
    }

    public ArrayList<GnomeDTO> getGnomes() {
        return this.gnomes;
    }

    /**
     * ************
     */
    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        transactionFailure = e;
    }

    /**
     * @return <code>true</code> if the latest transaction succeeded, otherwise
     * <code>false</code>.
     */
    public boolean getSuccess() {
        return transactionFailure == null;
    }

    /**
     * Returns the latest thrown exception.
     *
     * @return
     */
    public Exception getException() {
        return transactionFailure;
    }

    /**
     * This return value is needed because of a JSF 2.2 bug. Note 3 on page 7-10
     * of the JSF 2.2 specification states that action handling methods may be
     * void. In JSF 2.2, however, a void action handling method plus an
     * if-element that evaluates to true in the faces-config navigation case
     * causes an exception.
     *
     * @return an empty string.
     */
    private String jsf22Bugfix() {
        return "";
    }

}
