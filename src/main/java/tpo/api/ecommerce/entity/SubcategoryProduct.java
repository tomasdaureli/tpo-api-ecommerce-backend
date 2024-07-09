package tpo.api.ecommerce.entity;

public enum SubcategoryProduct {

    FASHION("Moda"),
    SPORTS("Deportes"),
    RUNNING("Running"),
    FOOTBALL("Fútbol"),
    SANDALS("Ojotas"),
    SHIRTS("Camisas"),
    SOCKS("Medias"),
    CAPS("Gorras"),
    BAGS("Bolsos"),
    HOODIES("Sudaderas"),
    SHORTS("Pantalones cortos"),
    TROUSERS("Pantalones"),
    BALLS("Pelotas");

    private final String displayValue;

    SubcategoryProduct(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public String getKey() {
        return name();
    }

}
