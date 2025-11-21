package t1;

import java.util.List;
import java.util.Map;

class Application {
  public static void main(String[] args) {
    List<Message> messages = MessageGen.genMessages(5);

    System.out.println("Исходный список:");
    messages.forEach(System.out::println);
    System.out.println("//////////");

    // 3
    List<Message> unique = MessageService.uniqOrder(messages);
    System.out.println("Уникальные в порядке появления:");
    unique.forEach(System.out::println);
    System.out.println("///////////");

    // 4
    MessageService.deleteByPriority(messages, Priority.HIGH);

    // 5
    MessageService.stayWithPrio(messages, Priority.MED);

    // 6
    Map<Priority, Long> byPriority = MessageService.countByPrio(messages);
    System.out.println("Подсчёт по приоритетам: " + byPriority);

    // 7
    Map<Integer, Long> byCode = MessageService.countByKey(messages);
    System.out.println("Подсчёт по кодам: " + byCode);

    // 8
    System.out.println("Количество уникальных сообщений: " + MessageService.countUnique(messages));
  }
}
