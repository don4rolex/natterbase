package com.andrew.natterbase.exception;

/**
 * @author andrew
 */
public class CountryNotFoundException extends RuntimeException {

  public CountryNotFoundException() {
    super("Country not found");
  }

  public CountryNotFoundException(String message) {
    super(message);
  }
}
