package fitness;

import java.time.LocalTime;

class MembershipValidate {

  public boolean canEnter(Membership membership, ZoneType zone) {

    if (!membership.isValid()) {
      System.out.println("Абонемент просрочен");
      return false;
    }

    LocalTime now = LocalTime.now();

    return switch (membership.getType()) {
      case ONE_TIME -> validateOneTime(now);
      case DAY_PASS -> validateDayPass(now, zone);
      case FULL -> validateFull(now);
    };
  }

  private boolean validateOneTime(LocalTime now) {
    return !now.isBefore(LocalTime.of(8, 0)) && !now.isAfter(LocalTime.of(21, 59));
  }

  private boolean validateDayPass(LocalTime now, ZoneType zone) {
    if (zone == ZoneType.POOL) {
      System.out.println("Дневной абонемент не дает доступ к бассейну");
      return false;
    }
    return !now.isBefore(LocalTime.of(8, 0)) && !now.isAfter(LocalTime.of(15, 59));
  }

  private boolean validateFull(LocalTime now) {
    return !now.isBefore(LocalTime.of(8, 0)) && !now.isAfter(LocalTime.of(21, 59));
  }
}
