package fitness;

import java.time.LocalTime;
import java.util.ArrayList;

class FitnessClub {
  final int GYM_CAPACITY = 50;
  final int POOL_CAPACITY = 20;
  final int GROUP_CLASS_CAPACITY = 10;

  ArrayList<Membership> gymMembers = new ArrayList<>();
  ArrayList<Membership> poolMembers = new ArrayList<>();
  ArrayList<Membership> groupClassMembers = new ArrayList<>();

  public void checkEntrance(Membership membership, String zone) {
    if (!membership.isValid()) {
      System.out.println(membership.person + ": Срок действия абонемента истёк!");
      return;
    }

    if (!isZoneAllowedForMembership(membership.getType(), zone)) {
      System.out.println(
          membership.person
              + ": Абонемент типа "
              + membership.getType()
              + " не действителен для зоны \""
              + zone
              + "\"");
      return;
    }

    if (!isTimeAllowedForMembership(membership.getType())) {
      System.out.println(membership.person + ": Время входа не соответствует типу абонемента!");
      return;
    }

    if (isRegisteredInOtherZone(membership, zone)) {
      System.out.println(membership.person + ": Уже зарегистрирован в другой зоне!");
      return;
    }

    if (!registerInZone(membership, zone)) {
      System.out.println(membership.person + ": В зоне \"" + zone + "\" нет свободных мест!");
      return;
    }

    System.out.println(membership.person + ": Успешно зарегистрирован в зоне \"" + zone + "\"");
  }

  private boolean isZoneAllowedForMembership(MembershipType type, String zone) {
    switch (type) {
      case ONE_TIME:
        return zone.equals("бассейн") || zone.equals("тренажёрный зал");
      case DAY_PASS:
        return zone.equals("тренажёрный зал") || zone.equals("групповые занятия");
      case FULL:
        return zone.equals("бассейн")
            || zone.equals("тренажёрный зал")
            || zone.equals("групповые занятия");
      default:
        return false;
    }
  }

  private boolean isTimeAllowedForMembership(MembershipType type) {
    LocalTime currentTime = LocalTime.now();

    switch (type) {
      case ONE_TIME:
        return currentTime.isAfter(LocalTime.of(7, 59))
            && currentTime.isBefore(LocalTime.of(22, 1));
      case DAY_PASS:
        return currentTime.isAfter(LocalTime.of(7, 59))
            && currentTime.isBefore(LocalTime.of(16, 1));
      case FULL:
        return currentTime.isAfter(LocalTime.of(7, 59))
            && currentTime.isBefore(LocalTime.of(22, 1));
      default:
        return false;
    }
  }

  private boolean isRegisteredInOtherZone(Membership membership, String currentZone) {
    switch (currentZone) {
      case "тренажёрный зал":
        return poolMembers.contains(membership) || groupClassMembers.contains(membership);
      case "бассейн":
        return gymMembers.contains(membership) || groupClassMembers.contains(membership);
      case "групповые занятия":
        return gymMembers.contains(membership) || poolMembers.contains(membership);
      default:
        return false;
    }
  }

  private boolean registerInZone(Membership membership, String zone) {
    switch (zone) {
      case "тренажёрный зал":
        if (gymMembers.size() >= GYM_CAPACITY) return false;
        gymMembers.add(membership);
        return true;
      case "бассейн":
        if (poolMembers.size() >= POOL_CAPACITY) return false;
        poolMembers.add(membership);
        return true;
      case "групповые занятия":
        if (groupClassMembers.size() >= GROUP_CLASS_CAPACITY) return false;
        groupClassMembers.add(membership);
        return true;
      default:
        return false;
    }
  }

  public void displayVisitors(MembershipType type) {
    System.out.println("\n=== Посетители с абонементом " + type + " ===");
    System.out.println("Тренажёрный зал:");
    displayMemberships(gymMembers, type);
    System.out.println("Бассейн:");
    displayMemberships(poolMembers, type);
    System.out.println("Групповые занятия:");
    displayMemberships(groupClassMembers, type);
  }

  private void displayMemberships(ArrayList<Membership> memberships, MembershipType type) {
    boolean found = false;
    for (Membership membership : memberships) {
      if (membership.getType() == type) {
        System.out.println("  - " + membership.person);
        found = true;
      }
    }
    if (!found) {
      System.out.println("  (нет посетителей)");
    }
  }

  public void displayStatistics() {
    System.out.println("\n=== Статистика по зонам ===");
    System.out.println("Тренажёрный зал: " + gymMembers.size() + "/" + GYM_CAPACITY);
    System.out.println("Бассейн: " + poolMembers.size() + "/" + POOL_CAPACITY);
    System.out.println(
        "Групповые занятия: " + groupClassMembers.size() + "/" + GROUP_CLASS_CAPACITY);
  }
}
