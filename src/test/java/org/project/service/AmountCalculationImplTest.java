package org.project.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmountCalculationImplTest {

    @Test
    void calculateDiscount() {
        AmountCalculationImpl calculation = new AmountCalculationImpl();
        double discount = calculation.calculateDiscount(20, 400);

        assertEquals(20, discount);
    }
}