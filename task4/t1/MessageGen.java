package t1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MessageGen {
  private static final Random rand = new Random();
  private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public static List<Message> genMessages(int n) {
    List<Message> list = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      Priority p = Priority.values()[rand.nextInt(Priority.values().length)];
      int key = 1 + rand.nextInt(5);
      String text = randString(8);

      list.add(new Message(p, key, text));
    }
    return list;
  }

  public static String randString(int length) {
    StringBuilder sb = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      sb.append(CHARSET.charAt(rand.nextInt(CHARSET.length())));
    }

    return sb.toString();
  }
}
