import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MenuService {

  private final DishRepository dishRepository;
  private final DrinkRepository drinkRepository;

  public MenuService(DishRepository dishRepository, DrinkRepository drinkRepository) {
    this.dishRepository = dishRepository;
    this.drinkRepository = drinkRepository;
  }

  public List<MenuItem> getMenuForRestaurant(RestaurantBranch restaurant, LocalDateTime dateTime)
      throws SQLException {
    List<MenuItem> result = new ArrayList<>();
    List<Dish> dishes = dishRepository.findByRestaurant(restaurant.getId());
    List<Drink> drinks = drinkRepository.findByRestaurant(restaurant.getId());

    int hour = dateTime.getHour();

    boolean breakfastTime = hour >= 7 && hour < 11;
    boolean businessLunchTime = hour >= 12 && hour < 15;

    result.addAll(
        dishes.stream()
            .filter(dish -> isCategoryVisible(dish.getCategory(), breakfastTime, businessLunchTime))
            .collect(Collectors.toList()));

    result.addAll(
        drinks.stream()
            .filter(
                drink -> isCategoryVisible(drink.getCategory(), breakfastTime, businessLunchTime))
            .collect(Collectors.toList()));

    result.sort(Comparator.comparing(MenuItem::getName));
    return result;
  }

  private boolean isCategoryVisible(
      MenuCategory category, boolean breakfastTime, boolean businessLunchTime) {
    if (category == MenuCategory.BREAKFAST) {
      return breakfastTime;
    }
    if (category == MenuCategory.BUSINESS_LUNCH) {
      return businessLunchTime;
    }
    return true;
  }
}
