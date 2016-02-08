/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apgshop.model;

import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * Account JPA implementation
 * @author Fhersso
 */
@Entity
public class Account implements Serializable, AccountDTO {

    private static final long serialVersionUID = 1L;
    
    /*Username and password*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;
    private String password;

    /*Account status and type*/
    private boolean isBanned;
    private boolean isAdmin;

    /*Shopping cart details*/
    private double debt;
    private HashMap<String, Integer> cart;

    public Account() {
    }

    public Account(String name, String password) {
        this.userId = name;
        this.password = password;
        this.isBanned = false;
        this.isAdmin = false;
        this.debt = 0.0;
        this.cart = new HashMap<>();
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "apgshop.model.Customer[ id=" + userId + " ]";
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Boolean getIsbanned() {
        return this.isBanned;
    }

    @Override
    public void setIsbanned(Boolean isBanned) {
        this.isBanned = isBanned;
    }

    @Override
    public Boolean getIsAdmin() {
        return this.isAdmin;
    }

    @Override
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public double getDebt() {
        return this.debt;
    }

    @Override
    public HashMap<String, Integer> getCart() {
        return this.cart;
    }
/**
 * Add element to the user cart
 * @param type Gnome type
 * @param amount number of gnomes
 * @param price price in euros
 */
    @Override
    public void addToCart(String type, Integer amount, double price) {
        Integer previous = cart.remove(type);
        if (previous != null)
            this.debt = debt - previous*price;
        
        cart.put(type, amount);
        this.debt= debt + amount*price;
    }
/**
 * Clear cart
 */
    @Override
    public void emptyCart() {
        debt = 0.0;
        cart.clear();
    }

}
