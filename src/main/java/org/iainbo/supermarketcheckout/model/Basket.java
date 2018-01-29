package org.iainbo.supermarketcheckout.model;

import java.math.BigDecimal;
import java.util.List;

public class Basket {
    private BigDecimal totalCost;
    private BigDecimal costAfterOffersApplied;
    private List<Item> items;

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> basket) {
        this.items = basket;
    }
}
