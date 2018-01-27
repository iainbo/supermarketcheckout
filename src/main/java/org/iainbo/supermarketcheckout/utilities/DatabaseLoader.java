package org.iainbo.supermarketcheckout.utilities;

import org.iainbo.supermarketcheckout.entities.Item;
import org.iainbo.supermarketcheckout.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DatabaseLoader implements CommandLineRunner{

    private final ItemRepository itemRepository;

    @Autowired
    public DatabaseLoader(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Override
    public void run(String... strings) throws Exception{
        this.itemRepository.save(new Item("Biscuits", BigDecimal.valueOf(1.29)));
        this.itemRepository.save(new Item("Can of Juice", BigDecimal.valueOf(0.53)));
        this.itemRepository.save(new Item("Microwave Meal", BigDecimal.valueOf(3.50)));
    }
}
