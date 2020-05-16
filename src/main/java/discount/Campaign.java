package discount;

import cart.Category;

public class Campaign {
    // properties
    private Category category;
    private float discountAmount;
    private int minimumProductCount;
    private DiscountType discountType;

    /**
     * Constructor
     * @param category cart.Category
     * @param discountAmount discount amount
     * @param productLimit minimum product count
     * @param selectedType selectedDiscountType
     */
    public Campaign(Category category, float discountAmount, int productLimit, DiscountType selectedType) {
        this.category = category;
        this.discountAmount = discountAmount;
        this.minimumProductCount = productLimit;
        this.discountType = selectedType;
    }

    public Category getCategory() {
        return category;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public int getMinimumProductCount() {
        return minimumProductCount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }
}
