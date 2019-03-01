package com.andrew.natterbase.dto;

/**
 * @author andrew
 */
public class CountryDto {

  private String name;
  private String continent;

  public CountryDto() {
  }

  public CountryDto(String name, String continent) {
    this.name = name;
    this.continent = continent;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CountryDto that = (CountryDto) o;

    if (!name.equals(that.name)) return false;
    return continent.equals(that.continent);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + continent.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "CountryDto{" +
        ", name='" + name + '\'' +
        ", continent='" + continent + '\'' +
        '}';
  }
}
