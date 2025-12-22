import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

  private final PriceService priceService;

  public OrderService(PriceService priceService) {
    this.priceService = priceService;
  }

  public class Order {
    private final RestaurantBranch restaurant;
    private final List<MenuItem> items = new ArrayList<>();
    private final LocalDateTime createdAt;

    public Order(RestaurantBranch restaurant, LocalDateTime createdAt) {
      this.restaurant = restaurant;
      this.createdAt = createdAt;
    }

    public void addItem(MenuItem item) {
      items.add(item);
    }

    public List<MenuItem> getItems() {
      return items;
    }

    public RestaurantBranch getRestaurant() {
      return restaurant;
    }

    public LocalDateTime getCreatedAt() {
      return createdAt;
    }

    public double getTotal() {
      return priceService.calculateTotal(items, restaurant, createdAt);
    }
  }
}
