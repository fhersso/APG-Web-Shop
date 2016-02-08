/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apgshop.controller;

import apgshop.model.Account;
import apgshop.model.AccountDTO;
import apgshop.model.Gnome;
import apgshop.model.GnomeDTO;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Fhersso
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class WebShopFacade {

    @PersistenceContext(unitName = "APGShopPU")
    private EntityManager em;

    /**
     * Initialize the web shop adding an admin and an inventory
     */
    public void init() {
        AccountDTO admin = new Account("Fernando", "11111111");
        admin.setIsAdmin(true);
        em.persist(admin);
        GnomeDTO gnome1 = new Gnome("Red", 10.0, 10);
        GnomeDTO gnome2 = new Gnome("Yellow", 10.0, 10);
        GnomeDTO gnome3 = new Gnome("Blue", 10.0, 10);
        em.persist(gnome1);
        em.persist(gnome2);
        em.persist(gnome3);

    }

    // Account management
    /**
     * Create a new Account
     *
     * @param name username
     * @param password password
     * @return Account
     */
    public AccountDTO createAccountDTO(String name, String password) {
        AccountDTO account = new Account(name, password);
        em.persist(account);
        return account;
    }

    /**
     * Find a user by username
     *
     * @param name username
     * @return the account or null if no user was found
     */
    public AccountDTO getAccount(String name) {
        AccountDTO account = em.find(Account.class, name);
        if (account == null) {
            return null;
        }
        return account;
    }

    // Find all gnomes
    /**
     * Get the inventory of all products
     *
     * @return an array of gnomes
     */
    public ArrayList<GnomeDTO> getGnomes() {
        ArrayList<GnomeDTO> gnomes = new ArrayList<>();
        gnomes.add(em.find(Gnome.class, "Red"));
        gnomes.add(em.find(Gnome.class, "Yellow"));
        gnomes.add(em.find(Gnome.class, "Blue"));
        return gnomes;
    }

    /**
     * Add Gnomes to a cart
     *
     * @param name Username
     * @param type Type of gnome
     * @param quantity Quantity
     * @param price Price in euros
     */
    public void addToCart(String name, String type, Integer quantity, double price) {
        AccountDTO account = em.find(Account.class, name);
        if (account != null) {
            account.addToCart(type, quantity, price);
            em.merge(account);
        }

    }

    /**
     * Clear the Cart of a User
     *
     * @param name Username
     * @return null if no user was found
     */
    public HashMap<String, Integer> emptyCart(String name) {
        AccountDTO account = em.find(Account.class, name);
        if (account == null) {
            return null;
        }
        account.emptyCart();
        em.merge(account);
        return account.getCart();
    }

    /**
     * Pay the gnomes in the cart and remove them from the inventory
     *
     * @param cart Shopping cart
     * @param type type of gnomes
     */
    public void payGnomes(HashMap<String, Integer> cart, String type) {
        Integer amount = cart.get(type);
        if (amount != null) {
            GnomeDTO gnome1 = em.find(Gnome.class, type);
            if (gnome1.getAmount() >= amount) {
                gnome1.setAmount(gnome1.getAmount() - cart.get(type));
                em.merge(gnome1);
            }
        }
    }

    /**
     * Ban a user from the webshop
     *
     * @param name username
     * @return string
     */
    public String banUser(String name) {
        AccountDTO account = em.find(Account.class, name);
        if (account != null) {
            account.setIsbanned(true);
            em.merge(account);
            return "Banned Succesfully";
        } else {
            return "User does no exist";
        }
    }

    /**
     * Update the amount of gnomes in the store
     *
     * @param type type of gonomes
     * @param amount quantity
     * @return
     */
    public String updateGnomes(String type, Integer amount) {
        GnomeDTO gnome = em.find(Gnome.class, type);
        if (gnome != null) {
            gnome.setAmount(gnome.getAmount()+amount);
            em.merge(gnome);
            return "update Succesfully";
        } else {
            return "gnome does no exist";
        }
    }

    /**
     *
     * @param type
     * @param amount
     * @return
     */
    public String removeGnomeType(String type) {
        GnomeDTO gnome = em.find(Gnome.class, type);
        if (gnome != null) {
            em.remove(gnome);
            return "Gnome type removed Succesfully";
        } else {
            return "gnome does no exist";
        }
    }

    /**
     * Add a new gnome type and price
     *
     * @param type gnome type
     * @param price price per unit
     */
    public String addGnomeType(String type, double price) {
        GnomeDTO gnome = new Gnome(type, price, 0);
        em.persist(gnome);
        return "Gnome type added Succesfully";
    }

}
