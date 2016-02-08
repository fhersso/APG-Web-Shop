/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apgshop.view;

import apgshop.controller.WebShopFacade;
import apgshop.model.AccountDTO;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Fhersso
 */
@ManagedBean(name = "webShopManager")
@SessionScoped
public class WebShopManager implements Serializable {

    private static final long serialVersionUID = 16247164406L;
    @EJB
    private WebShopFacade webShopFacade;
    private String currentUsername;
    private String currentPassword;
    private String error = null;

    private Exception transactionFailure;
    @Inject
    private Conversation conversation;

    @ManagedProperty(value = "#{homePageManager}")
    private HomePageManager homePageManager;
    @ManagedProperty(value = "#{administratorPageManager}")
    private AdministratorPageManager administratorPageManager;

    //getters and setters*************
    public HomePageManager getHomePageManager() {
        return homePageManager;
    }

    public void setHomePageManager(HomePageManager homePageManager) {
        this.homePageManager = homePageManager;
    }

    public AdministratorPageManager getAdministratorPageManager() {
        return administratorPageManager;
    }

    public void setAdministratorPageManager(AdministratorPageManager administratorPageManager) {
        this.administratorPageManager = administratorPageManager;
    }

    public String getCurrentUsername() {
        return this.currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getCurrentPassword() {
        return this.currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    //Account management  *************************
    /**
     * Loging for users and admins
     */
    public void loginAccount() {
        startConversation();
        AccountDTO account;
        try {
            account = webShopFacade.getAccount(currentUsername);
            if (account == null) {
                error = "Error : This name does not exist";
            } else if (!(account.getPassword().equals(currentPassword))) {
                error = "Error : The password or the pseudo is not correct";
            } else if (account.getIsbanned()) {
                System.out.println(account.getIsbanned());
                error = "Error : This user has been banned";
            } else {
                error = null;
                if (account.getIsAdmin()) {
                    administratorPageManager.setUserName(account.getUserId());
                    administratorPageManager.setGnomes(webShopFacade.getGnomes());
                    administratorPageManager.setLogIn();

                } else {
                    homePageManager.setUserName(account.getUserId());
                    homePageManager.setGnomes(webShopFacade.getGnomes());
                    homePageManager.setLogIn();
                }

            }
        } catch (Exception e) {
            handleException(e);
        }
    }
    /**
     * Create a new user account
     */
    public void createNewAccount() {
        startConversation();
        try {
            if (currentPassword.length() < 8) {
                error = "Error : Password length should be longer than 8 characters";
            } else if (webShopFacade.getAccount(currentUsername) != null) {
                error = "Error : This name has already been registered";
            } else {
                error = null;
                AccountDTO account;
                account = webShopFacade.createAccountDTO(currentUsername, currentPassword);

                if (account.getIsAdmin()) {

                } else {
                    homePageManager.setUserName(account.getUserId());
                    homePageManager.setGnomes(webShopFacade.getGnomes());
                    homePageManager.setLogIn();
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Initialize store
     */
    public void init() {
        startConversation();
        if (webShopFacade.getAccount("Fernando") == null) {
            webShopFacade.init();
        } else {
            error = "Error : This store is already initialized";
        }
    }

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
