package cart;

public class Category {
    // properties
    private String title;
    private Category parentCategory;

    /**
     * Constructor
     * @param title sring title
     */
    public Category(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (!title.equalsIgnoreCase(category.title)) return false;
        return parentCategory != null ? parentCategory.equals(category.parentCategory) : category.parentCategory == null;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (parentCategory != null ? parentCategory.hashCode() : 0);
        return result;
    }
}
