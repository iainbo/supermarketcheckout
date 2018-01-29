package org.iainbo.supermarketcheckout.service;

import org.iainbo.supermarketcheckout.model.Item;
import org.iainbo.supermarketcheckout.model.Offer;
import org.iainbo.supermarketcheckout.repositories.ItemRepository;
import org.iainbo.supermarketcheckout.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
public class BasketService {

    private static final String BUY_TWO_GET_ONE_FREE = "B2G1F";
    private static final String BUY_TWO_FOR_FIVE = "2FOR5";

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
        long noOfTimesOfferToBeApplied = basket.get(itemId) / 2;
        if(offer.getName().equals(BUY_TWO_GET_ONE_FREE)){
            newTotal = totalCostBeforeDiscount();
            for(int i = 0; i < noOfTimesOfferToBeApplied; i++){
                addItemToBasket(item);
            }
        }if(offer.getName().equals(BUY_TWO_FOR_FIVE)){
            if(basket.get(itemId) == 2){
                return offer.getNewCost();
            }else{
                for(int i = 0; i < noOfTimesOfferToBeApplied; i++){
                    newTotal = newTotal.add(offer.getNewCost());
                }
                if(basket.get(itemId) % 2 > 0){
                    newTotal = newTotal.add(item.getCost());
                }
            }
        }
        return newTotal;
    }
}
