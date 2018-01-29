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

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BasketService basketService;

    @RequestMapping(value="/supermarket")
    public String listItems(Model model){
        Basket basket = new Basket();
        basket.setItems(itemRepository.findAll());
        model.addAttribute("basket", basket);
        model.addAttribute("items", itemRepository.findAll());
        return "supermarket";
    }

    @RequestMapping(value="/processForm", method = RequestMethod.POST)
    public String basketSubmit(@ModelAttribute("basket") Basket basket, @ModelAttribute("item") Item item){

        basket.setTotalCost(basketService.totalCostBeforeDiscount());
        basket.setCostAfterOffersApplied(basketService.applyOfferAndGetNewTotal());
        return "checkOutResult";
    }
}
