package org.project.service;

import org.project.domain.Cart;
import org.project.domain.DeliveryCostCalculator;

public interface DeliveryCostCalculatorService {
    public double calculateFor(Cart cart, DeliveryCostCalculator calculator);
}
