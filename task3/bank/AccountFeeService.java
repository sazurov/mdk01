package bank;

public class AccountFeeService {

  public static void deductMonthlyFee(Account acc) {
    double fee = acc.getServiceType().getMonthSub();

    if (fee <= 0) {
      System.out.printf("Для типа %s ежемесячная плата не требуется%n%n", acc.getServiceType());
      return;
    }

    if (acc.getBalance() < fee)
      throw new IllegalStateException("Недостаточно средств для списания ежемесячной платы");

    acc.subtractBalance(fee);

    if (acc.getBalance() < 0) {
      acc.addBalance(fee);
      throw new IllegalStateException("Списание платежа привело бы к отрицательному балансу");
    }

    System.out.printf("Списание ежемесячной платы: -%.2f руб.%n", fee);
    System.out.printf("Баланс: %.2f руб.%n%n", acc.getBalance());
  }
}
