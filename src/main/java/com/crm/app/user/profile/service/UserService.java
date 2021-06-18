package com.crm.app.user.profile.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crm.app.user.profile.dto.ApiResonseDto;
import com.crm.app.user.profile.dto.CityDto;
import com.crm.app.user.profile.dto.CountryDto;
import com.crm.app.user.profile.dto.ErrorDto;
import com.crm.app.user.profile.dto.StateDto;
import com.crm.app.user.profile.model.City;
import com.crm.app.user.profile.model.Country;
import com.crm.app.user.profile.model.State;
import com.crm.app.user.profile.model.User;
import com.crm.app.user.profile.repository.CityRepository;
import com.crm.app.user.profile.repository.CountryRepository;
import com.crm.app.user.profile.repository.StateRepository;
import com.crm.app.user.profile.repository.UserRepository;
import com.crm.app.user.profile.util.CustomUserDetails;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {
	
	@Autowired
	private CountryRepository countryRepo;
	@Autowired
	private StateRepository stateRepo;
	@Autowired
	private CityRepository cityRepo;
	@Autowired
	private UserRepository userRepository;
	
	//@Cacheable(cacheNames = "countryCache", unless ="#result==null" )
	public List<CountryDto> fetchAllCountries(){
		List<CountryDto> cntryListResponse = new ArrayList<CountryDto>();
		CountryDto cntryDto = null;
		try {
			List<Country> countryList = countryRepo.findAll();
			for (Country c : countryList) {
					cntryDto = new CountryDto();
					cntryDto.setCountryName(c.getCountryName());
					cntryDto.setCountryId(c.getCountryId());
					cntryListResponse.add(cntryDto);
				
			}
			return cntryListResponse;
		}catch(Exception e) {
			log.error("Exception Occured : "+e.getMessage());
		}
	
		return cntryListResponse;
	}
	
	@Cacheable(cacheNames = "countryCache",key="#countryId",unless ="#result==null")
	public List<StateDto> fetchStatesByCountry(long countryId){
		List<StateDto> stateListResponse = new ArrayList<StateDto>();
		StateDto stateDto = null;
		try {
			List<State> stateList = stateRepo.fetchStatesByCountry(countryId);
			for (State s : stateList) {
				stateDto = new StateDto();
				stateDto.setStateId(s.getStateId());
				stateDto.setStateName(s.getStateName());
				stateListResponse.add(stateDto);
			}
			return stateListResponse;
		}catch(Exception e) {
			log.error("Exception Occured : "+e.getMessage());
		}
	
		return stateListResponse;
	}
	
		@Cacheable(cacheNames = "countryCache",key="#stateId",unless ="#result==null")
		public List<CityDto> fetchCityByState(long stateId){
			List<CityDto> cityListResponse = new ArrayList<CityDto>();
			CityDto cityDto = null;
			try {
				List<City> cityList = cityRepo.findCities(stateId);
				for (City cty : cityList) {
					cityDto = new CityDto();
					cityDto.setCityId(cty.getCityId());
					cityDto.setCityName(cty.getCityName());
					cityListResponse.add(cityDto);
				}
				return cityListResponse;
			}catch(Exception e) {
				log.error("Exception Occured : "+e.getMessage());
			}
		
			return cityListResponse;
		}

		@Override
	    @Transactional
	    public UserDetails loadUserByUsername(String username)
	            throws UsernameNotFoundException {
	    	
	        User user = null;
			try {
				user = (User) userRepository.findByUsername(username)
						.orElseThrow (
										() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username)
									);
			} catch (Throwable e) {
				log.error("Exception occured: " +e.getMessage());
			}

	        return CustomUserDetails.build(user);
	    }
		
		@Transactional
		public String saveUserDetails(User user) {
			userRepository.save(user);
			return "success";
		}

		public boolean isEmailIdExist(String emailId) {
			Optional<User> user = userRepository.findByEmailId(emailId);
			if(user.isPresent()) {
				return true;
			}
			return false;
		}

		@Transactional
		public void updateLoginTime(String logginTime, String emailId) {
			userRepository.updateLoginTime(logginTime, emailId);
			
		}
		
		//@CacheEvict(value = "userImage", key = "#filename")
		@Transactional
		public int updateUserImage(byte[] uploadedImage, String filename, String fileType, long userId) {
			return userRepository.updateUserImage(compressBytes(uploadedImage), filename, fileType, userId);
		}
		
		public boolean isValidImageType(String contentType) {
	        return contentType.equals("image/png")
	                || contentType.equals("image/jpg")
	                || contentType.equals("image/jpeg");
	    }

		public ApiResonseDto fetchUserImage(long userId) {
			ApiResonseDto apires = new ApiResonseDto();
			
			 userRepository.findById(userId).map(u->{
				 apires.setImage(decompressBytes(u.getImage()));
				 apires.setFilename(u.getFilename());
				 apires.setFileType(u.getFileType());
				 apires.setMessage("Success");
				 return apires;
			 }
			
		);
			
			return apires;
		}
		
		// compress the image bytes before storing it in the database
		private static byte[] compressBytes(byte[] data) {
			Deflater deflater = new Deflater();
			deflater.setInput(data);
			deflater.finish();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				int count = deflater.deflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			try {
				outputStream.close();
			} catch (IOException e) {
			}
			log.info("Compressed Image Byte Size - " + outputStream.toByteArray().length);
			return outputStream.toByteArray();
		}

		// uncompress the image bytes before returning it to the front end application
		private static byte[] decompressBytes(byte[] data) {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			try {
				while (!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
			} catch (IOException ioe) {
			} catch (DataFormatException e) {
			}
			return outputStream.toByteArray();
		}
	}
