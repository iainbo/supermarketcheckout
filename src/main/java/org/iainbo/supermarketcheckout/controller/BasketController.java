package org.iainbo.supermarketcheckout.controller;

import org.iainbo.supermarketcheckout.repositories.ItemRepository;
import org.iainbo.supermarketcheckout.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BasketController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private ItemRepository itemRepository;
}
