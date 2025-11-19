package products;

class Application {
  public static void main(String[] args) {
    Product p1 = new Product("Apple", 0.3, 0.2, 25, 52);
    Product p2 = new Product("Chicken", 26, 3.6, 0, 165);

    MyProducts menu = new MyProducts(30, 20, 50, 300);
    menu.addProduct(p1);
    menu.addProduct(p2);

    menu.printAllProductNames();
  }
}
