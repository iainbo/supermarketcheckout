package org.iainbo.supermarketcheckout.utilities;

import org.iainbo.supermarketcheckout.entities.Item;
import org.iainbo.supermarketcheckout.entities.Offer;
import org.iainbo.supermarketcheckout.repositories.ItemRepository;
import org.iainbo.supermarketcheckout.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DatabaseLoader implements CommandLineRunner{

    private final ItemRepository itemRepository;
    private final OfferRepository offerRepository;

    private Item biscuits = new Item("Biscuits", BigDecimal.valueOf(1.29));
    private Item juice = new Item("Can of Juice", BigDecimal.valueOf(0.53));
    private Item microwaveMeal = new Item("Microwave Meal", BigDecimal.valueOf(3.50));

    @Autowired
    public DatabaseLoader(ItemRepository itemRepository, OfferRepository offerRepository){
        this.itemRepository = itemRepository;
        this.offerRepository = offerRepository;
    }

    @Override
    public void run(String... strings) throws Exception{
        this.itemRepository.save(biscuits);
        this.itemRepository.save(juice);
        this.itemRepository.save(microwaveMeal);

        this.offerRepository.save(new Offer("BOGOF", juice, "Buy 2 get 1 Free", BigDecimal.ZERO, 2L));
        this.offerRepository.save(new Offer("2FOR5", microwaveMeal, "2 for £5", BigDecimal.valueOf(1.50), 2L));
    }
}
