package org.iainbo.supermarketcheckout.service;

import org.iainbo.supermarketcheckout.entities.Item;
import org.iainbo.supermarketcheckout.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class BasketService {

    @Autowired
    private ItemRepository itemRepository;

    private HashMap<Long, Long> basket = new HashMap<>();

    public HashMap<Long, Long> getBasket() {
        return basket;
    }

    public void setBasket(HashMap<Long, Long> basket) {
        this.basket = basket;
    }

    public void addItemToBasket(Item item){
        if(basket.containsKey(item.getId())){
            basket.replace(item.getId(), basket.get(item.getId()) + 1);
        }else{
            basket.put(item.getId(), 1L);
        }
    }

    public BigDecimal totalCostBeforeDiscount(){
        BigDecimal total = BigDecimal.ZERO;

        for(Long itemId : basket.keySet()){
            Item item = itemRepository.findById(itemId);
            BigDecimal itemCost = item.getCost();
            total.add(itemCost);
        }

        return total;
    }

    private boolean itemHasOffer(Item item){
        return false;
    }

    public BigDecimal totalCostAfterDiscountApplied(){
        return BigDecimal.ZERO;
    }
}
