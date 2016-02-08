/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apgshop.model;

/**
 * Gnome Data Transfer Object
 * @author Fhersso
 */
public interface GnomeDTO {

    public String getType();

    public void setType(String type);

    public double getPrice();

    public void setPrice(double price);

    public Integer getAmount();

    public void setAmount(Integer amount);

}
