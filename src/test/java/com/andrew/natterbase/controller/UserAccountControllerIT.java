package com.andrew.natterbase.controller;

import com.andrew.natterbase.dto.LoginDto;
import com.andrew.natterbase.dto.SignUpDto;
import com.andrew.natterbase.repository.UserAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.andrew.natterbase.util.Utility.newLoginDto;
import static com.andrew.natterbase.util.Utility.newSignUpDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author andrew
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UserAccountControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserAccountRepository userAccountRepository;

  @After
  public void tearDown() {
    userAccountRepository.deleteAll();
  }

  @Test
  public void signUp() throws Exception {
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

    mockMvc.perform(post("/signup")
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void login() throws Exception {
    mockMvc.perform(post("/signup")
        .content(objectMapper.writeValueAsString(newSignUpDto()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    mockMvc.perform(post("/login")
        .content(objectMapper.writeValueAsString(newLoginDto()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(header().exists("Authorization"));
  }

  @Test
  public void login_invalidLogin() throws Exception {
    mockMvc.perform(post("/signup")
        .content(objectMapper.writeValueAsString(newSignUpDto()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    final LoginDto dto = new LoginDto();
    dto.setUsername("don4rolex");
    dto.setPassword("invalid");

    mockMvc.perform(post("/login")
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized())
        .andExpect(header().doesNotExist("Authorization"));
  }

}