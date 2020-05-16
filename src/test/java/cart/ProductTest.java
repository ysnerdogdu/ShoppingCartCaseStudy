package cart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testEqualsWithDifferentTitleSameCategory() {
        Category category = new Category("food");
        Product product1 = new Product("Lamba", 100, category);
        Product product2= new Product("Koltuk", 100, category);

        // call function
        boolean equals = product1.equals(product2);
        // check result
        Assertions.assertFalse(equals);
    }

    @Test
    void testEqualsWithSameTitleButDifferentCategory() {
        Category category1 = new Category("electronic");
        Product product1= new Product("pil", 100, category1);

        Category category2 = new Category("office");
        Product product2 = new Product("pil", 100, category2);

        // call function
        boolean equals = category1.equals(category2);
        // check result
        Assertions.assertFalse(equals);
    }
    @Test
    void testEqualsWithSameTitleAndSameCategory() {
        Category category1 = new Category("food");
        Product product1= new Product("kEBab", 100, category1);

        Category category2 = new Category("food");
        Product product2 = new Product("KebaB", 100, category2);

        // call function
        boolean equals = category1.equals(category2);
        // check result
        Assertions.assertTrue(equals);
    }
}