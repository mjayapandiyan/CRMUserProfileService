package com.crm.app.user.profile.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crm.app.user.profile.amqp.MessageSender;
import com.crm.app.user.profile.amqp.PropertyConfig;
import com.crm.app.user.profile.constants.RoleNames;
import com.crm.app.user.profile.dto.ApiResonseDto;
import com.crm.app.user.profile.dto.CityDto;
import com.crm.app.user.profile.dto.CountryDto;
import com.crm.app.user.profile.dto.LoginRequestDto;
import com.crm.app.user.profile.dto.LoginResponseDto;
import com.crm.app.user.profile.dto.MailDto;
import com.crm.app.user.profile.dto.ProfileDTO;
import com.crm.app.user.profile.dto.ResetFormDto;
import com.crm.app.user.profile.dto.SignUpDto;
import com.crm.app.user.profile.dto.StateDto;
import com.crm.app.user.profile.dto.UIParamDto;
import com.crm.app.user.profile.exception.UserInputException;
import com.crm.app.user.profile.model.Address;
import com.crm.app.user.profile.model.Profile;
import com.crm.app.user.profile.model.Role;
import com.crm.app.user.profile.model.User;
import com.crm.app.user.profile.model.UserInterfaceConfig;
import com.crm.app.user.profile.repository.CityRepository;
import com.crm.app.user.profile.repository.CountryRepository;
import com.crm.app.user.profile.repository.ProfileRepository;
import com.crm.app.user.profile.repository.RoleRepository;
import com.crm.app.user.profile.repository.StateRepository;
import com.crm.app.user.profile.repository.UserRepository;
import com.crm.app.user.profile.service.UserService;
import com.crm.app.user.profile.util.CustomUserDetails;
import com.crm.app.user.profile.util.JwtTokenUtil;
import com.crm.app.user.profile.util.PasswordGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("deprecation")
@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepository cityRepository;

	
	@Autowired
	private PasswordGenerator passwordGenerator;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private PropertyConfig propertyConfig;
	
	@Autowired
	private MessageSender messageSender;
	
	@Autowired
	private MailDto mailDto;
	
	private static final String COUNTRY_ID = "countryId";
	
	@PostMapping(value = "/users/auth")
    public ResponseEntity<LoginResponseDto> authenticateUser(@RequestBody LoginRequestDto loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
		log.info("Is User Authenticated: " +authentication.isAuthenticated());
		final CustomUserDetails userDetails =  (CustomUserDetails) userService.loadUserByUsername(loginRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		LoginResponseDto response = new LoginResponseDto();
		response.setEmailId(userDetails.getEmailId());
		response.setFirstname(userDetails.getFirstName());
		response.setLastname(userDetails.getLastName());
		response.setUserId(userDetails.getUserId());
		response.setStatus(userDetails.getStatus());
		String logginTime = String.valueOf(userDetails.getLogginTimeStamp());
		response.setLoginTimeStamp(logginTime);
		response.setToken(token);
		
		Optional<Role> roleOb = roleRepository.findById(userDetails.getRoleId());
		
		if(roleOb.isPresent()) {
			response.setRole(roleOb.get().getRoleName());
			List<UserInterfaceConfig> userparamOb = roleOb.get().getConfig();
			List<UIParamDto> uiparamList = new ArrayList<>();
			for(UserInterfaceConfig param : userparamOb) {
				UIParamDto uiparam = new UIParamDto();
				if(param.getComponent().equalsIgnoreCase("Menu") && param.getStatus().equalsIgnoreCase("Active")) {
					uiparam.setName(param.getPath());
					uiparam.setValue(param.getValue());
					uiparam.setStatus(param.getStatus());
					uiparamList.add(uiparam);
				}
			}
			response.setParam(uiparamList);
		}
		
		
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@GetMapping(value = "/welcome")
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<>("Welcome to CRM Application", HttpStatus.OK);
    }
	
	@GetMapping(value = "/location/countries")
    public ResponseEntity<List<CountryDto>> fetchAllCountries() {
		List<CountryDto> responseList = userService.fetchAllCountries();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
	
	@GetMapping(value = "/location/state")
    public ResponseEntity<List<StateDto>> fetchStateByCountry(@RequestParam("countryId") long countryId) {
		List<StateDto> stateDtoResponse= userService.fetchStatesByCountry(countryId);
        return new ResponseEntity<>(stateDtoResponse, HttpStatus.OK);
    }
	
	@GetMapping(value = "/location/city")
    public ResponseEntity<List<CityDto>> fetchStateByCity(@RequestParam("stateId") long stateId) {
		List<CityDto> cityDtoResponse = userService.fetchCityByState(stateId);
        return new ResponseEntity<>(cityDtoResponse, HttpStatus.OK);
    }
	
	@PostMapping(value = "/users/associate/onboard")
    public ResponseEntity<String> userSignUp(@Valid @RequestBody SignUpDto signUpRequest) throws UserInputException{
		if(userRepository.findByEmailId(signUpRequest.getEmail()).isPresent()) {
			throw new UserInputException("Email Id already exist!!");
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails currentUser = (CustomUserDetails)auth.getPrincipal();
		
		Date date= new Date();
		User user = new User();
		final Long userIdSequence = userRepository.getNextSequenceNumber();
		user.setUserId(userIdSequence);
		user.setTitle(signUpRequest.getTitle());
		user.setFirstname(signUpRequest.getFirstname());
		user.setLastname(signUpRequest.getLastname());
		user.setUsername(signUpRequest.getEmail());
		user.setEmailId(signUpRequest.getEmail());
		user.setDob(signUpRequest.getDob());
		user.setContactno(signUpRequest.getContactno());
		user.setGender(signUpRequest.getGender());
		user.setCreatedAt(new Timestamp(date.getTime()));
		user.setCreatedBy(String.valueOf(currentUser.getUserId()));
		user.setQualification(signUpRequest.getQualification());
		user.setStatus("Active");
		user.setRole(signUpRequest.getRole());
		char[] oneTimeAccessCode = passwordGenerator.generatePassword();
		String accessCode = new String(oneTimeAccessCode);
		user.setAccessCode(accessCode);
		Address address = new Address();
		address.setLandmark(signUpRequest.getLandmark());
		address.setCity(signUpRequest.getCity());
		address.setCountry(signUpRequest.getCountry());
		address.setState(signUpRequest.getState());
		address.setPincode(signUpRequest.getZipcode());
		user.setAddress(address);
		Profile prof = new Profile();
		prof.setDirectorId(currentUser.getUserId());
		prof.setDirectorName(currentUser.getFirstName()+" " +currentUser.getLastName());
		prof.setIsTerminated("N");
		prof.setIsTerminationLetterIssued("N");
		prof.setUserId(userIdSequence);
		prof.setAllocatedBy(currentUser.getUserId());
		prof.setAllocationStatus("UNALLOCATED");
		prof.setAllocatedDate(new Timestamp(date.getTime()));
		prof.setProjectId(9999);
		
		userRepository.findById(currentUser.getUserId()).map(
				k->{
					prof.setCompanyId(k.getProfile().getCompanyId());
					prof.setCompanyName(k.getProfile().getCompanyName());
					prof.setUrl(k.getProfile().getUrl());
					return prof;
				}
		);
				
		user.setProfile(prof);
		
		if(currentUser.getRoleId() == RoleNames.ROLE_DIRECTOR.getRoleId()) {
			user.setRoleId(RoleNames.ROLE_EMPLOYEE.getRoleId());
		} else {
			user.setRoleId(RoleNames.ROLE_DIRECTOR.getRoleId());
		}
		userService.saveUserDetails(user);
		String response = sendEmailNotification(user);
        return new ResponseEntity<>("User Registration Done Successfully. "+response, HttpStatus.OK);
    }
	
	 private String sendEmailNotification(User user) {
		 mailDto.setAccessCode(user.getAccessCode());
		 mailDto.setCreatedDate(String.valueOf(user.getCreatedAt()));
		 mailDto.setMailId(user.getEmailId());
		 mailDto.setFirstname(user.getFirstname());
		 mailDto.setLastname(user.getLastname());
		 mailDto.setMessage("New Associate Registration Confirmation - CRM Web");
		 messageSender.sendMessage(rabbitTemplate, propertyConfig.getExchange(), propertyConfig.getRoutingKey(), mailDto);
		 return "An E-mail has been triggerd to the associate";
	}

	@PutMapping("/users/profile/image/upload")
	 public ResponseEntity<ApiResonseDto> uploadImage(@RequestParam("imageFile") MultipartFile file, @RequestParam("userId") long userId) throws IOException {
		 	if(file.isEmpty()) {
		 		throw new UserInputException("Please upload an Image");
		 	} else if(!userService.isValidImageType(file.getContentType())){
		 		throw new UserInputException("Only .png or .jpeg types are allowed");
		 	}
		 	
			log.info("Uploaded Image Byte Size - " + file.getBytes().length);
			log.info("file name : " + file.getOriginalFilename() + "file.getContentType() : " +file.getContentType());
			
			String filename = userId+".jpg";
			
			writeImageToFilePath(file.getBytes(), filename);
			int count = userService.updateUserImage(file.getBytes(), filename, file.getContentType(), userId);
			ApiResonseDto response = new ApiResonseDto();
			if(count > 0) {
				log.info("Image has been uploaded Successfully. upload count : " +count);
				response.setMessage("Image has been uploaded Successfully");
			} else {
				throw new UserInputException("Image upload failed for unknown reason");
			}
			 return new ResponseEntity<>(response,HttpStatus.OK);
		}
	 
	private void writeImageToFilePath(byte[] uploadedImage, String filename) {
		 StringBuilder filePath = new StringBuilder();
		 try (
				 FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath.append(propertyConfig.getImageFilePath())
				 .append("/")
				 .append(filename)
				 .toString()));
				 BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream)){
			
			 bos.write(uploadedImage);    
			 bos.flush();  
			 log.info("Image has been written successfully");
		 } catch (Exception ex) {
			 log.error("Exception caught at writeImageToFilePath() " +ex.getMessage());
		 }    
	}

	 @GetMapping("/users/profile/image/{userId}")
	 public ApiResonseDto fetchImage(@PathVariable("userId") long userId, HttpServletResponse response) throws UserInputException {
		 if(!userRepository.findById(userId).isPresent()) {
				throw new UserInputException("User Id is does not exist!!");
			}
		 return userService.fetchUserImage(userId);
		}
	 
	 
		
		
		@GetMapping(value = "/users/profile/{userId}")
		public ProfileDTO getUserProfile(@PathVariable("userId") long userId) {
			ProfileDTO profiledDto = new ProfileDTO();
			Map<String,String> address = new HashMap<>();
			userRepository.findById(userId).map(
					userOb -> {
						profiledDto.setFirstname(userOb.getFirstname());
						profiledDto.setLastname(userOb.getLastname());
						profiledDto.setEmailId(userOb.getEmailId());
						profiledDto.setUserId(userOb.getUserId());
						profiledDto.setStatus(userOb.getStatus());
						profiledDto.setTitle(userOb.getTitle());
						profiledDto.setGender(userOb.getGender());
						profiledDto.setQualification(userOb.getQualification());
						profiledDto.setRole(userOb.getRole());
						profiledDto.setDob(userOb.getDob());
						profiledDto.setPhone(userOb.getContactno());
						address.put("pincode", userOb.getAddress().getPincode());
						address.put("landmark", userOb.getAddress().getLandmark());
						address.put(COUNTRY_ID, userOb.getAddress().getCountry());
						countryRepository.findById(Long.valueOf(address.get(COUNTRY_ID)))
						.map(
							k-> address.put("countryName", k.getCountryName())
						);
							stateRepository.fetchStatesByCountry(Long.valueOf(address.get(COUNTRY_ID)))
									.stream()
									.filter(k->String.valueOf(k.getStateId()).equals(userOb.getAddress().getState()))
									.findFirst()
									.map(
										v-> {
										address.put("stateId", String.valueOf(v.getStateId())); 
										  address.put("stateName", v.getStateName());
											  v.getCity().stream()
											  .filter(c-> String.valueOf(userOb.getAddress().getCity()).equalsIgnoreCase(String.valueOf(c.getCityId())))
											  .findAny()
											  .map(
												n-> {
													address.put("cityId", String.valueOf(n.getCityId()));
													address.put("cityName", n.getCityName());
													return address;
												});
										  return address;
										
										});
						
							profiledDto.setAddress(address);
							profiledDto.setUrl(userOb.getProfile().getUrl());
							profiledDto.setCompanyName(userOb.getProfile().getCompanyName());
							profiledDto.setManagerId(userOb.getProfile().getDirectorId());
							profiledDto.setManagerName(userOb.getProfile().getDirectorName());
							profiledDto.setAllocationStatus(userOb.getProfile().getAllocationStatus());
							profiledDto.setAssignedFrom(userOb.getProfile().getAssignedfrom());
							profiledDto.setAssignedTo(userOb.getProfile().getAssignedTo());
						
							//TODO - Project finding logic
						return profiledDto;
						});
						
			return profiledDto;
		}
		
		
@PutMapping("/users/profile/update")
public ResponseEntity<ApiResonseDto> updateUserProfile(@RequestBody SignUpDto updateRequest,HttpServletRequest request) {
	ApiResonseDto response = new ApiResonseDto();
	response.setStatusCode(HttpStatus.OK);
	response.setPath(request.getContextPath());
	response.setMessage("Your profile information has been updated successfully");
	return new ResponseEntity<>(response, HttpStatus.OK);
}

@PutMapping("/users/auth/pwd/reset")
public ResponseEntity<ApiResonseDto> passwordReset(@RequestBody ResetFormDto resetForm,HttpServletRequest request) {
	
	int rowsAffected = userService.resetPassword(resetForm.getMailId(), String.valueOf(resetForm.getPassword()), String.valueOf(resetForm.getAccessCode()));
	log.info("No of rows updated : "+rowsAffected);
	ApiResonseDto response = new ApiResonseDto();
	response.setStatusCode(HttpStatus.OK);
	response.setPath(request.getContextPath());
	response.setMessage("Password has been reset successfully");
	return new ResponseEntity<>(response, HttpStatus.OK);
}

}
