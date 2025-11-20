package bank;

public class TransactionService {

  private static final double MIN_BAL = 0;

  public static void replenish(Account acc, double amount) {
    if (amount <= 0) throw new IllegalArgumentException("Сумма не должна быть меньше или равна 0");

    acc.addBalance(amount);
    System.out.printf("Пополнение счёта: +%.2f руб.%n", amount);
    System.out.printf("Новый баланс: %.2f руб.%n%n", acc.getBalance());
  }

  public static void payService(Account acc, double amount) {
    if (amount <= 0) throw new IllegalArgumentException("Сумма оплаты должна быть больше 0");
    if (acc.getBalance() < amount) throw new IllegalStateException("Недостаточно средств");

    acc.subtractBalance(amount);

    if (acc.getBalance() < MIN_BAL) {
      acc.addBalance(amount);
      throw new IllegalStateException("Операция привела бы к отрицательному балансу");
    }

    double cashback = acc.getServiceType().getCashbackRate(amount) * amount;
    acc.addBalance(cashback);

    if (!acc.isWelcomeBonusUsed()) {
      acc.addBalance(1000);
      acc.setWelcomeBonusUsed();
      System.out.println("Приветственный бонус: +1000 руб.");
    }

    System.out.printf("Оплата услуги: %.2f руб.%n", amount);
    System.out.printf(
        "Кешбек (%.0f%%): +%.2f руб.%n",
        acc.getServiceType().getCashbackRate(amount) * 100, cashback);
    System.out.printf("Новый баланс: %.2f руб.%n%n", acc.getBalance());
  }

  public static void transfer(Account from, Account to, double amount) {
    if (amount <= 0) throw new IllegalArgumentException("Сумма перевода должна быть больше 0");
    if (to == null) throw new IllegalArgumentException("Счёт получателя не может быть null");
    if (from.getAccNums().equals(to.getAccNums()))
      throw new IllegalArgumentException("Нельзя переводить деньги на свой же счёт");
    if (from.getBalance() < amount) throw new IllegalStateException("Недостаточно средств");

    from.subtractBalance(amount);
    if (from.getBalance() < MIN_BAL) {
      from.addBalance(amount);
      throw new IllegalStateException("Операция привела бы к отрицательному балансу");
    }

    to.addBalance(amount);

    System.out.printf(
        "Перевод от %s на счёт %s: %.2f руб.%n", from.getAccNums(), to.getAccNums(), amount);
    System.out.printf("Баланс отправителя: %.2f руб.%n", from.getBalance());
    System.out.printf("Баланс получателя: %.2f руб.%n%n", to.getBalance());
  }
}
