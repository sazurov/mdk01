package t1;

import java.util.*;
import java.util.stream.Collectors;

class MessageService {

  public static List<Message> uniqOrder(List<Message> list) {
    Set<Message> seen = new HashSet<>();
    List<Message> res = new ArrayList<>();

    for (Message m : list) {
      if (seen.add(m)) {
        res.add(m);
      }
    }
    return res;
  }

  public static void deleteByPriority(List<Message> list, Priority priority) {
    System.out.println("До:");
    list.forEach(System.out::println);

    list.removeIf(m -> m.gPriority() == priority);

    System.out.println("После: ");
    list.forEach(System.out::println);
  }

  public static void stayWithPrio(List<Message> list, Priority priority) {
    System.out.println("До:");
    list.forEach(System.out::println);

    list.removeIf(m -> m.gPriority() != priority);

    System.out.println("После:");
    list.forEach(System.out::println);
  }

  public static Map<Priority, Long> countByPrio(List<Message> list) {
    return list.stream().collect(Collectors.groupingBy(Message::gPriority, Collectors.counting()));
  }

  public static Map<Integer, Long> countByKey(List<Message> list) {
    return list.stream().collect(Collectors.groupingBy(Message::gKey, Collectors.counting()));
  }

  public static long countUnique(List<Message> list) {
    return new HashSet<>(list).size();
  }
}
