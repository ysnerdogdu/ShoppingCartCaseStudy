package discount;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.print.attribute.standard.NumberUp;

import static org.junit.jupiter.api.Assertions.*;

class DiscountUtilTest {

    private Campaign mockCampaign;
    private Coupon mockCoupon;

    private final float campaignDiscountAmount = 20;
    private final float couponDiscountAmount = 100;

    @BeforeEach
    void setUp() {
        mockCampaign = Mockito.mock(Campaign.class);
        Mockito.when(mockCampaign.getDiscountAmount()).thenReturn(campaignDiscountAmount);
        Mockito.when(mockCampaign.getDiscountType()).thenReturn(DiscountType.RATE);

        mockCoupon = Mockito.mock(Coupon.class);
        Mockito.when(mockCoupon.getDiscountAmount()).thenReturn(couponDiscountAmount);
        Mockito.when(mockCoupon.getDiscountType()).thenReturn(DiscountType.AMOUNT);
    }

    @AfterEach
    void tearDown() {
        mockCampaign = null;
        mockCoupon = null;
    }

    @Test
    void makeCampaignDiscountToProduct() {
        // calculate expected discount amount
        double totalCategoryAmount = 800;
        double expectedDiscountAmount =  (totalCategoryAmount * campaignDiscountAmount)  / 100;
        // call static function
        double actualCampaignDiscount = DiscountUtil.makeCampaignDiscountToProduct(mockCampaign, totalCategoryAmount);
        // check result
        Assertions.assertEquals(expectedDiscountAmount, actualCampaignDiscount);
    }

    @Test
    void makeCouponDiscountToProduct() {
        // call static function
        double totalCartAmount = 1200;
        double actualCampaignDiscount = DiscountUtil.makeCouponDiscountToProduct(mockCoupon, totalCartAmount);
        // check result
        Assertions.assertEquals(couponDiscountAmount, actualCampaignDiscount);
    }
}