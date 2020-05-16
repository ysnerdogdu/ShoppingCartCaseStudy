package cart;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeliveryCostCalculatorTest {

    private DeliveryCostCalculator deliveryCostCalculator;
    private ShoppingCart mockShoppingCart;

    private final int numberOfCategories = 5;
    private final int numberOfProducts = 20;
    private final float costPerDelivery = 3.5f;
    private final float costPerProduct = 2.5F;
    private final float fixedCost = 2.99f;


    @BeforeEach
    void setUp() {
        mockShoppingCart = Mockito.mock(ShoppingCart.class);
        Mockito.when(mockShoppingCart.getNumberOfDeliveries()).thenReturn(numberOfCategories);
        Mockito.when(mockShoppingCart.getNumberOfProducts()).thenReturn(numberOfProducts);

        deliveryCostCalculator = new DeliveryCostCalculator(costPerDelivery, costPerProduct, fixedCost);
    }

    @AfterEach
    void tearDown() {
        deliveryCostCalculator = null;
    }

    @Test
    void calculateFor() {

        double deliveryCost = (costPerDelivery * numberOfCategories) + (costPerProduct * numberOfProducts) + fixedCost;

        Assertions.assertEquals(deliveryCost, deliveryCostCalculator.calculateFor(mockShoppingCart));
    }
}