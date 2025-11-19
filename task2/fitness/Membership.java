package fitness;

import java.time.LocalDate;
import java.util.Objects;

enum MembershipType {
  ONE_TIME,
  DAY_PASS,
  FULL
}

class Membership {
  LocalDate regDate, expDate;
  Person person;
  MembershipType type;

  public Membership(LocalDate regDate, LocalDate expDate, Person person, MembershipType type) {
    this.regDate = Objects.requireNonNull(regDate);
    this.expDate = Objects.requireNonNull(expDate);
    this.person = Objects.requireNonNull(person);
    this.type = Objects.requireNonNull(type);
  }

  public MembershipType getType() {
    return type;
  }

  public Person getPerson() {
    return person;
  }

  public boolean isValid() {
    return LocalDate.now().isBefore(expDate);
  }

  @Override
  public String toString() {
    return person.toString() + " | " + type;
  }
}
