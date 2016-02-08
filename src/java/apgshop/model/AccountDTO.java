/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apgshop.model;

import java.util.HashMap;

/**
 * Acoount Data Transfer Object
 * @author Fhersso
 */
public interface AccountDTO {

    public String getUserId();

    public String getPassword();

    public Boolean getIsbanned();

    public void setIsbanned(Boolean isBanned);

    public Boolean getIsAdmin();

    public void setIsAdmin(Boolean isAdmin);
    
    public double getDebt();
    
    public HashMap<String, Integer> getCart();
    
    public void addToCart(String type, Integer amount, double price);

    public void emptyCart();

}
