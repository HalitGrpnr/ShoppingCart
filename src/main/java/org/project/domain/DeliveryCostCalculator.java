package org.project.domain;

import java.math.BigDecimal;
import java.util.Map;

public class DeliveryCostCalculator {

    private BigDecimal costPerDelivery;
    private BigDecimal costPerProduct;
    private BigDecimal fixedCost;

    public DeliveryCostCalculator(double costPerDelivery, double costPerProduct, double fixedCost) {
        this.costPerDelivery = BigDecimal.valueOf(costPerDelivery);
        this.costPerProduct = BigDecimal.valueOf(costPerProduct);
        this.fixedCost = BigDecimal.valueOf(fixedCost);
    }

    public BigDecimal getCostPerDelivery() {
        return costPerDelivery;
    }

    public void setCostPerDelivery(BigDecimal costPerDelivery) {
        this.costPerDelivery = costPerDelivery;
    }

    public BigDecimal getCostPerProduct() {
        return costPerProduct;
    }

    public void setCostPerProduct(BigDecimal costPerProduct) {
        this.costPerProduct = costPerProduct;
    }

    public BigDecimal getFixedCost() {
        return fixedCost;
    }

    public void setFixedCost(BigDecimal fixedCost) {
        this.fixedCost = fixedCost;
    }
}
