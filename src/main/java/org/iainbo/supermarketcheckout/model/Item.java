package org.iainbo.supermarketcheckout.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name="ITEMS")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="NAME")
    @NotNull
    private String name;

    @Column(name="COST")
    @NotNull
    private BigDecimal cost;

    private int amountToBuy;

    public Item(){

    }

    public Item(String name, BigDecimal cost){
        this.name = name;
        this.cost = cost;
        this.amountToBuy = 0;
    }

    public int getAmountToBuy() {
        return amountToBuy;
    }

    public void setAmountToBuy(int amountToBuy) {
        this.amountToBuy = amountToBuy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
