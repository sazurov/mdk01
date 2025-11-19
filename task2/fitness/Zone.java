package fitness;

import java.util.ArrayList;
import java.util.List;

enum ZoneType {
  POOL(20),
  GYM(50),
  GROUP(10);

  private final int capacity;

  ZoneType(int capacity) {
    this.capacity = capacity;
  }

  public int getCapacity() {
    return capacity;
  }
}

class Zone {
  private ZoneType type;
  List<Membership> visitors = new ArrayList<>();

  public Zone(ZoneType type) {
    this.type = type;
  }

  public ZoneType getZone() {
    return type;
  }

  public boolean isFree() {
    return visitors.size() < type.getCapacity();
  }

  public void addVisitor(Membership membership) {
    visitors.add(membership);
  }

  public List<Membership> getVisitors() {
    return visitors;
  }
}
