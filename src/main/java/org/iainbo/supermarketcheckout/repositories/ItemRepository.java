package org.iainbo.supermarketcheckout.repositories;

import org.iainbo.supermarketcheckout.entities.Item;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ItemRepository extends Repository<Item, Long>{

    Item save(Item item);

    Item findByName(String name);

    List<Item> findAll();
}
