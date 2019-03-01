package com.andrew.natterbase.controller;

import com.andrew.natterbase.dto.CountryDto;
import com.andrew.natterbase.model.Country;
import com.andrew.natterbase.repository.CountryRepository;
import com.andrew.natterbase.repository.UserAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.andrew.natterbase.util.Utility.newLoginDto;
import static com.andrew.natterbase.util.Utility.newSignUpDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author andrew
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CountryControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CountryRepository countryRepository;

  @Autowired
  UserAccountRepository userAccountRepository;

  private String token;

  @Before
  public void setUp() throws Exception {
    mockMvc.perform(post("/signup")
        .content(objectMapper.writeValueAsString(newSignUpDto()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    final MvcResult result = mockMvc.perform(post("/login")
        .content(objectMapper.writeValueAsString(newLoginDto()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(header().exists("Authorization"))
        .andReturn();

    token = result.getResponse().getHeader("Authorization");
  }

  @After
  public void tearDown() {
    countryRepository.deleteAll();
    userAccountRepository.deleteAll();
  }

  @Test
  public void addCountry() throws Exception {
    addCountry(new CountryDto("Nigeria", "Africa"));
  }

  @Test
  public void addCountry_noToken() throws Exception {
    mockMvc.perform(post("/countries")
        .content(objectMapper.writeValueAsString(new CountryDto("Nigeria", "Africa")))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void addCountry_incompleteFields() throws Exception {
    final CountryDto dto = new CountryDto();
    dto.setName("Ghana");

    mockMvc.perform(post("/countries")
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getCountries() throws Exception {
    addCountry(new CountryDto("England", "Europe"));
    addCountry(new CountryDto("Spain", "Europe"));

    mockMvc.perform(get("/countries")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("England"))
        .andExpect(jsonPath("$[1].continent").value("Europe"));
  }

  @Test
  public void updateCountry() throws Exception {
    final Country country = addCountry(new CountryDto("UK", "Europe"));

    mockMvc.perform(put("/countries/" + country.getId())
        .content(objectMapper.writeValueAsString(new CountryDto("England", "Europe")))
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("England"))
        .andExpect(jsonPath("$.continent").value("Europe"));
  }

  @Test
  public void updateCountry_invalidId() throws Exception {
    mockMvc.perform(put("/countries/22")
        .content(objectMapper.writeValueAsString(new CountryDto("England", "Europe")))
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token))
        .andExpect(status().isNotFound());
  }

  @Test
  public void deleteCountry() throws Exception {
    final Country country = addCountry(new CountryDto("UK", "Europe"));

    mockMvc.perform(delete("/countries/" + country.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token))
        .andExpect(status().isOk());

    mockMvc.perform(get("/countries")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token))
        .andExpect(content().string("[]"));
  }

  private Country addCountry(CountryDto dto) throws Exception {
    final MvcResult result = mockMvc.perform(post("/countries")
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(dto.getName()))
        .andExpect(jsonPath("$.continent").value(dto.getContinent()))
        .andReturn();

    return objectMapper.readValue(result.getResponse().getContentAsString(), Country.class);
  }
}