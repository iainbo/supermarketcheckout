package org.iainbo.supermarketcheckout.controller;

import org.iainbo.supermarketcheckout.entities.SuperMarket;
import org.iainbo.supermarketcheckout.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping("/supermarket")
    public String listItems(Model model){
        model.addAttribute("items", itemRepository.findAll());
        return "supermarket";
    }

    /*@GetMapping("/supermarket")
    public String itemListForm(Model model){
        model.addAttribute("superMarket", new SuperMarket());
        return "supermarket";
    }*/

    @PostMapping("/supermarket")
    public String basketSubmit(@ModelAttribute SuperMarket superMarket){
        return "checkOutResult";
    }
}
