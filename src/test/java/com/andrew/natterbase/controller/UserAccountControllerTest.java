package com.andrew.natterbase.controller;

import com.andrew.natterbase.dto.LoginDto;
import com.andrew.natterbase.dto.SignUpDto;
import com.andrew.natterbase.service.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;

import static com.andrew.natterbase.util.Utility.newLoginDto;
import static com.andrew.natterbase.util.Utility.newSignUpDto;
import static com.andrew.natterbase.util.Utility.newUserAccount;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author andrew
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserAccountController.class)
public class UserAccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @MockBean
  private UserAccountService userAccountService;

  @Test
  public void signUp() throws Exception {
    when(userAccountService.signUp(newSignUpDto())).thenReturn(newUserAccount());

    mockMvc.perform(post("/signup")
        .content(objectMapper.writeValueAsString(newSignUpDto()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value("Andrew"))
        .andExpect(jsonPath("$.lastName").value("Mbata"));
  }

  @Test
  public void signUp_incompleteFields() throws Exception {
    final SignUpDto dto = new SignUpDto();
    dto.setPassword("password");

    when(userAccountService.signUp(dto)).thenThrow(ConstraintViolationException.class);

    mockMvc.perform(post("/signup")
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void login() throws Exception {
    when(userAccountService.signUp(newSignUpDto())).thenReturn(newUserAccount());

    mockMvc.perform(post("/signup")
        .content(objectMapper.writeValueAsString(newSignUpDto()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    when(userAccountService.loadUserByUsername("don4rolex"))
        .thenReturn(new User("don4rolex", bCryptPasswordEncoder.encode("123456"), emptyList()));

    mockMvc.perform(post("/login")
        .content(objectMapper.writeValueAsString(newLoginDto()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(header().exists("Authorization"));
  }

  @Test
  public void login_invalidLogin() throws Exception {
    final LoginDto dto = new LoginDto();
    dto.setUsername("invalid");
    dto.setPassword("invalid");

    when(userAccountService.loadUserByUsername("invalid")).thenThrow(UsernameNotFoundException.class);

    mockMvc.perform(post("/login")
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized())
        .andExpect(header().doesNotExist("Authorization"));
  }

}