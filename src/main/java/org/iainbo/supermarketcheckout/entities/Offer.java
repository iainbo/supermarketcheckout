package org.iainbo.supermarketcheckout.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //private Item item;
    private String description;
    private BigDecimal newCost;
    private Long amountToQualify;

    public Offer(){

    }

    public Offer(String description, BigDecimal newCost){
        //this.item = item;
        this.description = description;
        this.newCost = newCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getNewCost() {
        return newCost;
    }

    public void setNewCost(BigDecimal newCost) {
        this.newCost = newCost;
    }

    public Long getAmountToQualify() {
        return amountToQualify;
    }

    public void setAmountToQualify(Long amountToQualify) {
        this.amountToQualify = amountToQualify;
    }
}
