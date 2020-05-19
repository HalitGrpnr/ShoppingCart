package org.project.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.domain.Cart;
import org.project.domain.Category;
import org.project.domain.DeliveryCostCalculator;
import org.project.domain.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryCostCalculatorServiceImplTest {

    @Mock
    Cart cart;

    @Mock
    DeliveryCostCalculator calculator;

    @InjectMocks
    DeliveryCostCalculatorServiceImpl calculatorService;


    private static Category food;
    private static Category clothes;

    private static Product apple;
    private static Product almond;
    private static Product tshirt;

    @BeforeAll
    public static void setUp(){
        food = new Category("food");
        clothes = new Category("clothes");

        apple = new Product("Apple", 100.0, food);
        almond = new Product("Almonds", 150.0, food);
        tshirt = new Product("Tshirt", 40.0, clothes);
    }

    @Test
    void calculateFor_oneCategory() {
        Map<Category, Map<Product, Integer>> productsMap = new HashMap<>();
        Map<Product, Integer> foodMap = new HashMap<>();
        foodMap.put(apple, 3);
        foodMap.put(almond, 1);
        productsMap.put(food, foodMap);

        given(cart.getProducts()).willReturn(productsMap);

        when(calculator.getCostPerProduct()).thenReturn(BigDecimal.valueOf(3.0));
        when(calculator.getCostPerDelivery()).thenReturn(BigDecimal.valueOf(5.0));
        when(calculator.getFixedCost()).thenReturn(BigDecimal.valueOf(2.99));

        assertEquals(13.99, calculatorService.calculateFor(cart, calculator));

    }

    @Test
    void calculateFor_twoCategory() {
        Map<Category, Map<Product, Integer>> productsMap = new HashMap<>();
        Map<Product, Integer> foodMap = new HashMap<>();
        Map<Product, Integer> clothesMap = new HashMap<>();

        foodMap.put(apple, 9);
        foodMap.put(almond, 4);
        productsMap.put(food, foodMap);

        clothesMap.put(tshirt, 5);
        productsMap.put(clothes, clothesMap);

        given(cart.getProducts()).willReturn(productsMap);

        when(calculator.getCostPerProduct()).thenReturn(BigDecimal.valueOf(3.25));
        when(calculator.getCostPerDelivery()).thenReturn(BigDecimal.valueOf(4.15));
        when(calculator.getFixedCost()).thenReturn(BigDecimal.valueOf(1.55));

        assertEquals(19.60, calculatorService.calculateFor(cart, calculator));

    }
}