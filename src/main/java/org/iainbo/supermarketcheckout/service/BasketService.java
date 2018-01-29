package org.iainbo.supermarketcheckout.service;

import org.iainbo.supermarketcheckout.entities.Item;
import org.iainbo.supermarketcheckout.entities.Offer;
import org.iainbo.supermarketcheckout.repositories.ItemRepository;
import org.iainbo.supermarketcheckout.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
public class BasketService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OfferRepository offerRepository;

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
            Long quantity = basket.get(itemId);
            Item item = itemRepository.findById(itemId);
            BigDecimal itemCost = item.getCost().multiply(BigDecimal.valueOf(quantity));
            total = total.add(itemCost);
        }

        return total;
    }

    public BigDecimal applyOfferAndGetNewTotal(){
        BigDecimal total = BigDecimal.ZERO;
        List<Offer> allOffers = offerRepository.findAll();

            for(Long itemId : basket.keySet()){
                for(Offer offer : allOffers){
                    if((offer.getItem().getId() ==  itemId) && (basket.get(itemId) >= offer.getAmountToQualify())){
                        total = applyOffer(offer, itemId);
                    }
                }
            }

        return total;
    }

    private BigDecimal applyOffer(Offer offer, Long itemId){
        BigDecimal newTotal = BigDecimal.ZERO;
        Item item = itemRepository.findById(itemId);
        if(offer.getName().equals("B2G1F")){
            newTotal = totalCostBeforeDiscount();
            long noOfItemsToBeAdded = basket.get(itemId) / 2;
            for(int i = 0; i < noOfItemsToBeAdded; i++){
                addItemToBasket(item);
            }
        }if(offer.getName().equals("2FOR5")){
            newTotal = totalCostBeforeDiscount().subtract(BigDecimal.valueOf(2));
        }
        return newTotal;
    }
}
