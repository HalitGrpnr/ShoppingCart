package org.project.service;

import org.project.DiscountType;
import org.project.DisctountCalculationFactory;
import org.project.domain.*;

import java.util.*;

public class CartServiceImpl implements CartService {

    private Cart cart;
    private DeliveryCostCalculator deliveryCostCalculator;
    private DeliveryCostCalculatorService calculatorService;

    public CartServiceImpl(Cart cart, DeliveryCostCalculator deliveryCostCalculator, DeliveryCostCalculatorService calculatorService) {
        this.cart = cart;
        this.deliveryCostCalculator = deliveryCostCalculator;
        this.calculatorService = calculatorService;
    }

    @Override
    public void addItem(Product product, int quantity) {
        if (cart.getProducts().containsKey(product.getCategory())) {
            cart.getProducts().get(product.getCategory()).put(product, quantity);
        } else {
            Map<Product, Integer> newItemMap = new HashMap<>();
            newItemMap.put(product, quantity);
            cart.getProducts().put(product.getCategory(), newItemMap);
        }
        addNewItemsToTotalAmountOfCart(product, quantity);
    }

    @Override
    public void applyCampaigns(List<Campaign> campaignsList) {
        Map<Category, List<Campaign>> campaignMap = parseCampaignsForCategories(campaignsList);

        for (Map.Entry<Category, Map<Product, Integer>> entry : cart.getProducts().entrySet()) {
            Category category = entry.getKey();
            Map<Product, Integer> productMap = entry.getValue();
            if (campaignMap.get(category) != null) {
                Campaign properCampaign = findProperCampaignForCategory(productMap, campaignMap.get(category));
                if (properCampaign != null) {
                    cart.setTotalDiscount(cart.getTotalDiscount() + getCampaignDiscount(productMap, properCampaign));
                }
            }
        }
    }

    @Override
    public void applyCoupon(Coupon coupon) {
        cart.setTotalDiscount(cart.getTotalDiscount() + getCouponDiscount(coupon));
    }

    private double getTotalAmountForCategory(Map<Product, Integer> productQuantityMap) {
        double totalAmountForCategory = 0;

        for (Map.Entry<Product, Integer> entry : productQuantityMap.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalAmountForCategory += product.getPrice() * quantity;
        }
        return totalAmountForCategory;
    }

    private Campaign findProperCampaignForCategory(Map<Product, Integer> productQuantityMap, List<Campaign> campaignList) {
        Campaign properCampaign = null;

        int totalQuantityOfCategory = productQuantityMap.values().stream().reduce(Integer::sum).orElse(0);
        double totalAmountForCategory = getTotalAmountForCategory(productQuantityMap);


        double discountForProperCampaign = 0;
        for (Campaign campaign : campaignList) {
            if (totalQuantityOfCategory > campaign.getQuantity()) {
                if (DiscountType.AMOUNT.equals(campaign.getType())) {
                    if (campaign.getDiscount() > discountForProperCampaign) {
                        properCampaign = campaign;
                        discountForProperCampaign = campaign.getDiscount();
                    }
                } else if (DiscountType.RATE.equals(campaign.getType())) {
                    double discountForRate = totalAmountForCategory * (campaign.getDiscount() / 100);
                    if (discountForRate > discountForProperCampaign) {
                        properCampaign = campaign;
                        discountForProperCampaign = discountForRate;
                    }
                }
            }
        }
        return properCampaign;
    }

    private Map<Category, List<Campaign>> parseCampaignsForCategories(List<Campaign> campaignsList) {
        Map<Category, List<Campaign>> campaignMap = new HashMap<>();

        for (Campaign campaign : campaignsList) {
            if (campaignMap.containsKey(campaign.getCategory())) {
                campaignMap.get(campaign.getCategory()).add(campaign);
            } else {
                List<Campaign> newCampaigns = new ArrayList<>();
                newCampaigns.add(campaign);
                campaignMap.put(campaign.getCategory(), newCampaigns);
            }
        }
        return campaignMap;
    }

    @Override
    public double getTotalAmountAfterDiscounts() {
        return cart.getTotalAmount() - cart.getTotalDiscount();
    }

    @Override
    public double getCouponDiscount(Coupon coupon) {
        double couponDiscount = 0.0;
        if (getTotalAmountAfterDiscounts() >= coupon.getMinPurchase()) {
            couponDiscount = getDiscountCalculationService(coupon.getType())
                    .calculateDiscount(coupon.getDiscount(), cart.getTotalAmount());
        }
        return couponDiscount;
    }

    private DiscountCalculationService getDiscountCalculationService(DiscountType type) throws IllegalArgumentException {
        DisctountCalculationFactory factory = new DisctountCalculationFactory();
        DiscountCalculationService service = factory.getService(type);
        if (service != null){
            return service;
        } else {
            throw new IllegalArgumentException("Discount type is null");
        }
    }

    @Override
    public double getCampaignDiscount(Map<Product, Integer> productIntegerMap, Campaign campaign) {
        double totalAmountForCategory = getTotalAmountForCategory(productIntegerMap);
        return getDiscountCalculationService(campaign.getType()).calculateDiscount(campaign.getDiscount(), totalAmountForCategory);
    }

    @Override
    public void calculateDeliveryCost() {
        double deliveryCost = calculatorService.calculateFor(cart, deliveryCostCalculator);
        cart.setDeliveryCost(deliveryCost);
    }

    @Override
    public String print() {
        return cart.toString();
    }


    private void addNewItemsToTotalAmountOfCart(Product product, int quantity) {

        double newItemsAmount = product.getPrice() * quantity;

        cart.setTotalAmount(cart.getTotalAmount() + newItemsAmount);
    }

    @Override
    public void calculateTotalPrice() {
        cart.setTotalPrice(getTotalAmountAfterDiscounts() + cart.getDeliveryCost());
    }
}
