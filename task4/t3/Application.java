package t3;

import java.util.*;

public class Application {
  private static final Map<String, String> nameToPhone = new HashMap<>();
  private static final Map<String, String> phoneToName = new HashMap<>();
  private static final Scanner scanner = new Scanner(System.in);
  private static final String PHONE_REGEX = "^\\d{10,15}$";

  public static void main(String[] args) {

    while (true) {
      showMenu();
      int choice = gChoice();

      switch (choice) {
        case 1:
          addM();
          break;
        case 2:
          lContacts();
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
    System.out.println("════════════════════════════");
    System.out.println("1. Добавить или найти контакт");
    System.out.println("2. Показать все контакты");
    System.out.println("3. Выход");
    System.out.println("════════════════════════════");
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

  private static void addM() {
    System.out.print("Введите имя или номер телефона: ");
    String input = scanner.nextLine().trim();

    if (input.isEmpty()) {
      System.out.println("Ошибка: поле не может быть пустым\n");
      return;
    }

    search(input);
    System.out.println();
  }

  private static void search(String input) {
    if (isPhone(input)) {
      srchByPhone(input);
    } else {
      srchByName(input);
    }
  }

  private static void srchByName(String name) {
    if (nameToPhone.containsKey(name)) {
      String phone = nameToPhone.get(name);
      System.out.println("Найден контакт:");
      System.out.println("  Имя: " + name);
      System.out.println("  Телефон: " + phone);
      return;
    }

    System.out.print("Введите номер телефона для " + name + ": ");
    String phone = scanner.nextLine().trim();

    if (!isPhone(phone)) {
      System.out.println("Ошибка: некорректный формат номера (10-15 цифр)");
      return;
    }

    if (phoneToName.containsKey(phone)) {
      System.out.println(
          "Ошибка: номер " + phone + " уже существует для " + phoneToName.get(phone));
      return;
    }

    nameToPhone.put(name, phone);
    phoneToName.put(phone, name);
    System.out.println("Контакт добавлен: " + name + " - " + phone);
  }

  private static void srchByPhone(String phone) {
    if (phoneToName.containsKey(phone)) {
      String name = phoneToName.get(phone);
      System.out.println("Найден контакт:");
      System.out.println("  Имя: " + name);
      System.out.println("  Телефон: " + phone);
      return;
    }

    System.out.print("Введите имя для номера " + phone + ": ");
    String name = scanner.nextLine().trim();

    if (name.isEmpty()) {
      System.out.println("Ошибка: имя не может быть пустым");
      return;
    }

    if (nameToPhone.containsKey(name)) {
      System.out.println("Ошибка: имя " + name + " уже существует для " + nameToPhone.get(name));
      return;
    }

    nameToPhone.put(name, phone);
    phoneToName.put(phone, name);
    System.out.println("Контакт добавлен: " + name + " - " + phone);
  }

  private static void lContacts() {
    if (nameToPhone.isEmpty()) {
      System.out.println("Телефонная книга пуста\n");
      return;
    }

    System.out.println("Телефонная книга:");

    List<String> names = new ArrayList<>(nameToPhone.keySet());
    Collections.sort(names);

    int index = 1;
    for (String name : names) {
      String phone = nameToPhone.get(name);
      System.out.println("  " + index + ". " + name + " - " + phone);
      index++;
    }
    System.out.println();
  }

  private static boolean isPhone(String input) {
    return input.matches(PHONE_REGEX);
  }
}
