public class RestaurantBranch {

    private final int id;
    private final String name;
    private final String city;
    private final boolean moscow;

    public RestaurantBranch(int id, String name, String city, boolean moscow) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.moscow = moscow;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public boolean isMoscow() {
        return moscow;
    }

    @Override
    public String toString() {
        return name + " (" + city + ")";
    }
}


