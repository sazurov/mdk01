package fitness;

import java.time.LocalDate;
import java.util.List;

public class Application {
  public static void main(String[] args) {

    List<Zone> zones =
        List.of(new Zone(ZoneType.GYM), new Zone(ZoneType.POOL), new Zone(ZoneType.GROUP));

    FitnessClub club = new FitnessClub(zones);

    Person p1 = new Person("Алжон", "Бобиев", 1990);
    Membership m1 =
        new Membership(LocalDate.now(), LocalDate.now().plusDays(1), p1, MembershipType.ONE_TIME);

    club.register(m1, ZoneType.GYM);
  }
}
