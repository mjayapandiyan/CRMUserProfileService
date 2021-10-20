package com.crm.app.user.profile.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.validation.constraints.AssertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.crm.app.user.profile.exception.InvalidRecordException;
import com.crm.app.user.profile.model.City;
import com.crm.app.user.profile.model.Country;
import com.crm.app.user.profile.model.State;
import com.crm.app.user.profile.repository.CityRepository;
import com.crm.app.user.profile.repository.CountryRepository;
import com.crm.app.user.profile.repository.StateRepository;
import com.crm.app.user.profile.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest{
	
	@InjectMocks
	private UserService userService;
	
    @Mock
    private CountryRepository cntryDao;
    
    @Mock
    private StateRepository stateDao;
    
    @Mock
    private CityRepository cityDao;
    
    @Captor
    private ArgumentCaptor<Long> captor;
	
	
	
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
		 verify(cntryDao).findAll(); 

		log.info("fetchAllCountriesSizeTest end");
	}
	
	@Test
	public void fetchAllCountriesValuesTest() {
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
		Assert.assertEquals("India", userService.fetchAllCountries().get(0).getCountryName());
		log.info("fetchAllCountriesValuesTest end");
	}
	
	@Test
	public void fetchCountriesNoOfTimeMethodInvocationTest() {
		ArrayList<Country> cntryList = new ArrayList<>();
		when(cntryDao.findAll()).thenReturn(cntryList);
		userService.fetchAllCountries();
		verify(cntryDao, times(1)).findAll();
		verify(cntryDao, atMost(1)).findAll();
		verify(cntryDao, atLeast(1)).findAll();
	}
	
	@Test
	public void fetchStatesByCountryValueTest() {
		State tn = new State(100,"Tamil Nadu", new Country(101, "India", "100", 91L, null),null);
		State kerala = new State(101,"Kerala", new Country(101, "India", "100", 91L, null),null);
		ArrayList<State> stateList = new ArrayList<>();
		stateList.add(kerala);
		stateList.add(tn);
		when(stateDao.fetchStatesByCountry(101)).thenReturn(stateList);
		Assert.assertEquals("Kerala", userService.fetchStatesByCountry(101).get(0).getStateName());
	}
	
	
	@Test
	public void fetchStatesByCountryEmptySetTest() {
		when(stateDao.fetchStatesByCountry(101)).thenReturn(new ArrayList<>());
		Assert.assertEquals(0, userService.fetchStatesByCountry(101).size());
	}
	
	@Test
	public void fetchCityByStateValueTest() throws Exception{
		City chennai = new City(200,"Chennai",new State(100,"Tamil Nadu", new Country(101, "India", "100", 91L, null),null));
		City salem = new City(201,"Salem",new State(100,"Tamil Nadu", new Country(101, "India", "100", 91L, null),null));
		ArrayList<City> cityList = new ArrayList<>();
		cityList.add(chennai);
		cityList.add(salem);
		when(cityDao.findCities(100)).thenReturn(cityList);
		Assert.assertEquals("Chennai", userService.fetchCityByState(100).get(0).getCityName());
		
	}
	
	@Test
	public void fetchCityByState_InvalidStateId_test(){
		
		try {
			when(cityDao.findCities(0L)).thenThrow(new InvalidRecordException("State Id does not exist"));
			userService.fetchCityByState(0L);
		} catch (Exception e) {
			assertTrue(e instanceof InvalidRecordException);
			assertEquals("State Id does not exist", e.getMessage());
		}
		
	}
	
	
	
	 
	

}
