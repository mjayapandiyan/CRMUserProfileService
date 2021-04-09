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
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
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

import com.crm.app.user.profile.binding.MailBinding;
import com.crm.app.user.profile.constants.RoleNames;
import com.crm.app.user.profile.constants.UserConstant;
import com.crm.app.user.profile.dto.ApiResonseDto;
import com.crm.app.user.profile.dto.CityDto;
import com.crm.app.user.profile.dto.CountryDto;
import com.crm.app.user.profile.dto.LoginRequestDto;
import com.crm.app.user.profile.dto.LoginResponseDto;
import com.crm.app.user.profile.dto.ProfileDTO;
import com.crm.app.user.profile.dto.ProjectDTO;
import com.crm.app.user.profile.dto.SignUpDto;
import com.crm.app.user.profile.dto.StateDto;
import com.crm.app.user.profile.dto.UIParamDto;
import com.crm.app.user.profile.exception.UserInputException;
import com.crm.app.user.profile.model.Address;
import com.crm.app.user.profile.model.Project;
import com.crm.app.user.profile.model.Role;
import com.crm.app.user.profile.model.State;
import com.crm.app.user.profile.model.User;
import com.crm.app.user.profile.model.UserInterfaceConfig;
import com.crm.app.user.profile.repository.CityRepository;
import com.crm.app.user.profile.repository.CountryRepository;
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
@EnableBinding(MailBinding.class)
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
	private MailBinding mailNotificationBinder;
	
	private File imagePath = null;
	private FileOutputStream fos = null;
	private BufferedOutputStream bos = null;
	
	@PostMapping(value = "/users/auth")
    public ResponseEntity<LoginResponseDto> authenticateUser(@RequestBody LoginRequestDto loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
		
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
			List<UIParamDto> uiparamList = new ArrayList<UIParamDto>();
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
    public ResponseEntity<?> welcome() {
        return new ResponseEntity<>("Welcome to CRM Application", HttpStatus.OK);
    }
	
	@GetMapping(value = "/location/countries")
    public ResponseEntity<?> fetchAllCountries() {
		List<CountryDto> responseList = userService.fetchAllCountries();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
	
	@GetMapping(value = "/location/state")
    public ResponseEntity<?> fetchStateByCountry(@RequestParam("countryId") long countryId) {
		List<StateDto> stateDtoResponse= userService.fetchStatesByCountry(countryId);
        return new ResponseEntity<>(stateDtoResponse, HttpStatus.OK);
    }
	
	@GetMapping(value = "/location/city")
    public ResponseEntity<?> fetchStateByCity(@RequestParam("stateId") long stateId) {
		List<CityDto> cityDtoResponse = userService.fetchCityByState(stateId);
        return new ResponseEntity<>(cityDtoResponse, HttpStatus.OK);
    }
	
	@PostMapping(value = "/users/associate/onboard")
    public ResponseEntity<?> userSignUp(@Valid @RequestBody SignUpDto signUpRequest) throws UserInputException{
		if(!userRepository.findByEmailId(signUpRequest.getEmail()).isEmpty()) {
			throw new UserInputException("Email Id already exist!!");
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails currentUser = (CustomUserDetails)auth.getPrincipal();
		Date date= new Date();
		User user = new User();
		user.setTitle(signUpRequest.getTitle());
		user.setFirstname(signUpRequest.getFirstname());
		user.setLastname(signUpRequest.getLastname());
		user.setUsername(signUpRequest.getEmail());
		user.setDob(signUpRequest.getDob());
		user.setEmailId(signUpRequest.getEmail());
		user.setContactno(signUpRequest.getContactno());
		user.setGender(signUpRequest.getGender());
		user.setCreatedAt(new Timestamp(date.getTime()));
		user.setCreatedBy(String.valueOf(currentUser.getUserId()));
		user.setStatus("Active");
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
		if(currentUser.getRoleId() == RoleNames.ROLE_DIRECTOR.getRoleId()) {
			user.setRoleId(RoleNames.ROLE_EMPLOYEE.getRoleId());
		} else {
			user.setRoleId(RoleNames.ROLE_DIRECTOR.getRoleId());
		}
		userService.saveUserDetails(user);
		mailNotificationBinder.triggerEmailNotification().send(MessageBuilder.withPayload("Test@123").build());
        return new ResponseEntity<>("User Registration Done Successfully", HttpStatus.OK);
    }
	
	 @PutMapping("/users/profile/image/upload")
	 public ResponseEntity<ApiResonseDto> uploadImage(@RequestParam("imageFile") MultipartFile file, @RequestParam("userId") long userId) throws Exception {
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
			 return new ResponseEntity<ApiResonseDto>(response,HttpStatus.OK);
		}
	 
	 private void writeImageToFilePath(byte[] uploadedImage, String filename) throws Exception {
		 StringBuilder filePath = new StringBuilder();
		 try {
			 imagePath = new File(filePath.append(UserConstant.IMAGE_FILE_PATH)
					 .append("/")
					 .append(filename)
					 .toString());
			  fos = new FileOutputStream(imagePath);    
			  bos = new BufferedOutputStream(fos);    
			  bos.write(uploadedImage);    
			  bos.flush();    
			 log.info("Image has been written successfully");
		 } catch (Exception ex) {
			 log.error("Exception caught at writeImageToFilePath() " +ex.getMessage());
		 } finally {
			 	bos.close();    
			 	fos.close();   
		 }
	}

	 @GetMapping("/users/profile/image/{userId}")
	 public ApiResonseDto fetchImage(@PathVariable("userId") long userId, HttpServletResponse response) throws Exception {
		 if(userRepository.findById(userId).isEmpty()) {
				throw new UserInputException("User Id is does not exist!!");
			}
		 ApiResonseDto apiResponse = userService.fetchUserImage(userId);
		 return apiResponse;
		}
	 
	 
		
		
		@GetMapping(value = "/users/profile/{userId}")
		public ProfileDTO getUserProfile(@PathVariable("userId") long userId) throws Exception {
			ProfileDTO profile = new ProfileDTO();
			Map<String,String> address = new HashMap<>();
			userRepository.findById(userId).map(
					userOb -> {
						ProjectDTO project = new ProjectDTO();
						profile.setFirstname(userOb.getFirstname());
						profile.setLastname(userOb.getLastname());
						profile.setEmailId(userOb.getEmailId());
						profile.setUserId(userOb.getUserId());
						profile.setStatus(userOb.getStatus());
						profile.setTitle(userOb.getTitle());
						profile.setGender(userOb.getGender());
						profile.setQualification(userOb.getQualification());
						profile.setRole(userOb.getRole());
						profile.setDob(userOb.getDob());
						profile.setPhone(userOb.getContactno());
						address.put("pincode", userOb.getAddress().getPincode());
						address.put("landmark", userOb.getAddress().getLandmark());
						address.put("countryId", userOb.getAddress().getCountry());
						countryRepository.findById(Long.valueOf(address.get("countryId")))
						.map(
								
							k-> { 
								return address.put("countryName", k.getCountryName());
								}
						);
							stateRepository.fetchStatesByCountry(Long.valueOf(address.get("countryId")))
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
						
						profile.setAddress(address);
						profile.setUrl(userOb.getProfile().getUrl());
						profile.setCompanyName(userOb.getProfile().getCompanyName());
						profile.setManagerId(userOb.getProfile().getDirectorId());
						profile.setManagerName(userOb.getProfile().getDirectorName());
						List<Project> projectList = userOb.getProfile().getProject();
						for(Project p : projectList) {
							if("Active".equalsIgnoreCase(p.getAllocationStatus())) {
								project.setAllocationStatus(p.getAllocationStatus());
								project.setProjectName(p.getProjectName());
								project.setClientName(p.getClientName());
								project.setAssingedFrom(p.getAssingedFrom());
								project.setAssingedTo(p.getAssingedTo());
								break;
							}
						}
						profile.setProject(project);
						
						return profile;
						});
						
			return profile;
		}
		
		


}
