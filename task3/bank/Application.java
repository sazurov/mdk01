package bank;

public class Application {
  public static void main(String[] args) {
    Bank bank = new Bank();

    for (int i = 1; i <= 5; i++) {
      String name = "BaseClient" + i;
      bank.openAccount(name, ServiceType.BASE);
    }

    for (int i = 1; i <= 5; i++) {
      String name = "PremiumClient" + i;
      bank.openAccount(name, ServiceType.PREMIUM);
    }

    for (int i = 1; i <= 5; i++) {
      String name = "VIPClient" + i;
      bank.openAccount(name, ServiceType.VIP);
    }

    bank.showAllAccounts();
  }
}
