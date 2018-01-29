package service;

import org.iainbo.supermarketcheckout.entities.Item;
import org.iainbo.supermarketcheckout.entities.Offer;
import org.iainbo.supermarketcheckout.repositories.ItemRepository;
import org.iainbo.supermarketcheckout.repositories.OfferRepository;
import org.iainbo.supermarketcheckout.service.BasketService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BasketService.class)
@EntityScan("org.iainbo.supermarketcheckout.entities")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BasketServiceTest {

    @Autowired
    private BasketService basketService;

    @MockBean
    private ItemRepository mockItemRepository;

    @MockBean
    private OfferRepository mockOfferRepository;

    private Item biscuitItem1;
    private Item biscuitItem2;
    private Item juiceItem;
    private Item microwaveItem;



    @Before
    public void setUp(){
        biscuitItem1 = new Item("biscuit", BigDecimal.valueOf(1.29));
        biscuitItem1.setId(1L);
        biscuitItem2 = new Item("biscuit", BigDecimal.valueOf(1.29));
        biscuitItem2.setId(1L);
        juiceItem = new Item("Can of Juice", BigDecimal.valueOf(0.53));
        juiceItem.setId(4L);
        microwaveItem = new Item("Micorwave Meal", BigDecimal.valueOf(3.50));
        microwaveItem.setId(5L);
    }

    @Test
    public void testAddingItemToBasketForFirstTime(){
        basketService.addItemToBasket(biscuitItem1);

        Assert.assertEquals(1, basketService.getBasket().size());
    }

    @Test
    public void testAddingTwoOfTheSameItem(){
        basketService.addItemToBasket(biscuitItem1);
        basketService.addItemToBasket(biscuitItem2);

        Assert.assertEquals(1, basketService.getBasket().size());
        Assert.assertEquals(Long.valueOf(2), basketService.getBasket().get(biscuitItem1.getId()));
    }

    @Test
    public void testAddingThreeDifferentItems(){
        basketService.addItemToBasket(biscuitItem1);
        basketService.addItemToBasket(juiceItem);
        basketService.addItemToBasket(microwaveItem);

        Assert.assertEquals(3, basketService.getBasket().size());
    }

    @Test
    public void testAddingTwoOfTheSameItemAndOneDifferentItem(){
        basketService.addItemToBasket(biscuitItem1);
        basketService.addItemToBasket(biscuitItem2);
        basketService.addItemToBasket(microwaveItem);

        Assert.assertEquals(2, basketService.getBasket().size());
        Long noOfBiscuitsInBasket = basketService.getBasket().get(biscuitItem1.getId());
        Assert.assertEquals(Long.valueOf(2), noOfBiscuitsInBasket);
    }

    @Test
    public void testTotalingOfBasketBeforeDiscountForOneOfEachItem(){
        basketService.addItemToBasket(biscuitItem1);
        basketService.addItemToBasket(juiceItem);
        basketService.addItemToBasket(microwaveItem);

        when(mockItemRepository.findById(biscuitItem1.getId())).thenReturn(biscuitItem1);
        when(mockItemRepository.findById(juiceItem.getId())).thenReturn(juiceItem);
        when(mockItemRepository.findById(microwaveItem.getId())).thenReturn(microwaveItem);

        BigDecimal expectedTotal = biscuitItem1.getCost().add(juiceItem.getCost().add(microwaveItem.getCost()));
        BigDecimal actualTotal = basketService.totalCostBeforeDiscount();

        Assert.assertEquals(expectedTotal, actualTotal);

    }

    @Test
    public void testTotalingOfBasketBeforeDiscountForThreeOfTheSameItems(){
        Item biscuitItem3 = new Item("biscuit", BigDecimal.valueOf(1.29));
        biscuitItem3.setId(1L);

        basketService.addItemToBasket(biscuitItem1);
        basketService.addItemToBasket(biscuitItem2);
        basketService.addItemToBasket(biscuitItem3);

        when(mockItemRepository.findById(biscuitItem1.getId())).thenReturn(biscuitItem1);
        when(mockItemRepository.findById(biscuitItem2.getId())).thenReturn(biscuitItem2);
        when(mockItemRepository.findById(biscuitItem3.getId())).thenReturn(biscuitItem3);

        BigDecimal expectedTotal = biscuitItem1.getCost().add(biscuitItem2.getCost().add(biscuitItem3.getCost()));

        Assert.assertEquals(expectedTotal, basketService.totalCostBeforeDiscount());

    }

    @Test
    public void testApplyingJuiceOffer(){
        Item juiceItem2 = new Item("Can of Juice", BigDecimal.valueOf(0.53));
        juiceItem2.setId(juiceItem.getId());

        Offer juiceOffer = new Offer("B2G1F", juiceItem, "Buy 2 get 1 Free", BigDecimal.ZERO, 2L);
        juiceOffer.setId(1L);

        List<Offer> offers = new ArrayList<>();
        offers.add(juiceOffer);

        basketService.addItemToBasket(juiceItem);
        basketService.addItemToBasket(juiceItem2);

        when(mockItemRepository.findById(juiceItem.getId())).thenReturn(juiceItem);
        when(mockItemRepository.findById(juiceItem2.getId())).thenReturn(juiceItem2);
        when(mockOfferRepository.findAll()).thenReturn(offers);

        BigDecimal expectedTotal = juiceItem.getCost().add(juiceItem2.getCost());

        Long amountInBasket = basketService.getBasket().get(juiceItem.getId());

        Assert.assertEquals(Long.valueOf(2), amountInBasket);

        BigDecimal actualCostBeforeDiscount = basketService.totalCostBeforeDiscount();
        BigDecimal actualCostAfterDiscount = basketService.applyOfferAndGetNewTotal();

        Assert.assertEquals(Long.valueOf(3), basketService.getBasket().get(juiceItem.getId()));
        Assert.assertEquals(expectedTotal, actualCostAfterDiscount);
        Assert.assertEquals(actualCostBeforeDiscount, actualCostAfterDiscount);
    }

    @Test
    public void testApplyingJuiceOfferToFourCans(){
        Item juiceItem2 = new Item("Can of Juice", BigDecimal.valueOf(0.53));
        juiceItem2.setId(juiceItem.getId());

        Item juiceItem3 = new Item("Can of Juice", BigDecimal.valueOf(0.53));
        juiceItem3.setId(juiceItem.getId());

        Item juiceItem4 = new Item("Can of Juice", BigDecimal.valueOf(0.53));
        juiceItem4.setId(juiceItem.getId());

        Offer juiceOffer = new Offer("B2G1F", juiceItem, "Buy 2 get 1 Free", BigDecimal.ZERO, 2L);
        juiceOffer.setId(1L);

        List<Offer> offers = new ArrayList<>();
        offers.add(juiceOffer);

        basketService.addItemToBasket(juiceItem);
        basketService.addItemToBasket(juiceItem2);
        basketService.addItemToBasket(juiceItem3);
        basketService.addItemToBasket(juiceItem4);

        when(mockItemRepository.findById(juiceItem.getId())).thenReturn(juiceItem);
        when(mockItemRepository.findById(juiceItem2.getId())).thenReturn(juiceItem2);
        when(mockItemRepository.findById(juiceItem3.getId())).thenReturn(juiceItem3);
        when(mockItemRepository.findById(juiceItem4.getId())).thenReturn(juiceItem4);
        when(mockOfferRepository.findAll()).thenReturn(offers);

        BigDecimal expectedTotal = juiceItem.getCost().add(juiceItem2.getCost().add(juiceItem3.getCost()).add(juiceItem4.getCost()));

        Long amountInBasket = basketService.getBasket().get(juiceItem.getId());

        Assert.assertEquals(Long.valueOf(4), amountInBasket);

        BigDecimal actualCostBeforeDiscount = basketService.totalCostBeforeDiscount();
        BigDecimal actualCostAfterDiscount = basketService.applyOfferAndGetNewTotal();

        Assert.assertEquals(Long.valueOf(6), basketService.getBasket().get(juiceItem.getId()));
        Assert.assertEquals(expectedTotal, actualCostAfterDiscount);
        Assert.assertEquals(actualCostBeforeDiscount, actualCostAfterDiscount);
    }

}
