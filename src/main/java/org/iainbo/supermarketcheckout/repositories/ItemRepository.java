package org.iainbo.supermarketcheckout.repositories;

import org.iainbo.supermarketcheckout.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item save(Item item);

    Item findById(Long id);

    Item findByName(String name);

    List<Item> findAll();
}
