package com.andrew.natterbase.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.AbstractMap;

/**
 * @author andrew
 */
@ControllerAdvice
@Component
public class GlobalExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(value = {
      InvalidFormatException.class,
      MismatchedInputException.class,
      ConstraintViolationException.class,
      NullPointerException.class
  })
  public ResponseEntity<AbstractMap.SimpleEntry<String, String>> invalidRequest(Exception exception) {
    LOG.error("Invalid request. ", exception);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @ExceptionHandler(value = {CountryNotFoundException.class})
  public ResponseEntity<AbstractMap.SimpleEntry<String, String>> countryNotFound(Exception exception) {
    LOG.error("Country not found", exception);

    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}
