package bank;

import java.security.SecureRandom;

public class Account {
  private String name, accNums;
  private double balance;
  private ServiceType serviceType;
  private boolean isWelcomeBonusUsed;

  private static final SecureRandom rand = new SecureRandom();

  public Account(String name, ServiceType serviceType) {
    if (name == null || name.trim().isEmpty())
      throw new IllegalArgumentException("Имя не может быть пустым");
    if (serviceType == null) throw new IllegalArgumentException("Тип сервиса не может быть null");

    this.name = name.trim();
    this.accNums = genAccNum();
    this.balance = 0;
    this.serviceType = serviceType;
    this.isWelcomeBonusUsed = false;
  }

  private String genAccNum() {
    StringBuilder sb = new StringBuilder(20);
    for (int i = 0; i < 20; i++) sb.append(rand.nextInt(10));
    return sb.toString();
  }

  public String getName() {
    return name;
  }

  public String getAccNums() {
    return accNums;
  }

  public double getBalance() {
    return balance;
  }

  public ServiceType getServiceType() {
    return serviceType;
  }

  public boolean isWelcomeBonusUsed() {
    return isWelcomeBonusUsed;
  }

  void addBalance(double amount) {
    balance += amount;
  }

  void subtractBalance(double amount) {
    balance -= amount;
  }

  void setWelcomeBonusUsed() {
    isWelcomeBonusUsed = true;
  }

  public void checkBalance() {
    System.out.printf("Баланс счёта %s: %.2f руб.%n", accNums, balance);
  }

  @Override
  public String toString() {
    return String.format(
        "имя: %s | счёт: %s | баланс: %.2f руб. | Тип услуги: %s",
        name, accNums, balance, serviceType);
  }
}
