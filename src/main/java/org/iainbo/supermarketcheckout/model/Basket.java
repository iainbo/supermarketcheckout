package org.iainbo.supermarketcheckout.model;

import java.math.BigDecimal;

public class Basket {
    private BigDecimal totalCost;
    private BigDecimal costAfterOffersApplied;
    private Long noOfItemsInBasket;

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getCostAfterOffersApplied() {
        return costAfterOffersApplied;
    }

    public void setCostAfterOffersApplied(BigDecimal costAfterOffersApplied) {
        this.costAfterOffersApplied = costAfterOffersApplied;
    }

    public Long getNoOfItemsInBasket() {
        return noOfItemsInBasket;
    }

    public void setNoOfItemsInBasket(Long noOfItemsInBasket) {
        this.noOfItemsInBasket = noOfItemsInBasket;
    }
}
