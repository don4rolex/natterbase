package com.andrew.natterbase.service;

import com.andrew.natterbase.dto.SignUpDto;
import com.andrew.natterbase.model.UserAccount;
import com.andrew.natterbase.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.util.Collections.emptyList;

/**
 * @author andrew
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

  private final UserAccountRepository userAccountRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserAccountServiceImpl(UserAccountRepository userAccountRepository,
                                BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userAccountRepository = userAccountRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  /**
   * Sign up for a new UserAccount
   *
   * @param signUp parameters for creating new UserAccount
   * @return newly created account
   * @throws javax.validation.ConstraintViolationException when fields are incomplete
   */
  @Override
  public UserAccount signUp(SignUpDto signUp) {
    return userAccountRepository.save(toUserAccount(signUp));
  }

  /**
   * This method is used internally by the JWTAuthenticationFilter to find and validate a username and password
   * during login.
   *
   * @param username username to validate
   * @return the JWT UserDetails for authentication
   * @throws UsernameNotFoundException when the username does not exist.
   */
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final UserAccount userAccount = userAccountRepository.findByUsername(username);

    if (userAccount == null) {
      throw new UsernameNotFoundException(username);
    }

    return new User(userAccount.getUsername(), userAccount.getPassword(), emptyList());
  }

  private UserAccount toUserAccount(SignUpDto signUp) {
    final UserAccount userAccount = new UserAccount();
    userAccount.setFirstName(signUp.getFirstName());
    userAccount.setLastName(signUp.getLastName());
    userAccount.setEmail(signUp.getEmail());
    userAccount.setUsername(signUp.getUsername());
    userAccount.setDateOfBirth(signUp.getDateOfBirth());
    userAccount.setPassword(bCryptPasswordEncoder.encode(signUp.getPassword()));
    userAccount.setCreated(LocalDateTime.now());

    return userAccount;
  }
}
