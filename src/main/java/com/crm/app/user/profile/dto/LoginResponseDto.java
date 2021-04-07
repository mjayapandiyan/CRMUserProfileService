package com.crm.app.user.profile.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponseDto extends BaseDto{
	
	private String token;
    private String type = "Bearer";
    private List<UIParamDto> param;
}
