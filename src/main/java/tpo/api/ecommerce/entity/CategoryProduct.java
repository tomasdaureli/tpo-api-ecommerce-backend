package tpo.api.ecommerce.entity;

public enum CategoryProduct {

    FOOTWEAR("Calzado"),
    CLOTHES("Ropa"),
    ACCESORIES("Accesorios");

    private final String displayValue;

    CategoryProduct(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public String getKey() {
        return name();
    }

}
