package fitness;

import java.util.List;

public class FitnessClub {

  private final MembershipValidate validator = new MembershipValidate();
  private final ZoneManager zoneManager;

  public FitnessClub(List<Zone> zones) {
    this.zoneManager = new ZoneManager(zones);
  }

  public void register(Membership membership, ZoneType zoneType) {

    Zone zone = zoneManager.getZone(zoneType);

    if (!validator.canEnter(membership, zoneType)) {
      return;
    }

    if (!zone.isFree()) {
      System.out.println("В зоне " + zoneType + " нет свободных мест.");
      return;
    }

    if (zoneManager.isRegdAnotherZone(membership, zoneType)) {
      System.out.println("Абонемент уже используется в другой зоне.");
      return;
    }

    zone.addVisitor(membership);
    System.out.println(
        "Посетитель "
            + membership.getPerson().getFullName()
            + " успешно зарегистрирован в зоне "
            + zoneType);
  }
}
