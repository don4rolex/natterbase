package com.andrew.natterbase.controller;

import com.andrew.natterbase.dto.SignUpDto;
import com.andrew.natterbase.model.UserAccount;
import com.andrew.natterbase.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andrew
 */
@RestController
public class UserAccountController {

  private final UserAccountService userAccountService;

  @Autowired
  public UserAccountController(UserAccountService userAccountService) {
    this.userAccountService = userAccountService;
  }

  @PostMapping(path = "/signup")
  public ResponseEntity<UserAccount> signUp(@RequestBody SignUpDto signUp) {
    return ResponseEntity.ok(userAccountService.signUp(signUp));
  }
}
