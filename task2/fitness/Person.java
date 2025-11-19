package fitness;

class Person {
  String firstName;
  String lastName;
  int birthYear;

  public Person(String firstName, String lastName, int birthYear) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthYear = birthYear;
  }

  @Override
  public String toString() {
    return firstName + " " + lastName;
  }
}
