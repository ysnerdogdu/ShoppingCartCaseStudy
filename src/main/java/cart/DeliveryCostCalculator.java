package cart;

public class DeliveryCostCalculator {
    // properties
    private float costPerDelivery;
    private float costPerProduct;
    private float fixedCost;

    /**
     * Constructor to create cart.DeliveryCostCalculator instance
     * @param costPerDelivery cost per delivery
     * @param costPerProduct cost per product
     * @param fixedCost fixed cost
     */
    public DeliveryCostCalculator(float costPerDelivery, float costPerProduct, float fixedCost) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }

    /**
     * Method to calculate delivery cost according to current cart status
     * @param cart cart.ShoppingCart instance
     * @return delivery cost
     */
    public double calculateFor(ShoppingCart cart) {
        int numberOfDeliveries = cart.getNumberOfDeliveries();
        int numberOfProducts = cart.getNumberOfProducts();

        // calculate delivery cost for given shopping cart
        return (costPerDelivery * numberOfDeliveries) + (costPerProduct * numberOfProducts) + fixedCost;
    }
}
