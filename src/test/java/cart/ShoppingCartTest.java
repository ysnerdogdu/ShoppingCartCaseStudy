package cart;

import discount.Campaign;
import discount.Coupon;
import discount.DiscountType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    private ShoppingCart shoppingCart;
    private Category home;
    private Category spor;
    private Category food;
    private Product lamb;
    private Product tv;
    private Product soccerBall;

    @BeforeEach
    void setUp() {

        shoppingCart = new ShoppingCart();

        // create 3 categories for testing
        home = new Category("Home");
        spor = new Category("Spor");
        food = new Category("Food");

        // create 3 products for testing
        lamb =  new Product("Askılı Lamba", 100, home);
        tv = new Product("Televizyon", 1500, home);
        soccerBall =  new Product("Futbol Topu", 120, spor);


    }

    @AfterEach
    void tearDown() {
        shoppingCart = null;
    }

    @Test
    void addItem() {
        Assertions.assertEquals(0, shoppingCart.getTotalAmountAfterDiscounts());

        shoppingCart.addItem(lamb, 2);
        shoppingCart.addItem(soccerBall, 3);

        Assertions.assertEquals(560, shoppingCart.getTotalAmountAfterDiscounts());

        shoppingCart.addItem(lamb, -5);
        Assertions.assertEquals(560, shoppingCart.getTotalAmountAfterDiscounts());

    }


    @Test
    void applyDiscountsForNotEnoughProduct() {

        // add products to cart
        shoppingCart.addItem(lamb, 4);
        shoppingCart.addItem(tv, 1);
        shoppingCart.addItem(soccerBall, 2);
        // check result
        Assertions.assertEquals(2140, shoppingCart.getTotalAmountAfterDiscounts());

        // create campaign for home category
        Campaign campaign = new Campaign(home, 25, 6, DiscountType.RATE);
        // apply campaign
        shoppingCart.applyDiscounts(campaign);
        // check result, no discount is made since, minimum product limit is not enough
        Assertions.assertEquals(2140, shoppingCart.getTotalAmountAfterDiscounts());
        Assertions.assertEquals(0, shoppingCart.getCampaignDiscount());

        // create campaign for home category
        Campaign campaign2 = new Campaign(food, 5, 1, DiscountType.AMOUNT);
        // apply campaign
        shoppingCart.applyDiscounts(campaign2);
        // check result, no discount is made since, minimum product limit is not enough
        Assertions.assertEquals(2140, shoppingCart.getTotalAmountAfterDiscounts());
        Assertions.assertEquals(0, shoppingCart.getCampaignDiscount());

    }

    @Test
    void applyDiscountForDifferentCampaigns() {
        // add products to cart
        shoppingCart.addItem(lamb, 4);
        shoppingCart.addItem(tv, 1);
        shoppingCart.addItem(soccerBall, 3);
        // check result
        Assertions.assertEquals(2260, shoppingCart.getTotalAmountAfterDiscounts());

        // create campaignS
        Campaign campaign2 = new Campaign(home, 20, 3, DiscountType.RATE);
        Campaign campaign = new Campaign(spor, 100, 3, DiscountType.AMOUNT);

        // apply campaigns
        shoppingCart.applyDiscounts(campaign, campaign2);
        // check result, home(320) + spor(260)
        Assertions.assertEquals(1780, shoppingCart.getTotalAmountAfterDiscounts());
        Assertions.assertEquals(480, shoppingCart.getCampaignDiscount());
    }

    @Test
    void applyDiscountForSameCategoryCampaigns() {
        // add products to cart
        shoppingCart.addItem(lamb, 4);
        shoppingCart.addItem(tv, 2);
        // check result
        Assertions.assertEquals(3400, shoppingCart.getTotalAmountAfterDiscounts());
        // create campaignS
        Campaign campaign2 = new Campaign(home, 20, 4, DiscountType.RATE);
        Campaign campaign = new Campaign(home, 10, 3, DiscountType.RATE);

        // apply campaigns
        shoppingCart.applyDiscounts(campaign, campaign2);
        // check result, home(288)
        Assertions.assertEquals(2448, shoppingCart.getTotalAmountAfterDiscounts());
        Assertions.assertEquals(952, shoppingCart.getCampaignDiscount());
    }

    @Test
    void applyCouponWithNoCampaign() {
        // add products to cart
        shoppingCart.addItem(lamb, 4);
        shoppingCart.addItem(soccerBall, 3);
        // check result
        Assertions.assertEquals(760, shoppingCart.getTotalAmountAfterDiscounts());

        // create coupon
        Coupon coupon = new Coupon(500, 20, DiscountType.RATE);
        // apply coupon
        shoppingCart.applyCoupon(coupon);
        //check result
        Assertions.assertEquals(608, shoppingCart.getTotalAmountAfterDiscounts());
        Assertions.assertEquals(152, shoppingCart.getCouponDiscount());
    }

    @Test
    void applyCouponBeforeCampaign() {

        // add products to cart
        shoppingCart.addItem(lamb, 5);
        shoppingCart.addItem(soccerBall, 5);
        // check result
        Assertions.assertEquals(1100, shoppingCart.getTotalAmountAfterDiscounts());

        // create coupon
        Coupon coupon = new Coupon(500, 20, DiscountType.RATE);
        // create coupon
        Coupon coupon2 = new Coupon(400, 100, DiscountType.AMOUNT);
        // apply coupons
        shoppingCart.applyCoupon(coupon);
        shoppingCart.applyCoupon(coupon2);

        //check result
        Assertions.assertEquals(780, shoppingCart.getTotalAmountAfterDiscounts());
        Assertions.assertEquals(320, shoppingCart.getCouponDiscount());
        Assertions.assertEquals(0, shoppingCart.getCampaignDiscount());

        // create campaign
        Campaign campaign = new Campaign(home, 20, 4, DiscountType.RATE);
        // apply campaigns
        shoppingCart.applyDiscounts(campaign);

        //check result
        Assertions.assertEquals(700, shoppingCart.getTotalAmountAfterDiscounts());
        Assertions.assertEquals(100, shoppingCart.getCampaignDiscount());
        Assertions.assertEquals(300, shoppingCart.getCouponDiscount());

    }

    @Test
    void applyCouponAfterCampaign() {
        // add products to cart
        shoppingCart.addItem(lamb, 4);
        shoppingCart.addItem(soccerBall, 3);
        // check result
        Assertions.assertEquals(760, shoppingCart.getTotalAmountAfterDiscounts());

        // create campaign
        Campaign campaign = new Campaign(home, 20, 4, DiscountType.RATE);
        // apply campaigns
        shoppingCart.applyDiscounts(campaign);
        //check result
        Assertions.assertEquals(80, shoppingCart.getCampaignDiscount());

        // create coupon
        Coupon coupon = new Coupon(500, 20, DiscountType.RATE);
        // apply coupon
        shoppingCart.applyCoupon(coupon);

        //check result
        Assertions.assertEquals(544, shoppingCart.getTotalAmountAfterDiscounts());
        Assertions.assertEquals(80, shoppingCart.getCampaignDiscount());
        Assertions.assertEquals(136, shoppingCart.getCouponDiscount());
    }

    @Test
    void applyCouponMoreThanOne() {
        // add products to cart
        shoppingCart.addItem(lamb, 4);
        shoppingCart.addItem(soccerBall, 3);
        // check result
        Assertions.assertEquals(760, shoppingCart.getTotalAmountAfterDiscounts());

        // create coupon
        Coupon coupon = new Coupon(500, 20, DiscountType.RATE);
        // create coupon
        Coupon coupon2 = new Coupon(300, 50, DiscountType.AMOUNT);
        // apply coupons
        shoppingCart.applyCoupon(coupon);
        shoppingCart.applyCoupon(coupon2);

        //check result
        Assertions.assertEquals(558, shoppingCart.getTotalAmountAfterDiscounts());
        Assertions.assertEquals(0, shoppingCart.getCampaignDiscount());
        Assertions.assertEquals(202, shoppingCart.getCouponDiscount());

    }

    @Test
    void getTotalAmountAfterDiscounts() {
        // add products to cart
        shoppingCart.addItem(lamb, 2);
        shoppingCart.addItem(soccerBall, 5);
        // check result
        Assertions.assertEquals(800, shoppingCart.getTotalAmountAfterDiscounts());

        // create campaign
        Campaign campaign = new Campaign(spor, 20, 4, DiscountType.RATE);
        // apply campaigns
        shoppingCart.applyDiscounts(campaign);
        //check result
        Assertions.assertEquals(680, shoppingCart.getTotalAmountAfterDiscounts());

        // create coupon
        Coupon coupon = new Coupon(500, 20, DiscountType.RATE);
        // apply coupon
        shoppingCart.applyCoupon(coupon);

        //check result
        Assertions.assertEquals(544, shoppingCart.getTotalAmountAfterDiscounts());
    }

    @Test
    void getCampaignDiscount() {
        // add products to cart
        shoppingCart.addItem(lamb, 2);
        shoppingCart.addItem(soccerBall, 5);
        // check result
        Assertions.assertEquals(800, shoppingCart.getTotalAmountAfterDiscounts());

        // create campaign
        Campaign campaign = new Campaign(home, 100, 2, DiscountType.AMOUNT);
        // apply campaigns
        shoppingCart.applyDiscounts(campaign);
        //check result
        Assertions.assertEquals(100, shoppingCart.getCampaignDiscount());

        // create campaign
        Campaign campaign2 = new Campaign(spor, 20, 4, DiscountType.RATE);
        // apply campaigns
        shoppingCart.applyDiscounts(campaign2);
        //check result
        Assertions.assertEquals(220, shoppingCart.getCampaignDiscount());

        // create campaign
        Campaign campaign3 = new Campaign(spor, 25, 4, DiscountType.RATE);
        // apply campaigns
        shoppingCart.applyDiscounts(campaign3);
        //check result
        Assertions.assertEquals(340, shoppingCart.getCampaignDiscount());

        // create campaign
        Campaign campaign4 = new Campaign(home, 30, 4, DiscountType.RATE);
        // apply campaigns
        shoppingCart.applyDiscounts(campaign4);
        //check result
        Assertions.assertEquals(340, shoppingCart.getCampaignDiscount());

    }

    @Test
    void getCouponDiscount() {
        // add products to cart
        shoppingCart.addItem(lamb, 4);
        shoppingCart.addItem(soccerBall, 4);
        // check result
        Assertions.assertEquals(880, shoppingCart.getTotalAmountAfterDiscounts());

        // create coupon
        Coupon coupon = new Coupon(900, 20, DiscountType.RATE);
        // apply coupon
        shoppingCart.applyCoupon(coupon);
        //check result
        Assertions.assertEquals(0, shoppingCart.getCouponDiscount());

        // create coupon
        Coupon coupon2 = new Coupon(500, 100, DiscountType.AMOUNT);
        // apply coupon
        shoppingCart.applyCoupon(coupon2);
        //check result
        Assertions.assertEquals(100, shoppingCart.getCouponDiscount());

        // create coupon
        Coupon coupon3 = new Coupon(500, 50, DiscountType.RATE);
        // apply coupon
        shoppingCart.applyCoupon(coupon3);
        //check result
        Assertions.assertEquals(490, shoppingCart.getCouponDiscount());
    }

    @Test
    void print() {
        // add products to cart
        shoppingCart.addItem(lamb, 4);
        shoppingCart.addItem(tv, 1);
        shoppingCart.addItem(soccerBall, 4);

        // check result
        Assertions.assertEquals(2380, shoppingCart.getTotalAmountAfterDiscounts());
        // call function
        shoppingCart.print();
        System.out.println("\n");

        // create campaign
        Campaign campaign = new Campaign(home, 80, 5, DiscountType.AMOUNT);
        // apply campaigns
        shoppingCart.applyDiscounts(campaign);
        //check result
        Assertions.assertEquals(80, shoppingCart.getCampaignDiscount());
        // call function
        shoppingCart.print();
        System.out.println("\n");

        // create coupon
        Coupon coupon = new Coupon(1000, 20, DiscountType.RATE);
        // apply coupon
        shoppingCart.applyCoupon(coupon);
        //check result
        Assertions.assertEquals(460, shoppingCart.getCouponDiscount());

        // call function
        shoppingCart.print();
    }
}