package discount;

public class Coupon {
    //properties
    private float minimumCartAmount;
    private float discountAmount;
    private DiscountType discountType;

    /**
     * Constructor to initialize Coupon object
     * @param minimumCartAmount minimum Cart amount
     * @param discountAmount discount amount
     * @param discountType selected discount type
     */
    public Coupon(float minimumCartAmount, float discountAmount, DiscountType discountType) {
        this.minimumCartAmount = minimumCartAmount;
        this.discountAmount = discountAmount;
        this.discountType = discountType;
    }

    public float getMinimumCartAmount() {
        return minimumCartAmount;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coupon coupon = (Coupon) o;

        if (Float.compare(coupon.minimumCartAmount, minimumCartAmount) != 0) return false;
        if (Float.compare(coupon.discountAmount, discountAmount) != 0) return false;
        return discountType == coupon.discountType;
    }

    @Override
    public int hashCode() {
        int result = (minimumCartAmount != +0.0f ? Float.floatToIntBits(minimumCartAmount) : 0);
        result = 31 * result + (discountAmount != +0.0f ? Float.floatToIntBits(discountAmount) : 0);
        result = 31 * result + (discountType != null ? discountType.hashCode() : 0);
        return result;
    }
}
