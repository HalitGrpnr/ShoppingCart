package org.project.domain;

import org.project.DiscountType;

public class Campaign {

    private Category category;
    private Double discount;
    private int quantity;
    private DiscountType type;

    public Campaign(Category category, Double discount, int quantity, DiscountType type) {
        this.category = category;
        this.discount = discount;
        this.quantity = quantity;
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DiscountType getType() {
        return type;
    }

    public void setType(DiscountType type) {
        this.type = type;
    }
}
