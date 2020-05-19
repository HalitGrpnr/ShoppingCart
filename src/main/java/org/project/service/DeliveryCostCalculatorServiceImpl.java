package org.project.service;

import org.project.domain.Cart;
import org.project.domain.DeliveryCostCalculator;

import java.math.BigDecimal;
import java.util.Map;

public class DeliveryCostCalculatorServiceImpl implements DeliveryCostCalculatorService {
    @Override
    public double calculateFor(Cart cart, DeliveryCostCalculator calculator) {
        BigDecimal numberOfDeliveries = BigDecimal.valueOf(getNumberOfDistinctCategories(cart));
        BigDecimal numberOfProducts = BigDecimal.valueOf(getNumberOfDifferentProducts(cart));
        return calculator.getCostPerDelivery().multiply(numberOfDeliveries)
                .add(calculator.getCostPerProduct().multiply(numberOfProducts))
                .add(calculator.getFixedCost()).doubleValue();
    }

    private int getNumberOfDifferentProducts(Cart cart) {
        return cart.getProducts().values().stream().map(Map::size).reduce(0, (x, y) -> x + y);
    }

    private int getNumberOfDistinctCategories(Cart cart) {
        return cart.getProducts().keySet().size();
    }
}
