package cart;

import cart.Category;
import cart.DeliveryCostCalculator;
import cart.Product;
import discount.Campaign;
import discount.Coupon;
import discount.DiscountUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShoppingCart {
    // constants
    public final float FIXED_COST = 2.99f;
    private final Map<Category, Map<Product, Integer>> cartMap;
    private Map<Category, Double> campaignDiscountMap;
    private Set<Coupon> couponSet;
    private float totalCouponDiscount;
    private DeliveryCostCalculator deliveryCostCalculator;
    private double deliveryCost;

    public ShoppingCart() {
        // create map with no default capacity
        cartMap = new HashMap<Category, Map<Product, Integer>>();
        campaignDiscountMap = new HashMap<Category, Double>();
        couponSet = new HashSet<Coupon>();
        totalCouponDiscount = 0;
        // costPerDelivery and costPerProduct değerlerinin nasıl elde edileceği hakkında case study'de bir şey bulamadım
        // o yüzden cart.ShoppingCart objesi yaratılırken random sayılarla cart.DeliveryCostCalculator instance'ı oluşturdum.
        deliveryCostCalculator = new DeliveryCostCalculator(10, 10, FIXED_COST);
    }


    /**
     * Add given product to map according its category
     * @param product cart.Product instance
     * @param quantity product quantity
     */
    public void addItem(Product product, int quantity) {
        if (product != null &&  quantity > 0) {
            //get product category
            Category category = product.getCategory();
            // if any product with this category has not been added yet to cart,
            // we need add default entry with this category to cartMap
            if (!cartMap.containsKey(category)) {
                cartMap.put(category, new HashMap<Product, Integer>());
            }
            // get product quantity map from cart map by category
            Map<Product, Integer> productQuantityMap = cartMap.get(category);
            // if given product has been added before, we need to update quantity field
            if (productQuantityMap.containsKey(product)) {
                // calculate new quantity
                int prevQuantity = productQuantityMap.get(product);
                int newQuantity = prevQuantity + quantity;
                productQuantityMap.put(product, newQuantity);
            } else {// if given product has not been added before
                productQuantityMap.put(product, quantity);
            }
            // calculate delivery cost whenever any product has been added
            deliveryCost =  deliveryCostCalculator.calculateFor(this);
        }
    }

    /**
     * Method to apply campaigns discount to cart
     * @param campaigns array of campaign
     */
    public void applyDiscounts(Campaign... campaigns) {
        for (Campaign campaign : campaigns) {
            // call method to apply current campaign to cart
            applyCampaignToCart(campaign);
        }
    }

    /**
     * Helper method to apply given campaign to cart
     * @param campaign Campaign instance
     */
    private void applyCampaignToCart(Campaign campaign) {
        // get campaign category
        Category category = campaign.getCategory();
        // get product quantity map by campaign category
        Map<Product, Integer> productQuantityMap = cartMap.get(category);
        // if any product has been added to cart with campaign category
        if (productQuantityMap != null) {
            int minimumProductCount = campaign.getMinimumProductCount();
            int productCountInCategory = 0;
            double totalCategoryPrice = 0;
            // get product set of given campaign category
            Set<Product> products = productQuantityMap.keySet();
            for (Product product : products) {
                // get each product quantity
                Integer productQuantity = productQuantityMap.get(product);
                // get total price for products on same category
                totalCategoryPrice += productQuantity * product.getPrice();
                // increment total product count of campaign categoty in cart
                productCountInCategory += productQuantity;
            }

            // check that total product count of campaign category is higher than given campaign minimum product count
            if (productCountInCategory >= minimumProductCount) {
                double campaignDiscount = 0;
                // if any campaign has been applied to current category before
                if (campaignDiscountMap.containsKey(category)) {
                    campaignDiscount = campaignDiscountMap.get(category);
                }
                // subtract before campaign discounts from category total price
                totalCategoryPrice -= campaignDiscount;
                // call utility function to calculate discount amount for given campaign
                double discountAmount = DiscountUtil.makeCampaignDiscountToProduct(campaign, totalCategoryPrice);
                campaignDiscount += discountAmount;
                // update campaign discount of current category on map
                campaignDiscountMap.put(category, campaignDiscount);

                // Since campaign discount has been made, we need to apply coupon discounts again
                totalCouponDiscount = 0;
                Set<Coupon> copyCouponSet = new HashSet<Coupon>(couponSet);
                // call applyCoupon function for each coupon
                for (Coupon coupon : copyCouponSet) {
                    applyCoupon(coupon);
                }
            }
        }
    }

    /**
     * Method to apply coupon discounts to cart
     * @param coupon Coupon instance
     */
    public void applyCoupon(Coupon coupon) {
        // get total original price amount of products in cart
        double totalCartAmount = getOriginalTotalCartAmount();
        // get total campaign discount applied to cart
        double totalCampaignDiscount = getCampaignDiscount();
        // subtract total campaign discounts from total original price
        double afterCampaignDiscounts  = totalCartAmount - totalCampaignDiscount;
        // subtract previous applied coupon discount
        double afterPrevCouponsDiscount = afterCampaignDiscounts - totalCouponDiscount;

        double minimumCartAmount = coupon.getMinimumCartAmount();
        // if given coupon minimum amount limit is lower than total amount in cart
        if (afterPrevCouponsDiscount >= minimumCartAmount) {
            // call utility function to calculate coupon discount
            double discountAmount = DiscountUtil.makeCouponDiscountToProduct(coupon, afterPrevCouponsDiscount);
            totalCouponDiscount += discountAmount;
            // add applied coupon to coupon set
            couponSet.add(coupon);
        } else {
            couponSet.remove(coupon);
        }
    }

    /**
     * Get total amount of products in cart after all discount has been applied
     * @return total amount
     */
    public double getTotalAmountAfterDiscounts() {
        double totalCartAmount = getOriginalTotalCartAmount();
        totalCartAmount -= getCampaignDiscount();
        totalCartAmount -= totalCouponDiscount;

        return totalCartAmount;
    }

    /**
     * Get total campaign discounts
     * @return total campaign discount
     */
    public double getCampaignDiscount() {
        double totalCampaignDiscount = 0;
        for (double discount : campaignDiscountMap.values()) {
            totalCampaignDiscount += discount;
        }
        return totalCampaignDiscount;
    }

    /**
     * Get tatal coupon discount
     * @return double
     */
    public double getCouponDiscount() {
        return totalCouponDiscount;
    }

    /**
     * Get delivery cost
     * @return double
     */
    public double getDeliveryCost() {
        return deliveryCost;
    }

    /**
     * Get number of different categories in cart
     * @return integer
     */
    public int getNumberOfDeliveries() {
        return cartMap.size();
    }

    /**
     * Get total number of distinct products in cart
     * @return Integer
     */
    public int getNumberOfProducts() {
        int numberOfDifferentProducts = 0;
        for (Category category : cartMap.keySet()) {
            Map<Product, Integer> productQuantityMap = cartMap.get(category);
            numberOfDifferentProducts += productQuantityMap.size();
        }
        return numberOfDifferentProducts;
    }

    /**
     * Get total original price amount of products in cart
     * @return total original price
     */
    private double getOriginalTotalCartAmount() {
        double totalCartAmount = 0;
        for (Category category : cartMap.keySet()) {
            // get cart.Product quantity map of current category
            Map<Product, Integer> productQuantityMap = cartMap.get(category);
            for (Product product : productQuantityMap.keySet()) {
                float unitPrice = product.getPrice();
                int quantity = productQuantityMap.get(product);
                totalCartAmount += (unitPrice * quantity);
            }
        }
        return totalCartAmount;
    }

    /**
     * Method to print products info grouped by category
     */
    public void print() {
        for (Category category : cartMap.keySet()) {
            // get map of product quantity by current category
            Map<Product, Integer> productQuantityMap = cartMap.get(category);
            for (Product product : productQuantityMap.keySet()) {
                // product quantity
                int quantity = productQuantityMap.get(product);
                // total price of current product
                double totalProductPrice = (product.getPrice() * quantity);
                // find category discount
                double categoryDiscount = 0;
                if (campaignDiscountMap.containsKey(category)) {
                    categoryDiscount = campaignDiscountMap.get(category);
                }
                // calculate total discount by adding campaign discount with coupon discount
                double totalDiscount = categoryDiscount + totalCouponDiscount;
                System.out.println("CategoryName: " + category.getTitle() + " ProductName: " + product.getTitle()
                        + " Quantity: " +  quantity + " UnitPrice: " + product.getPrice()
                        + " Total Price: " + totalProductPrice + " Category Discount: " + totalDiscount);
            }
        }
        System.out.println("Total Price: " + getTotalAmountAfterDiscounts() + " Delivery Cost: " + getDeliveryCost());
    }
}
