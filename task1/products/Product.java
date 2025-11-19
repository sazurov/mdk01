package products;

class Product {
  String name;
  double protein, fats, carbs, kk;

  public Product() {
    this.name = "Empty";
    this.protein = 0;
    this.fats = 0;
    this.carbs = 0;
    this.kk = 0;
  }

  public Product(String name) {
    validateByName(name);
    this.name = name;
    this.protein = 0;
    this.fats = 0;
    this.carbs = 0;
    this.kk = 0;
  }

  public Product(String name, double kk) {
    validateByName(name);
    validateNutrients(0, 0, 0, kk);
    this.name = name;
    this.protein = 0;
    this.fats = 0;
    this.carbs = 0;
    this.kk = kk;
  }

  public Product(String name, double protein, double fats, double carbs, double kk) {
    validateByName(name);
    validateNutrients(protein, fats, carbs, kk);
    this.name = name;
    this.protein = protein;
    this.fats = fats;
    this.carbs = carbs;
    this.kk = kk;
  }

  private void validateByName(String name) {
    if (name.trim().isEmpty() || name == null) {
      throw new IllegalArgumentException("Name is empty!");
    }
  }

  private void validateNutrients(double protein, double fats, double carbs, double kk) {
    if (protein < 0 || fats < 0 || carbs < 0 || kk < 0) {
      throw new IllegalArgumentException("Nutrients cant be negative!");
    }
  }

  @Override
  public String toString() {
    return String.format("%s (p:%.1f f:%.1f c:%.1f k:%.0f)", name, protein, fats, carbs, kk);
  }
}
