package discount;

/**
 * Utility class to calculate discount for campaign and coupons
 */
public class  DiscountUtil {

    /**
     * Utility method to calculate given campaign discount
     * @param campaign Campaign
     * @param totalCategoryAmount total product price on campaign category
     * @return discount amount
     */
    public static double makeCampaignDiscountToProduct(Campaign campaign, double totalCategoryAmount) {
        DiscountType selectedDiscountType = campaign.getDiscountType();
        float discountAmount = campaign.getDiscountAmount();
        return makeDiscountForGivenAmount(discountAmount, selectedDiscountType, totalCategoryAmount);
    }

    /**
     * Utility method to calculate given coupon discount
     * @param coupon Coupon instance
     * @param totalCartAmount total product price on cart
     * @return discount amount
     */
    public static double makeCouponDiscountToProduct(Coupon coupon, double totalCartAmount) {
        DiscountType selectedDiscountType = coupon.getDiscountType();
        float discountAmount = coupon.getDiscountAmount();
        return makeDiscountForGivenAmount(discountAmount, selectedDiscountType, totalCartAmount);
    }

    /**
     * Helper method to calculate discount amount
     * @param discountAmount float
     * @param discountType DiscountType
     * @param totalAmount double
     * @return double
     */
    private static double makeDiscountForGivenAmount(float discountAmount, DiscountType discountType, double totalAmount) {
        double amount = 0;
        if (discountType == DiscountType.AMOUNT) {
            amount = discountAmount;

        } else if (discountType == DiscountType.RATE){
            amount = (totalAmount * discountAmount)  / 100;
        }
        return amount;
    }
}
