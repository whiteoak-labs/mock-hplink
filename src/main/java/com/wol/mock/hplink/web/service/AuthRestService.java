package com.wol.mock.hplink.web.service;

import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wol.mock.hplink.service.SessionService;

@RestController
public class AuthRestService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthRestService.class);

	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(method=RequestMethod.POST, path="/token", produces="application/json")
	public ResponseEntity<String> getToken(HttpServletRequest request) {
		LOGGER.info("Received getToken request");
		ObjectMapper mapper = new ObjectMapper();
		Properties properties = new Properties();
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		properties.put("accessToken", token);
		String result = null;
		
		try {
			result = mapper.writeValueAsString(properties);
		} catch (JsonProcessingException e) {
			String errorMsg = "Failed to marshall authorization token response." + e.getMessage();
			LOGGER.error(errorMsg, e);
			return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		sessionService.createWithToken(token);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
