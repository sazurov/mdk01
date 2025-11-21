package t2;

import java.util.*;
import java.util.regex.Pattern;

public class Application {
  private static final Set<String> emails = new HashSet<>();
  private static final String EMAIL_REGEX = "^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
  private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    System.out.println("=== Email Manager ===\n");

    while (true) {
      showMenu();
      int choice = gChoice();

      switch (choice) {
        case 1:
          addEmailM();
          break;
        case 2:
          lEmails();
          break;
        case 3:
          scanner.close();
          return;
        default:
          System.out.println("Неверный выбор, попробуйте снова\n");
      }
    }
  }

  private static void showMenu() {
    System.out.println("=============================");
    System.out.println("1. Добавить email");
    System.out.println("2. Показать все email адреса");
    System.out.println("3. Выход");
    System.out.println("=============================");
  }

  private static int gChoice() {
    System.out.print("Выберите опцию (1-3): ");
    try {
      int choice = Integer.parseInt(scanner.nextLine().trim());
      System.out.println();
      return choice;
    } catch (NumberFormatException e) {
      System.out.println("Введите число\n");
      return -1;
    }
  }

  private static void addEmailM() {
    System.out.print("Введите email адрес: ");
    String email = scanner.nextLine().trim();

    if (email.isEmpty()) {
      System.out.println("Ошибка: email не может быть пустым\n");
      return;
    }

    addE(email);
    System.out.println();
  }

  private static void addE(String email) {
    if (!isValidEmail(email)) {
      System.out.println("Ошибка: '" + email + "' - некорректный формат email");
      return;
    }

    if (emails.contains(email)) {
      System.out.println("Email '" + email + "' уже существует в списке");
      return;
    }

    emails.add(email);
    System.out.println("Email '" + email + "' успешно добавлен");
  }

  private static void lEmails() {
    if (emails.isEmpty()) {
      System.out.println("Список пуст\n");
      return;
    }

    System.out.println("Список email адресов:");
    List<String> sortedEmails = new ArrayList<>(emails);
    Collections.sort(sortedEmails);

    int index = 1;
    for (String email : sortedEmails) {
      System.out.println("  " + index + ". " + email);
      index++;
    }
    System.out.println();
  }

  private static boolean isValidEmail(String email) {
    return email != null && !email.isEmpty() && EMAIL_PATTERN.matcher(email).matches();
  }
}
