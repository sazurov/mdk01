public class Drink implements MenuItem {

    private final int id;
    private String name;
    private int calories;
    private boolean alcoholic;
    private MenuCategory category;
    private double basePrice;

    public Drink(int id,
                 String name,
                 int calories,
                 boolean alcoholic,
                 MenuCategory category,
                 double basePrice) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.alcoholic = alcoholic;
        this.category = category;
        this.basePrice = basePrice;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public boolean isAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        this.alcoholic = alcoholic;
    }

    public MenuCategory getCategory() {
        return category;
    }

    public void setCategory(MenuCategory category) {
        this.category = category;
    }

    @Override
    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public boolean isFood() {
        return false;
    }
}


