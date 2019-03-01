package com.andrew.natterbase.util;

import com.andrew.natterbase.dto.CountryDto;
import com.andrew.natterbase.dto.LoginDto;
import com.andrew.natterbase.dto.SignUpDto;
import com.andrew.natterbase.model.Country;
import com.andrew.natterbase.model.UserAccount;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author andrew
 */
public class Utility {

  private Utility() {

  }

  public static SignUpDto newSignUpDto() {
    final SignUpDto dto = new SignUpDto();
    dto.setFirstName("Andrew");
    dto.setLastName("Mbata");
    dto.setEmail("don4rolex@yahoo.com");
    dto.setUsername("don4rolex");
    dto.setPassword("123456");
    dto.setDateOfBirth(LocalDate.now());

    return dto;
  }

  public static LoginDto newLoginDto() {
    final LoginDto dto = new LoginDto();
    dto.setUsername("don4rolex");
    dto.setPassword("123456");

    return dto;
  }

  public static UserAccount newUserAccount() {
    final UserAccount account = new UserAccount();
    account.setId(1L);
    account.setFirstName("Andrew");
    account.setLastName("Mbata");
    account.setEmail("don4rolex@yahoo.com");
    account.setUsername("don4rolex");
    account.setPassword("123456");
    account.setDateOfBirth(LocalDate.now());
    account.setCreated(LocalDateTime.now());

    return account;
  }

  public static Country newCountry(CountryDto dto) {
    final Country country = new Country();
    country.setId(1L);
    country.setName(dto.getName());
    country.setContinent(dto.getContinent());
    country.setCreated(LocalDateTime.now());

    return country;
  }
}
