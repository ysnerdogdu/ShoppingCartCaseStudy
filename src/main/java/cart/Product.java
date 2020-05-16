package cart;

public class Product {
    // properties
    private String title;
    private float price;
    private Category category;

    /**
     * Constructor
     * @param title product title
     * @param price unit price
     * @param category cart.Category of product
     */
    public Product(String title, float price, Category category) {
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Float.compare(product.price, price) != 0) return false;
        if (!title.equalsIgnoreCase(product.title)) return false;
        return category != null ? category.equals(product.category) : product.category == null;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
