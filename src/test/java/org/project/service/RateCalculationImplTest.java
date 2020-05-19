package org.project.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateCalculationImplTest {

    @Test
    void calculateDiscount() {
        RateCalculationImpl calculation = new RateCalculationImpl();
        double discount = calculation.calculateDiscount(10, 300);

        assertEquals(30, discount);
    }
}