package fitness;

import java.util.Objects;

class Person {
  String firstName, lastName;
  int birthYear;

  public Person(String firstName, String lastName, int birthYear) {
    this.firstName = Objects.requireNonNull(firstName);
    this.lastName = Objects.requireNonNull(lastName);
    this.birthYear = birthYear;
  }

  public String getFullName() {
    return firstName + " " + lastName + " " + birthYear;
  }
}
