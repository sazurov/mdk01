import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestaurantRepository {

  private final Database database;

  public RestaurantRepository(Database database) {
    this.database = database;
  }

  public List<RestaurantBranch> findAll() throws SQLException {
    List<RestaurantBranch> result = new ArrayList<>();
    String sql = "SELECT id, name, city, is_moscow FROM restaurants ORDER BY id";
    try (Connection connection = database.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        result.add(mapRow(rs));
      }
    }
    return result;
  }

  public Optional<RestaurantBranch> findById(int id) throws SQLException {
    String sql = "SELECT id, name, city, is_moscow FROM restaurants WHERE id = ?";
    try (Connection connection = database.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRow(rs));
        }
      }
    }
    return Optional.empty();
  }

  private RestaurantBranch mapRow(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");
    String name = rs.getString("name");
    String city = rs.getString("city");
    boolean isMoscow = rs.getInt("is_moscow") == 1;
    return new RestaurantBranch(id, name, city, isMoscow);
  }
}
