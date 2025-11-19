package products;

import java.util.ArrayList;

class MyProducts {
  private ArrayList<Product> products;
  double maxProtein, maxFats, maxCarbs, maxCal;

  private double[] maxValues;
  private String[] nutrientsNames;

  public MyProducts(double maxProtein, double maxFats, double maxCarbs, double maxCal) {
    this.products = new ArrayList<>();
    this.maxProtein = maxProtein;
    this.maxFats = maxFats;
    this.maxCarbs = maxCarbs;
    this.maxCal = maxCal;

    this.maxValues = new double[] {maxProtein, maxFats, maxCarbs, maxCal};
    this.nutrientsNames = new String[] {"Protein", "Fats", "Carbs", "Calories"};
  }

  private void validateNutrients(Product product) {
    double[] nutrients = {product.protein, product.fats, product.carbs, product.kk};

    for (int i = 0; i < nutrients.length; i++) {
      if (nutrients[i] > maxValues[i]) {
        throw new IllegalArgumentException(
            nutrientsNames[i] + "More than max possible value! " + "Max value is: " + maxValues[i]);
      }
    }
  }

  public void addProduct(Product product) {
    if (product == null) {
      throw new IllegalArgumentException("Product cant be null");
    }
    validateNutrients(product);
    products.add(product);
  }

  public void removeProductByIndex(int index) {
    if (index < 0 || index >= products.size()) {
      throw new IndexOutOfBoundsException("Index " + index + " doesnt exists");
    }
    products.remove(index);
  }

  public void removeProductByName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cant be empty!");
    }

    for (int i = 0; i < products.size(); i++) {
      if (products.get(i).name.equals(name)) {
        products.remove(i);
        return;
      }
    }
    throw new IllegalArgumentException("Product " + name + " not found!");
  }

  public void printAllProductNames() {
    if (products.isEmpty()) {
      System.out.println("Product list is empty!");
      return;
    }

    for (int i = 0; i < products.size(); i++) {
      System.out.println((i + 1) + ": " + products.get(i).name);
    }
  }
}
