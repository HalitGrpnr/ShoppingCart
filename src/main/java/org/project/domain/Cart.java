package org.project.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private double totalAmount;
    private double totalDiscount;
    private double deliveryCost;
    private double totalPrice;
    private Map<Category, Map<Product, Integer>> products = new HashMap<>();

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Map<Category, Map<Product, Integer>> getProducts() {
        return products;
    }

    public void setProducts(Map<Category, Map<Product, Integer>> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "products=" + products +
                "\ntotalAmount=" + totalAmount +
                "\ntotalDiscount=" + totalDiscount +
                "\ndeliveryCost=" + deliveryCost +
                "\ntotalPrice=" + totalPrice +
                '}';
    }
}
