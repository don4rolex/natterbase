package com.andrew.natterbase.controller;

import com.andrew.natterbase.dto.CountryDto;
import com.andrew.natterbase.exception.CountryNotFoundException;
import com.andrew.natterbase.model.Country;
import com.andrew.natterbase.service.CountryService;
import com.andrew.natterbase.service.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

import static com.andrew.natterbase.util.Utility.newCountry;
import static com.andrew.natterbase.util.Utility.newLoginDto;
import static com.andrew.natterbase.util.Utility.newSignUpDto;
import static com.andrew.natterbase.util.Utility.newUserAccount;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author andrew
 */
@RunWith(SpringRunner.class)
@WebMvcTest({UserAccountController.class, CountryController.class})
public class CountryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @MockBean
  private CountryService countryService;

  @MockBean
  private UserAccountService userAccountService;

  private String token;

  @Before
  public void setUp() throws Exception {
    when(userAccountService.signUp(newSignUpDto())).thenReturn(newUserAccount());

    mockMvc.perform(post("/signup")
        .content(objectMapper.writeValueAsString(newSignUpDto()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    when(userAccountService.loadUserByUsername("don4rolex"))
        .thenReturn(new User("don4rolex", bCryptPasswordEncoder.encode("123456"), emptyList()));

    final MvcResult result = mockMvc.perform(post("/login")
        .content(objectMapper.writeValueAsString(newLoginDto()))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(header().exists("Authorization"))
        .andReturn();

    token = result.getResponse().getHeader("Authorization");
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

    when(countryService.addCountry(dto)).thenThrow(ConstraintViolationException.class);

    mockMvc.perform(post("/countries")
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getCountries() throws Exception {
    final CountryDto england = new CountryDto("England", "Europe");
    final CountryDto spain = new CountryDto("Spain", "Europe");

    when(countryService.getCountries()).thenReturn(Arrays.asList(newCountry(england), newCountry(spain)));

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

    final CountryDto dto = new CountryDto("England", "Europe");
    final Country expectedCountry = newCountry(dto);
    when(countryService.updateCountry(country.getId(), dto)).thenReturn(expectedCountry);

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
    final CountryDto dto = new CountryDto("UK", "Europe");
    when(countryService.updateCountry(22L, dto)).thenThrow(CountryNotFoundException.class);

    mockMvc.perform(put("/countries/22")
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token))
        .andExpect(status().isNotFound());
  }

  @Test
  public void deleteCountry() throws Exception {
    doNothing().when(countryService).deleteCountry(1L);

    mockMvc.perform(delete("/countries/" + 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", token))
        .andExpect(status().isOk());

    verify(countryService, times(1)).deleteCountry(1L);
  }

  private Country addCountry(CountryDto dto) throws Exception {
    when(countryService.addCountry(dto)).thenReturn(newCountry(dto));

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