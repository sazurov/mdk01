import java.sql.SQLException;

public class Main {
  public static void main(String[] args) {
    Database database = new Database("jdbc:sqlite:restaurant.db");
    try {
      database.initialize();

      RestaurantRepository restaurantRepository = new RestaurantRepository(database);
      DishRepository dishRepository = new DishRepository(database);
      DrinkRepository drinkRepository = new DrinkRepository(database);

      PriceService priceService = new PriceService(dishRepository, drinkRepository);
      MenuService menuService = new MenuService(dishRepository, drinkRepository);
      OrderService orderService = new OrderService(priceService);

      RestaurantApp app = new RestaurantApp(
          restaurantRepository,
          menuService,
          orderService,
          priceService);
      app.run();
    } catch (SQLException e) {
      System.err.println("Ошибка работы с базой данных: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
