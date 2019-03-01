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
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author andrew
 */
@Entity
@Table(
    name = "country",
    indexes = {
        @Index(name = "INDEX_country_name", columnList = "name"),
        @Index(name = "INDEX_country_created", columnList = "created"),
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_country_name", columnNames = "name")
    }
)
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c"),
    @NamedQuery(name = "Country.findByName", query = "SELECT c FROM Country c WHERE c.name = :name")
})
public class Country implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @NotNull(message = "Name cannot be null")
  @Column(name = "name", nullable = false)
  private String name;

  @NotNull(message = "Continent cannot be null")
  @Column(name = "continent", nullable = false)
  private String continent;

  @NotNull(message = "Created cannot be null")
  @Column(name = "created", nullable = false)
  private LocalDateTime created;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContinent() {
    return continent;
  }

  public void setContinent(String continent) {
    this.continent = continent;
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

    Country Country = (Country) o;

    return id.equals(Country.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "Country{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", continent='" + continent + '\'' +
        ", created=" + created +
        '}';
  }
}
