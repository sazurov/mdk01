package bank;

enum ServiceType {
  BASE(100),
  PREMIUM(0),
  VIP(0);
  private final double monthSub;

  ServiceType(double monthSub) {
    this.monthSub = monthSub;
  }

  public double getMonthSub() {
    return monthSub;
  }

  public double getCashbackRate(double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Сумма не может быть отрицательной");
    }
    return switch (this) {
      case BASE -> amount >= 10000 ? 0.01 : 0;
      case PREMIUM -> amount >= 10000 ? 0.05 : 0;
      case VIP -> {
        if (amount < 10000) yield 0.01;
        if (amount >= 10000 && amount < 100000) yield 0.05;
        yield 0.10;
      }
    };
  }
}
