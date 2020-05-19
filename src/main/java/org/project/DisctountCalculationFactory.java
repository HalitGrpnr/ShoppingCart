package org.project;

import org.project.service.AmountCalculationImpl;
import org.project.service.DiscountCalculationService;
import org.project.service.RateCalculationImpl;

public class DisctountCalculationFactory {
    public DiscountCalculationService getService(DiscountType type){
        if (type == null){
            return null;
        }

        if (DiscountType.AMOUNT.equals(type)){
            return new AmountCalculationImpl();
        }

        if (DiscountType.RATE.equals(type)){
            return new RateCalculationImpl();
        }

        return null;
    }
}
