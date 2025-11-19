package fitness;

import java.util.List;

class ZoneManager {
  private List<Zone> zones;

  public ZoneManager(List<Zone> zones) {
    this.zones = zones;
  }

  public Zone getZone(ZoneType type) {
    return zones.stream().filter(z -> z.getZone() == type).findFirst().orElseThrow();
  }

  public boolean isRegdAnotherZone(Membership membership, ZoneType currentZone) {
    for (Zone z : zones) {
      if (z.getZone() != currentZone && z.getVisitors().contains(membership)) {
        return true;
      }
    }
    return false;
  }
}
