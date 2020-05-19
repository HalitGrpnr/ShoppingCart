package org.project.service;

import org.project.domain.Campaign;
import org.project.domain.Coupon;
import org.project.domain.Product;

import java.util.List;
import java.util.Map;

public interface CartService {
    void addItem(Product product, int quantity);

    void applyCampaigns(List<Campaign> discounts);

    void applyCoupon(Coupon coupon);

    double getTotalAmountAfterDiscounts();

    double getCouponDiscount(Coupon coupon);

    double getCampaignDiscount(Map<Product, Integer> productIntegerMap, Campaign campaign);

    void calculateDeliveryCost();

    void calculateTotalPrice();

    String print();
}
