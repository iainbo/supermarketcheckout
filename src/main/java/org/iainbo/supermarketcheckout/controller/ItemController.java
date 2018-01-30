package org.iainbo.supermarketcheckout.controller;

import org.iainbo.supermarketcheckout.model.Basket;
import org.iainbo.supermarketcheckout.model.Item;
import org.iainbo.supermarketcheckout.repositories.ItemRepository;
import org.iainbo.supermarketcheckout.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BasketService basketService;

    @RequestMapping(value="/supermarket")
    public String listItems(Model model){
        Basket basket = new Basket();
        model.addAttribute("basket", basket);
        model.addAttribute("items", itemRepository.findAll());
        return "supermarket";
    }

    @RequestMapping(value="/processForm", method = RequestMethod.POST)
    public String basketSubmit(@ModelAttribute("basket") Basket basket, HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        String[] itemIdS = parameterMap.get("itemId");
        String[] quantities = parameterMap.get("quantity");
        for (int i = 0; i < quantities.length; i++) {
            if (!quantities[i].equals("")){
                Item item = itemRepository.findById(Long.valueOf(itemIdS[i]));
                for (int j = 0; j < Long.valueOf(quantities[i]); j++) {
                    basketService.addItemToBasket(item);
                }
            }
        }

        basket.setTotalCost(basketService.totalCostBeforeDiscount());
        basket.setCostAfterOffersApplied(basketService.applyOfferAndGetNewTotal());
        Long noOfItems = 0L;

        for(Long amount : basketService.getBasket().values()){
            noOfItems = noOfItems + amount;
        }
        basket.setNoOfItemsInBasket(noOfItems);
        basketService.clear();
        return "checkOutResult";
    }
}
