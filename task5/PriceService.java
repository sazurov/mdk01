import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class PriceService {

  private final double moscowMarkup = 0.10;
  private final double foodDiscount = 0.20;

  private final DishRepository dishRepository;
  private final DrinkRepository drinkRepository;

  public PriceService(DishRepository dishRepository, DrinkRepository drinkRepository) {
    this.dishRepository = dishRepository;
    this.drinkRepository = drinkRepository;
  }

  public double calculateItemPrice(
      MenuItem item, RestaurantBranch restaurant, LocalDateTime dateTime) {
    double price = item.getBasePrice();
    if (restaurant.isMoscow()) {
      price += price * moscowMarkup;
    }

    if (item.isFood() && isFoodDiscountTime(dateTime)) {
      price -= price * foodDiscount;
    }
    return round(price);
  }

  public double calculateTotal(
      List<MenuItem> items, RestaurantBranch restaurant, LocalDateTime dateTime) {
    return round(
        items.stream().mapToDouble(item -> calculateItemPrice(item, restaurant, dateTime)).sum());
  }

  public void changeDishPrice(int dishId, double newPrice) throws java.sql.SQLException {
    dishRepository.updatePrice(dishId, newPrice);
  }

  public void changeDrinkPrice(int drinkId, double newPrice) throws java.sql.SQLException {
    drinkRepository.updatePrice(drinkId, newPrice);
  }

  private boolean isFoodDiscountTime(LocalDateTime dateTime) {
    DayOfWeek day = dateTime.getDayOfWeek();
    int hour = dateTime.getHour();
    boolean weekday = day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    return weekday && hour >= 15 && hour < 18;
  }

  private double round(double value) {
    return Math.round(value * 100.0) / 100.0;
  }
}
