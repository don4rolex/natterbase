package com.andrew.natterbase.dto;

import java.time.LocalDate;

/**
 * @author andrew
 */
public class SignUpDto {

  private String firstName;
  private String lastName;
  private LocalDate dateOfBirth;
  private String email;
  private String username;
  private String password;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SignUpDto dto = (SignUpDto) o;

    if (!firstName.equals(dto.firstName)) return false;
    if (!lastName.equals(dto.lastName)) return false;
    if (!dateOfBirth.equals(dto.dateOfBirth)) return false;
    if (!email.equals(dto.email)) return false;
    if (!username.equals(dto.username)) return false;
    return password.equals(dto.password);
  }

  @Override
  public int hashCode() {
    int result = firstName.hashCode();
    result = 31 * result + lastName.hashCode();
    result = 31 * result + dateOfBirth.hashCode();
    result = 31 * result + email.hashCode();
    result = 31 * result + username.hashCode();
    result = 31 * result + password.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SignUpDto{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", dateOfBirth=" + dateOfBirth +
        ", email='" + email + '\'' +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
