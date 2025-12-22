import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

public class RestaurantApp {

  private final RestaurantRepository restaurantRepository;
  private final MenuService menuService;
  private final OrderService orderService;
  private final PriceService priceService;

  private final Scanner scanner = new Scanner(System.in);

  public RestaurantApp(
      RestaurantRepository restaurantRepository,
      MenuService menuService,
      OrderService orderService,
      PriceService priceService) {
    this.restaurantRepository = restaurantRepository;
    this.menuService = menuService;
    this.orderService = orderService;
    this.priceService = priceService;
  }

  public void run() {
    System.out.println("Добро пожаловать в сеть ресторанов!");
    boolean exit = false;
    while (!exit) {
      try {
        System.out.println();
        System.out.println("1. Выбрать ресторан и сделать заказ");
        System.out.println("2. Изменить цены блюд/напитков");
        System.out.println("0. Выход");
        System.out.print("Ваш выбор: ");
        int choice = readInt();
        switch (choice) {
          case 1 -> handleOrderFlow();
          case 2 -> handlePriceChangeFlow();
          case 0 -> exit = true;
          default -> System.out.println("Неизвестный пункт меню.");
        }
      } catch (SQLException e) {
        System.err.println("Ошибка работы с базой данных: " + e.getMessage());
      }
    }
    System.out.println("До свидания!");
  }

  private void handleOrderFlow() throws SQLException {
    Optional<RestaurantBranch> restaurantOpt = chooseRestaurant();
    if (restaurantOpt.isEmpty()) {
      return;
    }
    RestaurantBranch restaurant = restaurantOpt.get();

    LocalDateTime now = LocalDateTime.now();
    List<MenuItem> menu = menuService.getMenuForRestaurant(restaurant, now);
    if (menu.isEmpty()) {
      System.out.println("Меню для текущего времени пусто.");
      return;
    }

    System.out.println();
    System.out.println("Меню для ресторана " + restaurant + " на " + now);
    printMenuWithPrices(menu, restaurant, now);

    OrderService.Order order = orderService.new Order(restaurant, now);
    boolean ordering = true;
    while (ordering) {
      System.out.print("Введите номер позиции для добавления в заказ (0 - завершить): ");
      int index = readInt();
      if (index == 0) {
        ordering = false;
      } else if (index < 0 || index > menu.size()) {
        System.out.println("Неверный номер позиции.");
      } else {
        MenuItem item = menu.get(index - 1);
        order.addItem(item);
        double price = priceService.calculateItemPrice(item, restaurant, now);
        System.out.println("Добавлено: " + item.getName() + " - " + price + " руб.");
      }
    }

    if (order.getItems().isEmpty()) {
      System.out.println("Заказ пуст.");
      return;
    }

    System.out.println();
    System.out.println("Ваш заказ:");
    IntStream.range(0, order.getItems().size())
        .forEach(
            i -> {
              MenuItem item = order.getItems().get(i);
              double price = priceService.calculateItemPrice(item, restaurant, now);
              System.out.println((i + 1) + ". " + item.getName() + " - " + price + " руб.");
            });
    double total = order.getTotal();
    System.out.println("Итого к оплате: " + total + " руб.");
  }

  private void handlePriceChangeFlow() throws SQLException {
    Optional<RestaurantBranch> restaurantOpt = chooseRestaurant();
    if (restaurantOpt.isEmpty()) {
      return;
    }
    RestaurantBranch restaurant = restaurantOpt.get();
    LocalDateTime now = LocalDateTime.now();
    List<MenuItem> menu = menuService.getMenuForRestaurant(restaurant, now);
    if (menu.isEmpty()) {
      System.out.println("Меню пусто, нечего изменять.");
      return;
    }
    System.out.println();
    System.out.println("Текущие позиции меню ресторана " + restaurant + ":");
    printMenuWithPrices(menu, restaurant, now);

    System.out.print("Введите номер позиции для изменения цены (0 - отмена): ");
    int index = readInt();
    if (index == 0) {
      return;
    }
    if (index < 0 || index > menu.size()) {
      System.out.println("Неверный номер.");
      return;
    }
    MenuItem item = menu.get(index - 1);
    System.out.print("Введите новую базовую цену для \"" + item.getName() + "\": ");
    double newPrice = readDouble();

    if (item instanceof Dish) {
      priceService.changeDishPrice(item.getId(), newPrice);
    } else if (item instanceof Drink) {
      priceService.changeDrinkPrice(item.getId(), newPrice);
    }
    System.out.println("Цена обновлена.");
  }

  private Optional<RestaurantBranch> chooseRestaurant() throws SQLException {
    List<RestaurantBranch> restaurants = restaurantRepository.findAll();
    if (restaurants.isEmpty()) {
      System.out.println("Нет доступных ресторанов.");
      return Optional.empty();
    }
    System.out.println();
    System.out.println("Выберите ресторан:");
    List<Integer> moscowIds = new ArrayList<>();
    List<Integer> spbIds = new ArrayList<>();
    IntStream.range(0, restaurants.size())
        .forEach(
            i -> {
              RestaurantBranch r = restaurants.get(i);
              System.out.println((i + 1) + ". " + r);
              if (r.isMoscow()) {
                moscowIds.add(r.getId());
              } else {
                spbIds.add(r.getId());
              }
            });

    System.out.print("Ваш выбор (0 - назад): ");
    int index = readInt();
    if (index == 0) {
      return Optional.empty();
    }
    if (index < 0 || index > restaurants.size()) {
      System.out.println("Неверный номер ресторана.");
      return Optional.empty();
    }
    return Optional.of(restaurants.get(index - 1));
  }

  private void printMenuWithPrices(
      List<MenuItem> menu, RestaurantBranch restaurant, LocalDateTime now) {
    IntStream.range(0, menu.size())
        .forEach(
            i -> {
              MenuItem item = menu.get(i);
              double price = priceService.calculateItemPrice(item, restaurant, now);
              System.out.println((i + 1) + ". " + item.getName() + " - " + price + " руб.");
            });
  }

  private int readInt() {
    while (true) {
      String line = scanner.nextLine();
      try {
        return Integer.parseInt(line.trim());
      } catch (NumberFormatException e) {
        System.out.print("Введите целое число: ");
      }
    }
  }

  private double readDouble() {
    while (true) {
      String line = scanner.nextLine();
      try {
        return Double.parseDouble(line.trim().replace(',', '.'));
      } catch (NumberFormatException e) {
        System.out.print("Введите число: ");
      }
    }
  }
}
