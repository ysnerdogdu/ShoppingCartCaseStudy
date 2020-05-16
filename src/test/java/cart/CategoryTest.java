package cart;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testEqualsWithDifferentTitle() {
        Category category1 = new Category("food");
        Category category2 = new Category("spor");
        // call function
        boolean equals = category1.equals(category2);
        // check result
        Assertions.assertFalse(equals);
    }

    @Test
    void testEqualsWithDifferentParentTitleButSameTitle() {
        Category category1 = new Category("electronic");
        Category parentCategory = new Category("home");
        category1.setParentCategory(parentCategory);

        Category category2 = new Category("electronic");
        Category parentCategory2 = new Category("office");
        category2.setParentCategory(parentCategory2);

        // call function
        boolean equals = category1.equals(category2);
        // check result
        Assertions.assertFalse(equals);
    }

    @Test
    void testEqualsWithSameParentTitleSameTitle() {
        Category category1 = new Category("electronic");
        Category parentCategory = new Category("HOME");
        category1.setParentCategory(parentCategory);

        Category category2 = new Category("electronic");
        Category parentCategory2 = new Category("home");
        category2.setParentCategory(parentCategory2);

        // call function
        boolean equals = category1.equals(category2);
        // check result
        Assertions.assertTrue(equals);
    }

}