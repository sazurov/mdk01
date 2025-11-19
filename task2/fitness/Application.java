package fitness;

import java.time.LocalDate;

enum MembershipType {
  ONE_TIME,
  DAY_PASS,
  FULL
}

class Application {
  public static void main(String[] args) {
    FitnessClub fitnessClub = new FitnessClub();

    Person person1 = new Person("Алжон", "Бобиев", 1990);
    Membership membership1 =
        new Membership(
            LocalDate.now(), LocalDate.now().plusDays(1), person1, MembershipType.ONE_TIME);

    Person person2 = new Person("Аджик", "Фалиев", 1985);
    Membership membership2 =
        new Membership(
            LocalDate.now(), LocalDate.now().plusDays(30), person2, MembershipType.DAY_PASS);

    Person person3 = new Person("Барак", "Обама", 1995);
    Membership membership3 =
        new Membership(
            LocalDate.now(), LocalDate.now().plusDays(365), person3, MembershipType.FULL);

    Person person4 = new Person("Истекший", "Абонемент", 1992);
    Membership membership4 =
        new Membership(
            LocalDate.now().minusDays(10),
            LocalDate.now().minusDays(1),
            person4,
            MembershipType.FULL);

    System.out.println("=== Проверка входов в фитнес клуб ===\n");

    fitnessClub.checkEntrance(membership1, "тренажёрный зал");
    fitnessClub.checkEntrance(membership1, "бассейн");

    fitnessClub.checkEntrance(membership2, "тренажёрный зал");
    fitnessClub.checkEntrance(membership2, "бассейн"); // Должно быть запрещено

    fitnessClub.checkEntrance(membership3, "тренажёрный зал");
    fitnessClub.checkEntrance(membership3, "бассейн");
    fitnessClub.checkEntrance(membership3, "групповые занятия");

    fitnessClub.checkEntrance(membership4, "тренажёрный зал"); // Абонемент истёк

    // Вывод статистики
    fitnessClub.displayStatistics();
    fitnessClub.displayVisitors(MembershipType.ONE_TIME);
    fitnessClub.displayVisitors(MembershipType.DAY_PASS);
    fitnessClub.displayVisitors(MembershipType.FULL);
  }
}
