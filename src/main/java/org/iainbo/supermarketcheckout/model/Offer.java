package org.iainbo.supermarketcheckout.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name="OFFERS")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ITEM_ID")
    private Item item;

    @Column
    @NotNull
    private String name;

    @Column(name="DESCRIPTION")
    @NotNull
    private String description;

    @Column(name="NEW_COST")
    @NotNull
    private BigDecimal newCost;

    @Column(name="AMOUNT_TO_QUALIFY")
    @NotNull
    private Long amountToQualify;

    public Offer(){

    }

    public Offer(String name, Item item, String description, BigDecimal newCost, Long amountToQualify){
        this.name = name;
        this.item = item;
        this.description = description;
        this.newCost = newCost;
        this.amountToQualify = amountToQualify;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

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
