package org.project.domain;

import org.project.DiscountType;

public class Coupon {

    private double minPurchase;
    private double discount;
    private DiscountType type;

    public Coupon(double minPurchase, double discount, DiscountType type) {
        this.minPurchase = minPurchase;
        this.discount = discount;
        this.type = type;
    }

    public double getMinPurchase() {
        return minPurchase;
    }

    public void setMinPurchase(double minPurchase) {
        this.minPurchase = minPurchase;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public DiscountType getType() {
        return type;
    }

    public void setType(DiscountType type) {
        this.type = type;
    }
}
