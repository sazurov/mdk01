package bank;

import java.util.HashMap;
import java.util.Map;

public class Bank {
  private final Map<String, Account> accounts;

  public Bank() {
    this.accounts = new HashMap<>();
  }

  public Account openAccount(String fullName, ServiceType serviceType) {
    Account account = new Account(fullName, serviceType);

    if (accounts.containsKey(account.getAccNums())) {
      throw new IllegalStateException("Счёт с таким номером уже существует (попробуйте ещё раз)");
    }

    accounts.put(account.getAccNums(), account);
    System.out.printf("Счёт успешно открыт для %s (%s)%n", fullName, serviceType);
    System.out.printf("Номер счёта: %s%n%n", account.getAccNums());

    return account;
  }

  public Account getAccount(String accountNumber) {
    if (accountNumber == null || accountNumber.trim().isEmpty()) {
      throw new IllegalArgumentException("Номер счёта не может быть пустым");
    }

    Account account = accounts.get(accountNumber);
    if (account == null) {
      throw new IllegalArgumentException("Счёт не найден: " + accountNumber);
    }

    return account;
  }

  public void showAllAccounts() {
    System.out.println("=== Список всех счётов ===");
    if (accounts.isEmpty()) {
      System.out.println("Счётов не найдено");
    } else {
      accounts.values().forEach(System.out::println);
    }
    System.out.println();
  }

  public void deductMonthlyFeesForAll() {
    accounts.values().forEach(AccountFeeService::deductMonthlyFee);
  }
}
