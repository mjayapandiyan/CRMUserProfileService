package com.crm.app.user.profile.controller.test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.crm.app.user.profile.controller.UserController;
import com.crm.app.user.profile.dto.CountryDto;
import com.crm.app.user.profile.service.UserService;
import com.crm.app.user.profile.util.AuthEntryPointJwt;
import com.crm.app.user.profile.util.JwtTokenUtil;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockmvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private AuthEntryPointJwt unauthorizedHandler;
	
	@MockBean
	private JwtTokenUtil jwtTokenUtil;
	
	@Test
	public void fetchAllCountries_Controller_Test() throws Exception {
		
		List<CountryDto> countries = Arrays.asList(
				new CountryDto("India",101L),
				new CountryDto("China",102L));
		when(userService.fetchAllCountries()).thenReturn(countries);
		mockmvc.perform(get("/location/countries"))
				.andExpect(status().isOk());				
	}

}
