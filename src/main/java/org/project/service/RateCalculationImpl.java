package org.project.service;

public class RateCalculationImpl implements DiscountCalculationService {

    @Override
    public double calculateDiscount(double discount, double totalAmountForDiscount) {
        return totalAmountForDiscount * (discount / 100);
    }
}
