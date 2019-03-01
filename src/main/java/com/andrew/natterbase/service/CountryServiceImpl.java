package com.andrew.natterbase.service;

import com.andrew.natterbase.dto.CountryDto;
import com.andrew.natterbase.exception.CountryNotFoundException;
import com.andrew.natterbase.model.Country;
import com.andrew.natterbase.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author andrew
 */
@Service
public class CountryServiceImpl implements CountryService{

  private final CountryRepository countryRepository;

  @Autowired
  public CountryServiceImpl(CountryRepository countryRepository) {
    this.countryRepository = countryRepository;
  }

  /**
   * Add new country
   *
   * @param dto New country parameters
   * @return created country
   */
  @Override
  public Country addCountry(CountryDto dto) {
    return countryRepository.save(toCountry(dto));
  }

  @Override
  public List<Country> getCountries() {
    return countryRepository.getCountries();
  }

  /**
   * Update existing country based on specified id
   *
   * @param id ID of the country
   * @param dto New country parameters
   * @return updated country
   */
  @Override
  public Country updateCountry(Long id, CountryDto dto) {
    final Optional<Country> optionalCountry = countryRepository.findById(id);

    final Country country = optionalCountry.orElseThrow(CountryNotFoundException::new);
    country.setName(dto.getName());
    country.setContinent(dto.getContinent());

    return country;
  }

  /**
   * Delete existing country
   *
   * @param id ID of the country
   */
  @Override
  public void deleteCountry(Long id) {
    countryRepository.deleteById(id);
  }

  private Country toCountry(CountryDto dto) {
    final Country country = new Country();
    country.setName(dto.getName());
    country.setContinent(dto.getContinent());
    country.setCreated(LocalDateTime.now());

    return country;
  }
}
