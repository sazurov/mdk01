package fitness;

import java.time.LocalDate;

class Membership {
  LocalDate registrationDate;
  LocalDate expirationDate;
  Person person;
  MembershipType type;

  public Membership(
      LocalDate registrationDate, LocalDate expirationDate, Person person, MembershipType type) {
    this.registrationDate = registrationDate;
    this.expirationDate = expirationDate;
    this.person = person;
    this.type = type;
  }

  public MembershipType getType() {
    return type;
  }

  public Person getPerson() {
    return person;
  }

  public boolean isValid() {
    return LocalDate.now().isBefore(expirationDate) || LocalDate.now().isEqual(expirationDate);
  }
}
