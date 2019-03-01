package com.andrew.natterbase.service;

import com.andrew.natterbase.dto.CountryDto;
import com.andrew.natterbase.model.Country;

import java.util.List;

/**
 * @author andrew
 */
public interface CountryService {

  Country addCountry(CountryDto dto);

  List<Country> getCountries();

  Country updateCountry(Long id, CountryDto dto);

  void deleteCountry(Long id);

}
