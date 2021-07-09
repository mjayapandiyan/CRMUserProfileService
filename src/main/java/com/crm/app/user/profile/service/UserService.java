package com.crm.app.user.profile.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.app.user.profile.dto.ApiResonseDto;
import com.crm.app.user.profile.dto.CityDto;
import com.crm.app.user.profile.dto.CountryDto;
import com.crm.app.user.profile.dto.StateDto;
import com.crm.app.user.profile.model.City;
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
	
	@Cacheable(cacheNames = "countryCache", unless ="#result==null" )
	public List<CountryDto> fetchAllCountries(){
			return countryRepo.findAll().stream().map(
					k->{
						CountryDto cntryDto = new CountryDto();
						cntryDto.setCountryName(k.getCountryName());
						cntryDto.setCountryId(k.getCountryId());
						return cntryDto;
					}).collect(Collectors.toList());
	}
	
	@Cacheable(cacheNames = "countryCache",key="#countryId",unless ="#result==null")
	public List<StateDto> fetchStatesByCountry(long countryId){
			Optional<List<State>> stateDtoList = Optional.ofNullable(stateRepo.fetchStatesByCountry(countryId));
			if(stateDtoList.isPresent()) {
				return stateDtoList
				.get()
				.stream()
				.map(
						s->{
							StateDto stateDto = new StateDto();
							stateDto.setStateId(s.getStateId());
							stateDto.setStateName(s.getStateName());
							return stateDto;
						})
				.collect(Collectors.toList());
			}
			return new ArrayList<>();
	}
	
		@Cacheable(cacheNames = "countryCache",key="#stateId",unless ="#result==null")
		public List<CityDto> fetchCityByState(long stateId){
			Optional<List<City>> cityDtoList = Optional.ofNullable(cityRepo.findCities(stateId));
			if(cityDtoList.isPresent()) {
				return cityDtoList
				.get()
				.stream()
				.map(
						c->{
							CityDto cityDto = new CityDto();
							cityDto.setCityId(c.getCityId());
							cityDto.setCityName(c.getCityName());
							
							return cityDto;
						})
				.collect(Collectors.toList());
			}
			return new ArrayList<>();
		}

		@Override
	    @Transactional
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    	
	        User user = null;
			try {
				user =  userRepository.findByUsername(username)
						.orElseThrow (
										() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username)
									);
			} catch (Exception e) {
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
			return user.isPresent();
		}

		@Transactional
		public void updateLoginTime(String logginTime, String emailId) {
			userRepository.updateLoginTime(logginTime, emailId);
			
		}
		
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
			try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)){
				byte[] buffer = new byte[1024];
				while (!deflater.finished()) {
					int count = deflater.deflate(buffer);
					outputStream.write(buffer, 0, count);
					return outputStream.toByteArray();
			}
			
			} catch(IOException e) {
				log.error("IOException occured in compressBytes() " +e.getMessage());
			}
			
			return new byte[0];
			
		}

		// uncompress the image bytes before returning it to the front end application
		private static byte[] decompressBytes(byte[] data) {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			
			try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)){
				byte[] buffer = new byte[1024];
				while (!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
					return outputStream.toByteArray();
			}
			
			} catch(IOException | DataFormatException e) {
				log.error("IOException occured in compressBytes() " +e.getMessage());
			}
			return new byte[0];
		}

		@Transactional
		public int resetPassword(String emailId, String password, String passcode) {
			Timestamp modifiedAt = new Timestamp(new Date().getTime());
			return userRepository.resetPassword(emailId, new BCryptPasswordEncoder().encode(password), passcode, modifiedAt);
		}
	}
