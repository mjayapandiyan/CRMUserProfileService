package com.crm.app.user.profile.service.test;

import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import com.crm.app.user.profile.dto.CountryDto;
import com.crm.app.user.profile.model.Country;
import com.crm.app.user.profile.repository.CountryRepository;
import com.crm.app.user.profile.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest{
	
	@InjectMocks
	private UserService userService;
	
    @Mock
    private CountryRepository cntryDao;
	
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        log.info("Setting up");
    }
	
	
	@Test
	public void fetchAllCountriesSizeTest() {
		Country cto1 = new Country();
		cto1.setCountryId(1);
		cto1.setCountryName("India");
		cto1.setCountryCode("101");
		
		Country cto2 = new Country();
		cto2.setCountryId(2);
		cto2.setCountryName("China");
		cto2.setCountryCode("102");
		
		ArrayList<Country> cntryList = new ArrayList<>();
		cntryList.add(cto1);cntryList.add(cto2);
		when(cntryDao.findAll()).thenReturn(cntryList);
		Assert.assertEquals(2, userService.fetchAllCountries().size());
		log.info("fetchAllCountriesSizeTest end");
	}
	
	/*@Test(expected = NullPointerException.class)
	public void fetchAllCountriesExceptionTest() {
		Country cto = new Country();
		cto.setCountryId(2);
		cto.setCountryName("China");
		cto.setCountryCode("102");
			when(cntryDao.findAll()).thenThrow(NullPointerException.class);
			Assert.assertThrows(NullPointerException.class, () -> userService.fetchAllCountries());
	}*/

}
