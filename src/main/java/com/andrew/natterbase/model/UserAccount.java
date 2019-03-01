package com.andrew.natterbase.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author andrew
 */
@Entity
@Table(
    name = "user_account",
    indexes = {
        @Index(name = "INDEX_user_account_email", columnList = "email"),
        @Index(name = "INDEX_user_account_username", columnList = "username"),
        @Index(name = "INDEX_user_account_created", columnList = "created"),
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_user_account_email", columnNames = "email"),
        @UniqueConstraint(name = "UK_user_account_username", columnNames = "username"),
    }
)
@NamedQueries({
    @NamedQuery(name = "UserAccount.findAll", query = "SELECT u FROM UserAccount u"),
    @NamedQuery(name = "UserAccount.findByEmail", query = "SELECT u FROM UserAccount u WHERE u.email = :email"),
    @NamedQuery(name = "UserAccount.findByUsernameAndPassword",
        query = "SELECT u FROM UserAccount u WHERE u.username = :username AND u.password = :password")
})
public class UserAccount implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @NotNull(message = "First name cannot be null")
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @NotNull(message = "Last name cannot be null")
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @NotNull(message = "Date Of cannot be null")
  @Column(name = "dateOfBirth", nullable = false)
  private LocalDate dateOfBirth;

  @Email(message = "Invalid email")
  @NotNull(message = "Email cannot be null")
  @Column(name = "email", nullable = false)
  private String email;

  @NotNull(message = "Username cannot be null")
  @Column(name = "username", nullable = false)
  private String username;

//  @JsonIgnore
  @NotNull(message = "Password cannot be null")
  @Column(name = "password", nullable = false)
  private String password;

  @NotNull(message = "Created cannot be null")
  @Column(name = "created", nullable = false)
  private LocalDateTime created;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserAccount userAccount = (UserAccount) o;

    return id.equals(userAccount.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "UserAccount{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", dateOfBirth=" + dateOfBirth +
        ", email='" + email + '\'' +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", created=" + created +
        '}';
  }
}
