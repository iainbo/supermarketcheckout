package org.iainbo.supermarketcheckout.repositories;

import org.iainbo.supermarketcheckout.entities.Item;
import org.iainbo.supermarketcheckout.entities.Offer;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface OfferRepository extends Repository<Offer, Long>{

    Offer save(Offer offer);

    Offer findByItem(Item item);

    List<Offer> findAll();
}
