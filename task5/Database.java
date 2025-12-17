import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private final String url;

    public Database(String url) {
        this.url = url;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void initialize() throws SQLException {
        try {
            // Для sqlite-jdbc (нужно добавить соответствующий .jar в classpath при запуске)
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Внимание: драйвер org.sqlite.JDBC не найден. Убедитесь, что библиотека sqlite-jdbc добавлена в classpath.");
        }

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            createTables(connection);
            seedInitialData(connection);
            connection.commit();
        }
    }

    private void createTables(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            st.execute("""
                    CREATE TABLE IF NOT EXISTS restaurants (
                        id INTEGER PRIMARY KEY,
                        name TEXT NOT NULL,
                        city TEXT NOT NULL,
                        is_moscow INTEGER NOT NULL
                    );
                    """);

            st.execute("""
                    CREATE TABLE IF NOT EXISTS dishes (
                        id INTEGER PRIMARY KEY,
                        name TEXT NOT NULL,
                        vegetarian INTEGER NOT NULL,
                        calories INTEGER NOT NULL,
                        type TEXT NOT NULL,
                        base_price REAL NOT NULL,
                        category TEXT NOT NULL
                    );
                    """);

            st.execute("""
                    CREATE TABLE IF NOT EXISTS drinks (
                        id INTEGER PRIMARY KEY,
                        name TEXT NOT NULL,
                        calories INTEGER NOT NULL,
                        alcoholic INTEGER NOT NULL,
                        base_price REAL NOT NULL,
                        category TEXT NOT NULL
                    );
                    """);

            st.execute("""
                    CREATE TABLE IF NOT EXISTS restaurant_dishes (
                        restaurant_id INTEGER NOT NULL,
                        dish_id INTEGER NOT NULL,
                        chef_special INTEGER NOT NULL DEFAULT 0,
                        PRIMARY KEY (restaurant_id, dish_id),
                        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
                        FOREIGN KEY (dish_id) REFERENCES dishes(id)
                    );
                    """);

            st.execute("""
                    CREATE TABLE IF NOT EXISTS restaurant_drinks (
                        restaurant_id INTEGER NOT NULL,
                        drink_id INTEGER NOT NULL,
                        PRIMARY KEY (restaurant_id, drink_id),
                        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
                        FOREIGN KEY (drink_id) REFERENCES drinks(id)
                    );
                    """);
        }
    }

    private boolean isTableEmpty(Connection connection, String table) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM " + table);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return true;
    }

    private void seedInitialData(Connection connection) throws SQLException {
        if (isTableEmpty(connection, "restaurants")) {
            seedRestaurants(connection);
        }
        if (isTableEmpty(connection, "dishes")) {
            seedDishes(connection);
        }
        if (isTableEmpty(connection, "drinks")) {
            seedDrinks(connection);
        }
        if (isTableEmpty(connection, "restaurant_dishes")) {
            seedRestaurantDishes(connection);
        }
        if (isTableEmpty(connection, "restaurant_drinks")) {
            seedRestaurantDrinks(connection);
        }
    }

    private void seedRestaurants(Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO restaurants (id, name, city, is_moscow) VALUES (?, ?, ?, ?)")) {
            // 3 филиала в Москве
            insertRestaurant(ps, 1, "Москва - Центр", "Москва", true);
            insertRestaurant(ps, 2, "Москва - Север", "Москва", true);
            insertRestaurant(ps, 3, "Москва - Юг", "Москва", true);
            // 2 в Санкт-Петербурге
            insertRestaurant(ps, 4, "Питер - Невский", "Санкт-Петербург", false);
            insertRestaurant(ps, 5, "Питер - Васильевский", "Санкт-Петербург", false);
        }
    }

    private void insertRestaurant(PreparedStatement ps, int id, String name, String city, boolean isMoscow)
            throws SQLException {
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, city);
        ps.setInt(4, isMoscow ? 1 : 0);
        ps.executeUpdate();
    }

    private void seedDishes(Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO dishes (id, name, vegetarian, calories, type, base_price, category) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            // Общие блюда
            insertDish(ps, 1, "Борщ", false, 250, "MEAT", 250.0, "REGULAR");
            insertDish(ps, 2, "Салат овощной", true, 150, "OTHER", 180.0, "REGULAR");
            insertDish(ps, 3, "Стейк из лосося", false, 320, "FISH", 480.0, "REGULAR");
            insertDish(ps, 4, "Овсяная каша", true, 200, "OTHER", 200.0, "BREAKFAST");
            insertDish(ps, 5, "Яичница с беконом", false, 300, "MEAT", 260.0, "BREAKFAST");
            insertDish(ps, 6, "Бизнес-ланч: суп и салат", false, 400, "OTHER", 350.0, "BUSINESS_LUNCH");
            insertDish(ps, 7, "Бизнес-ланч: паста и кофе", false, 520, "OTHER", 400.0, "BUSINESS_LUNCH");

            // Блюда от шефа (уникальные для каждого ресторана)
            insertDish(ps, 8, "Фирменный стейк Центр", false, 550, "MEAT", 650.0, "REGULAR");
            insertDish(ps, 9, "Фирменная паста Север", false, 500, "OTHER", 620.0, "REGULAR");
            insertDish(ps, 10, "Фирменный плов Юг", false, 580, "MEAT", 590.0, "REGULAR");
            insertDish(ps, 11, "Фирменный бургер Невский", false, 600, "MEAT", 610.0, "REGULAR");
            insertDish(ps, 12, "Фирменная уха Васильевский", false, 450, "FISH", 570.0, "REGULAR");
        }
    }

    private void insertDish(PreparedStatement ps, int id, String name, boolean vegetarian, int calories,
                            String type, double basePrice, String category) throws SQLException {
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, vegetarian ? 1 : 0);
        ps.setInt(4, calories);
        ps.setString(5, type);
        ps.setDouble(6, basePrice);
        ps.setString(7, category);
        ps.executeUpdate();
    }

    private void seedDrinks(Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO drinks (id, name, calories, alcoholic, base_price, category) " +
                        "VALUES (?, ?, ?, ?, ?, ?)")) {
            insertDrink(ps, 1, "Американо", 5, false, 120.0, "BREAKFAST");
            insertDrink(ps, 2, "Капучино", 90, false, 150.0, "BREAKFAST");
            insertDrink(ps, 3, "Чай черный", 2, false, 80.0, "BREAKFAST");
            insertDrink(ps, 4, "Сок апельсиновый", 80, false, 130.0, "REGULAR");
            insertDrink(ps, 5, "Минеральная вода", 0, false, 70.0, "REGULAR");
            insertDrink(ps, 6, "Пиво светлое", 120, true, 200.0, "REGULAR");
            insertDrink(ps, 7, "Вино красное бокал", 150, true, 320.0, "REGULAR");
            insertDrink(ps, 8, "Домашний квас (ланч)", 70, false, 90.0, "BUSINESS_LUNCH");
        }
    }

    private void insertDrink(PreparedStatement ps, int id, String name, int calories,
                             boolean alcoholic, double basePrice, String category) throws SQLException {
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, calories);
        ps.setInt(4, alcoholic ? 1 : 0);
        ps.setDouble(5, basePrice);
        ps.setString(6, category);
        ps.executeUpdate();
    }

    private void seedRestaurantDishes(Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO restaurant_dishes (restaurant_id, dish_id, chef_special) VALUES (?, ?, ?)")) {
            // Общие блюда (1-7) есть во всех ресторанах
            for (int restaurantId = 1; restaurantId <= 5; restaurantId++) {
                for (int dishId = 1; dishId <= 7; dishId++) {
                    insertRestaurantDish(ps, restaurantId, dishId, false);
                }
            }

            // Блюда от шефа: по одному уникальному на ресторан
            insertRestaurantDish(ps, 1, 8, true);
            insertRestaurantDish(ps, 2, 9, true);
            insertRestaurantDish(ps, 3, 10, true);
            insertRestaurantDish(ps, 4, 11, true);
            insertRestaurantDish(ps, 5, 12, true);
        }
    }

    private void insertRestaurantDish(PreparedStatement ps, int restaurantId, int dishId, boolean chefSpecial)
            throws SQLException {
        ps.setInt(1, restaurantId);
        ps.setInt(2, dishId);
        ps.setInt(3, chefSpecial ? 1 : 0);
        ps.executeUpdate();
    }

    private void seedRestaurantDrinks(Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO restaurant_drinks (restaurant_id, drink_id) VALUES (?, ?)")) {
            for (int restaurantId = 1; restaurantId <= 5; restaurantId++) {
                for (int drinkId = 1; drinkId <= 8; drinkId++) {
                    insertRestaurantDrink(ps, restaurantId, drinkId);
                }
            }
        }
    }

    private void insertRestaurantDrink(PreparedStatement ps, int restaurantId, int drinkId) throws SQLException {
        ps.setInt(1, restaurantId);
        ps.setInt(2, drinkId);
        ps.executeUpdate();
    }
}


