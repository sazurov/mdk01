public class Dish implements MenuItem {

  private final int id;
  private String name;
  private boolean vegetarian;
  private int calories;
  private DishType type;
  private MenuCategory category;
  private double basePrice;
  private boolean chefSpecial;

  public Dish(
      int id,
      String name,
      boolean vegetarian,
      int calories,
      DishType type,
      MenuCategory category,
      double basePrice,
      boolean chefSpecial) {
    this.id = id;
    this.name = name;
    this.vegetarian = vegetarian;
    this.calories = calories;
    this.type = type;
    this.category = category;
    this.basePrice = basePrice;
    this.chefSpecial = chefSpecial;
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

  public boolean isVegetarian() {
    return vegetarian;
  }

  public void setVegetarian(boolean vegetarian) {
    this.vegetarian = vegetarian;
  }

  public int getCalories() {
    return calories;
  }

  public void setCalories(int calories) {
    this.calories = calories;
  }

  public DishType getType() {
    return type;
  }

  public void setType(DishType type) {
    this.type = type;
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

  public boolean isChefSpecial() {
    return chefSpecial;
  }

  public void setChefSpecial(boolean chefSpecial) {
    this.chefSpecial = chefSpecial;
  }

  @Override
  public boolean isFood() {
    return true;
  }
}
