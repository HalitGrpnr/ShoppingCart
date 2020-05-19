package org.project;

import org.junit.jupiter.api.Test;
import org.project.service.AmountCalculationImpl;
import org.project.service.DiscountCalculationService;
import org.project.service.RateCalculationImpl;

import static org.junit.jupiter.api.Assertions.*;

class DisctountCalculationFactoryTest {

    @Test
    void getService_forRate() {
        DisctountCalculationFactory factory = new DisctountCalculationFactory();
        DiscountCalculationService rateService = factory.getService(DiscountType.RATE);

        assertTrue(rateService instanceof RateCalculationImpl);
    }

    @Test
    void getService_forAmount() {
        DisctountCalculationFactory factory = new DisctountCalculationFactory();
        DiscountCalculationService amountService = factory.getService(DiscountType.AMOUNT);

        assertTrue(amountService instanceof AmountCalculationImpl);
    }

    @Test
    void getService_forNullType() {
        DisctountCalculationFactory factory = new DisctountCalculationFactory();
        DiscountCalculationService nullService = factory.getService(null);

        assertEquals(null, nullService);
    }
}