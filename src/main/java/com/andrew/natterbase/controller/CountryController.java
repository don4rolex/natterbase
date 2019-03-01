package com.andrew.natterbase.controller;

import com.andrew.natterbase.dto.CountryDto;
import com.andrew.natterbase.model.Country;
import com.andrew.natterbase.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author andrew
 */
@RestController
@RequestMapping(value = "/countries")
public class CountryController {

  private final CountryService countryService;

  @Autowired
  public CountryController(CountryService countryService) {
    this.countryService = countryService;
  }

  @PostMapping
  public ResponseEntity<Country> addCountry(@RequestBody CountryDto country) {
    return ResponseEntity.ok(countryService.addCountry(country));
  }

  @GetMapping
  public ResponseEntity<List<Country>> getCountries() {
    return ResponseEntity.ok(countryService.getCountries());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Country> updateCountry(@PathVariable(name = "id") Long id, @RequestBody CountryDto country) {
    return ResponseEntity.ok(countryService.updateCountry(id, country));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteCountry(@PathVariable(name = "id") Long id) {
    countryService.deleteCountry(id);

    return ResponseEntity.ok().build();
  }
}