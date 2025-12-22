import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishRepository {

  private final Database database;

  public DishRepository(Database database) {
    this.database = database;
  }

  public List<Dish> findByRestaurant(int restaurantId) throws SQLException {
    String sql =
        """
        SELECT d.id,
               d.name,
               d.vegetarian,
               d.calories,
               d.type,
               d.base_price,
               d.category,
               rd.chef_special
        FROM dishes d
        JOIN restaurant_dishes rd ON d.id = rd.dish_id
        WHERE rd.restaurant_id = ?
        ORDER BY d.category, d.name
        """;
    List<Dish> dishes = new ArrayList<>();
    try (Connection connection = database.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, restaurantId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          dishes.add(mapRow(rs));
        }
      }
    }
    return dishes;
  }

  public void updatePrice(int dishId, double newPrice) throws SQLException {
    String sql = "UPDATE dishes SET base_price = ? WHERE id = ?";
    try (Connection connection = database.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setDouble(1, newPrice);
      ps.setInt(2, dishId);
      ps.executeUpdate();
    }
  }

  private Dish mapRow(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    String name = rs.getString("name");
    boolean vegetarian = rs.getInt("vegetarian") == 1;
    int calories = rs.getInt("calories");
    DishType type = DishType.valueOf(rs.getString("type"));
    double basePrice = rs.getDouble("base_price");
    MenuCategory category = MenuCategory.valueOf(rs.getString("category"));
    boolean chefSpecial = rs.getInt("chef_special") == 1;
    return new Dish(id, name, vegetarian, calories, type, category, basePrice, chefSpecial);
  }
}
