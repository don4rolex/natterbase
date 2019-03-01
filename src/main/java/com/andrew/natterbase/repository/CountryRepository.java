package com.andrew.natterbase.repository;

import com.andrew.natterbase.model.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author andrew
 */
public interface CountryRepository extends CrudRepository<Country, Long> {

    @Query("SELECT c FROM Country c")
    List<Country> getCountries();
}

