package t1;

import java.util.Objects;

class Message {

  private Priority priority;
  private int key;
  private String text;

  public Message(Priority priority, int key, String text) {
    this.priority = priority;
    this.key = key;
    this.text = text;
  }

  public Priority gPriority() {
    return priority;
  }

  public int gKey() {
    return key;
  }

  public String gText() {
    return text;
  }

  @Override
  public int hashCode() {
    return Objects.hash(priority, key, text);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Message msg)) return false;
    return key == msg.key && priority == msg.priority && Objects.equals(text, msg.text);
  }
}
