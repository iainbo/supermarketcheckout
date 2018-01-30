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
import java.util.stream.Collectors;

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
                Item item = itemRepository.findById(itemId);
                if(offerFound(allOffers, item)){
                    Offer offer = getOfferForItem(allOffers, item);
                    total = total.add(applyOffer(offer, item));
                }else{
                    total = total.add(item.getCost()).multiply(BigDecimal.valueOf(basket.get(item.getId())));
                }

            }
        return total;
    }

    private List<Offer> checkForOfferInList(List<Offer> offers, Item item){
        return offers.stream().filter(offer -> offer.getItem().getId().equals(item.getId())).collect(Collectors.toList());
    }

    private Offer getOfferForItem(List<Offer> offers, Item item){
        List<Offer> result = checkForOfferInList(offers, item);
        return result.get(0);
    }

    private boolean offerFound(List<Offer> offers, Item item){
        List<Offer> result = checkForOfferInList(offers, item);
        if(result.size() == 1){
            return true;
        }else {
            return false;
        }
    }

    private BigDecimal applyOffer(Offer offer, Item item){
        BigDecimal newTotal = BigDecimal.ZERO;
        long noOfTimesOfferToBeApplied = basket.get(item.getId()) / 2;
        if(offer.getName().equals(BUY_TWO_GET_ONE_FREE)){
            newTotal = item.getCost().multiply(BigDecimal.valueOf(basket.get(item.getId())));
            for(int i = 0; i < noOfTimesOfferToBeApplied; i++){
                addItemToBasket(item);
            }
        }if(offer.getName().equals(BUY_TWO_FOR_FIVE)){
            if(basket.get(item.getId()) == 2){
                return offer.getNewCost();
            }else{
                for(int i = 0; i < noOfTimesOfferToBeApplied; i++){
                    newTotal = newTotal.add(offer.getNewCost());
                }
                if(basket.get(item.getId()) % 2 > 0){
                    newTotal = newTotal.add(item.getCost());
                }
            }
        }
        return newTotal;
    }

    public void clear(){
        basket.clear();
    }
}
