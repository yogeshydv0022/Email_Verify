package com.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseRequest {
	
	private String name;
	private String email;
	

}
