import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DrinkRepository {

    private final Database database;

    public DrinkRepository(Database database) {
        this.database = database;
    }

    public List<Drink> findByRestaurant(int restaurantId) throws SQLException {
        String sql = """
                SELECT d.id,
                       d.name,
                       d.calories,
                       d.alcoholic,
                       d.base_price,
                       d.category
                FROM drinks d
                JOIN restaurant_drinks rd ON d.id = rd.drink_id
                WHERE rd.restaurant_id = ?
                ORDER BY d.category, d.name
                """;
        List<Drink> drinks = new ArrayList<>();
        try (Connection connection = database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, restaurantId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    drinks.add(mapRow(rs));
                }
            }
        }
        return drinks;
    }

    public void updatePrice(int drinkId, double newPrice) throws SQLException {
        String sql = "UPDATE drinks SET base_price = ? WHERE id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, newPrice);
            ps.setInt(2, drinkId);
            ps.executeUpdate();
        }
    }

    private Drink mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int calories = rs.getInt("calories");
        boolean alcoholic = rs.getInt("alcoholic") == 1;
        double basePrice = rs.getDouble("base_price");
        MenuCategory category = MenuCategory.valueOf(rs.getString("category"));
        return new Drink(id, name, calories, alcoholic, category, basePrice);
    }
}


