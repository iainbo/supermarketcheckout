package org.iainbo.supermarketcheckout.repositories;

import org.iainbo.supermarketcheckout.model.Item;
import org.iainbo.supermarketcheckout.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    Offer save(Offer offer);

    Offer findByItem(Item item);

    List<Offer> findAll();
}
