package com.andrew.natterbase.service;

import com.andrew.natterbase.dto.SignUpDto;
import com.andrew.natterbase.model.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author andrew
 */
public interface UserAccountService extends UserDetailsService {

  UserAccount signUp(SignUpDto signUp);
}
