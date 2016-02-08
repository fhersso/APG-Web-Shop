/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apgshop.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Gnome JPA implementation
 * @author Fhersso
 */
@Entity
public class Gnome implements Serializable, GnomeDTO {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String type;
    private double price;
    private Integer amount;

    public Gnome() {
    }
    /**
     * Create a new Gnome
     * @param type Gnome type
     * @param price price 
     * @param amount Quantity
     */
    public Gnome(String type, double price, Integer amount) {
        this.type = type;
        this.price = price;
        this.amount = amount;
    }

    public String getId() {
        return type;
    }

    public void setId(String id) {
        this.type = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (type != null ? type.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gnome)) {
            return false;
        }
        Gnome other = (Gnome) object;
        if ((this.type == null && other.type != null) || (this.type != null && !this.type.equals(other.type))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "apgshop.model.Gnome[ id=" + type + " ]";
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public Integer getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
