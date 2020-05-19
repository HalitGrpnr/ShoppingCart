package org.project.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.DiscountType;
import org.project.domain.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

    private static Category food;
    private static Category electronic;
    private static Category clothes;

    private static Product apple;
    private static Product almond;
    private static Product phone;
    private static Product tshirt;

    @Mock
    DeliveryCostCalculatorService calculatorService;

    @Mock
    DeliveryCostCalculator calculator;

    @InjectMocks
    private CartServiceImpl cartService;


    @BeforeAll
    public static void setUp(){

        food = new Category("food");
        electronic = new Category("electronic");
        clothes = new Category("clothes");

        apple = new Product("Apple", 100.0, food);
        almond = new Product("Almonds", 150.0, food);
        phone = new Product("Phone", 1000.0, electronic);
        tshirt = new Product("Tshirt", 40.0, clothes);
    }

    @Test
    void addItem(){
        Cart cart = new Cart();
        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        Map<Category, Map<Product, Integer>> mockedProductsMap = new HashMap<>();
        Map<Product, Integer> foodMap = new HashMap<>();
        foodMap.put(apple, 3);
        foodMap.put(almond, 1);
        mockedProductsMap.put(food, foodMap);

        cartService.addItem(apple, 3);
        cartService.addItem(almond, 1);


        Map<Category, Map<Product, Integer>> productsMap = cart.getProducts();

        assertEquals(mockedProductsMap.get(food), productsMap.get(food));
        assertEquals(1, productsMap.size());
        assertEquals(3, productsMap.get(food).get(apple));
    }

    @Test
    void applyCoupon_typeRate_applied(){
        Cart cart = new Cart();
        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        cartService.addItem(apple, 3);
        cartService.addItem(almond, 1);

        Coupon coupon = new Coupon(100.0, 10.0, DiscountType.RATE);

        cartService.applyCoupon(coupon);

        assertEquals(450.0, cart.getTotalAmount());
        assertEquals(45.0, cart.getTotalDiscount());
        assertEquals(405.0, cartService.getTotalAmountAfterDiscounts());
    }

    @Test
    void applyCoupon_typeRate_notApplied(){
        Cart cart = new Cart();
        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        cartService.addItem(apple, 3);
        cartService.addItem(almond, 1);

        Coupon coupon = new Coupon(500.0, 10.0, DiscountType.RATE);

        cartService.applyCoupon(coupon);

        assertEquals(450.0, cart.getTotalAmount());
        assertEquals(0.0, cart.getTotalDiscount());
        assertEquals(450.0, cartService.getTotalAmountAfterDiscounts());
    }

    @Test
    void applyCoupon_typeAmount_applied(){
        Cart cart = new Cart();
        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        cartService.addItem(apple, 3);
        cartService.addItem(almond, 1);

        Coupon coupon = new Coupon(100.0, 15.0, DiscountType.AMOUNT);

        cartService.applyCoupon(coupon);

        assertEquals(450.0, cart.getTotalAmount());
        assertEquals(15.0, cart.getTotalDiscount());
        assertEquals(435.0, cartService.getTotalAmountAfterDiscounts());
    }

    @Test
    void applyCoupon_typeAmount_notApplied(){
        Cart cart = new Cart();
        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        cartService.addItem(apple, 3);
        cartService.addItem(almond, 1);

        Coupon coupon = new Coupon(500.0, 15.0, DiscountType.AMOUNT);

        cartService.applyCoupon(coupon);

        assertEquals(450.0, cart.getTotalAmount());
        assertEquals(0.0, cart.getTotalDiscount());
        assertEquals(450.0, cartService.getTotalAmountAfterDiscounts());
    }

    @Test
    void applyCampaign_applied(){
        Cart cart = new Cart();
        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        cartService.addItem(apple, 3);
        cartService.addItem(almond, 1);
        cartService.addItem(tshirt, 6);
        cartService.addItem(phone, 2);

        Campaign campaign1 = new Campaign(food, 20.0, 2, DiscountType.RATE);
        Campaign campaign2 = new Campaign(food, 30.0, 3, DiscountType.RATE);
        Campaign campaign3 = new Campaign(electronic, 150.0, 1, DiscountType.AMOUNT);
        Campaign campaign4 = new Campaign(clothes, 10.0, 5, DiscountType.RATE);

        cartService.applyCampaigns(Arrays.asList(campaign1, campaign2, campaign3, campaign4));

        assertEquals(2690.0, cart.getTotalAmount());
        assertEquals(309.0, cart.getTotalDiscount());
        assertEquals(2381.0, cartService.getTotalAmountAfterDiscounts());
    }

    @Test
    void applyCampaign_applied_2(){
        Cart cart = new Cart();
        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        cartService.addItem(apple, 8);
        cartService.addItem(almond, 2);
        cartService.addItem(tshirt, 2);

        Campaign campaign1 = new Campaign(food, 5.0, 3, DiscountType.RATE);
        Campaign campaign2 = new Campaign(food, 60.0, 5, DiscountType.AMOUNT);
        Campaign campaign3 = new Campaign(clothes, 10.0, 3, DiscountType.RATE);


        cartService.applyCampaigns(Arrays.asList(campaign1, campaign2, campaign3));

        assertEquals(1180.0, cart.getTotalAmount());
        assertEquals(60.0, cart.getTotalDiscount());
        assertEquals(1120.0, cartService.getTotalAmountAfterDiscounts());
    }

    @Test
    void applyCampaign_notApplied(){
        Cart cart = new Cart();
        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        cartService.addItem(apple, 8);
        cartService.addItem(almond, 2);
        cartService.addItem(tshirt, 2);

        Campaign campaign1 = new Campaign(food, 25.0, 11, DiscountType.RATE);
        Campaign campaign2 = new Campaign(clothes, 10.0, 3, DiscountType.RATE);


        cartService.applyCampaigns(Arrays.asList(campaign1, campaign2));

        assertEquals(1180.0, cart.getTotalAmount());
        assertEquals(0.0, cart.getTotalDiscount());
        assertEquals(1180.0, cartService.getTotalAmountAfterDiscounts());
    }

    @Test
    void applyCampaignAndCoupon_bothOfapplied(){
        Cart cart = new Cart();
        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        cartService.addItem(apple, 5);
        cartService.addItem(almond, 3);
        cartService.addItem(tshirt, 2);
        cartService.addItem(phone, 2);

        Campaign campaign1 = new Campaign(food, 20.0, 3, DiscountType.RATE);
        Campaign campaign2 = new Campaign(electronic, 150.0, 1, DiscountType.AMOUNT);
        Campaign campaign3 = new Campaign(clothes, 10.0, 5, DiscountType.RATE);

        Coupon coupon = new Coupon(1500.0, 200.0, DiscountType.AMOUNT);

        cartService.applyCampaigns(Arrays.asList(campaign1, campaign2, campaign3));
        cartService.applyCoupon(coupon);

        assertEquals(3030.0, cart.getTotalAmount());
        assertEquals(540.0, cart.getTotalDiscount());
        assertEquals(2490.0, cartService.getTotalAmountAfterDiscounts());
    }

    @Test
    void applyCampaignAndCoupon_campaignApplied_couponNotApplied(){
        Cart cart = new Cart();
        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        cartService.addItem(apple, 3);
        cartService.addItem(almond, 2);
        cartService.addItem(tshirt, 1);

        Campaign campaign1 = new Campaign(food, 20.0, 3, DiscountType.RATE);
        Campaign campaign2 = new Campaign(electronic, 150.0, 1, DiscountType.AMOUNT);

        Coupon coupon = new Coupon(1000.0, 100.0, DiscountType.AMOUNT);

        cartService.applyCampaigns(Arrays.asList(campaign1, campaign2));
        cartService.applyCoupon(coupon);

        assertEquals(640.0, cart.getTotalAmount());
        assertEquals(120.0, cart.getTotalDiscount());
        assertEquals(520.0, cartService.getTotalAmountAfterDiscounts());
    }

    @Test
    void applyCampaignAndCoupon_campaignNotApplied_couponApplied(){
        Cart cart = new Cart();
        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        cartService.addItem(apple, 2);
        cartService.addItem(almond, 1);
        cartService.addItem(tshirt, 3);

        Campaign campaign1 = new Campaign(food, 20.0, 4, DiscountType.RATE);
        Campaign campaign2 = new Campaign(electronic, 150.0, 1, DiscountType.AMOUNT);

        Coupon coupon = new Coupon(300.0, 20.0, DiscountType.AMOUNT);

        cartService.applyCampaigns(Arrays.asList(campaign1, campaign2));
        cartService.applyCoupon(coupon);

        assertEquals(470.0, cart.getTotalAmount());
        assertEquals(20.0, cart.getTotalDiscount());
        assertEquals(450.0, cartService.getTotalAmountAfterDiscounts());
    }

    @Test
    void applyCoupon_withInvalidType_throwsException(){
        Cart cart = new Cart();
        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        cartService.addItem(apple, 2);
        cartService.addItem(almond, 1);
        cartService.addItem(tshirt, 3);


        Coupon coupon = new Coupon(300.0, 20.0, null);

        assertThrows(IllegalArgumentException.class, () -> cartService.applyCoupon(coupon));

    }

    @Test
    void calculateDeliveryCost(){
        Cart cart = new Cart();
        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        when(calculatorService.calculateFor(any(Cart.class), any(DeliveryCostCalculator.class))).thenReturn(29.99);
        cartService.calculateDeliveryCost();
        assertEquals(29.99, cart.getDeliveryCost());
    }

    @Test
    void print(){
        Cart cart = new Cart();

        cartService = new CartServiceImpl(cart, calculator, calculatorService);

        cartService.addItem(apple, 2);
        cartService.addItem(almond, 2);
        cartService.addItem(tshirt, 3);
        cartService.addItem(phone, 1);

        Campaign campaign1 = new Campaign(food, 20.0, 3, DiscountType.RATE);
        Coupon coupon = new Coupon(1500.0, 200.0, DiscountType.AMOUNT);

        cartService.applyCampaigns(Arrays.asList(campaign1));
        cartService.applyCoupon(coupon);

        when(calculatorService.calculateFor(any(Cart.class), any(DeliveryCostCalculator.class))).thenReturn(29.99);
        cartService.calculateDeliveryCost();
        cartService.calculateTotalPrice();

        String cartDetails = cartService.print();

        assertTrue(cartDetails.contains("Category{title='clothes'}"));
        assertTrue(cartDetails.contains("Product{title='Tshirt', price=40.0}=3}"));
        assertTrue(cartDetails.contains("Category{title='food'}"));
        assertTrue(cartDetails.contains("Product{title='Almonds', price=150.0}=2"));
        assertTrue(cartDetails.contains("Product{title='Apple', price=100.0}=2"));
        assertTrue(cartDetails.contains("Category{title='electronic'}"));
        assertTrue(cartDetails.contains("Product{title='Phone', price=1000.0}=1"));
        assertTrue(cartDetails.contains("totalAmount=1620.0"));
        assertTrue(cartDetails.contains("totalDiscount=300.0"));
        assertTrue(cartDetails.contains("deliveryCost=29.99"));
        assertTrue(cartDetails.contains("totalPrice=1349.99"));
    }
}
