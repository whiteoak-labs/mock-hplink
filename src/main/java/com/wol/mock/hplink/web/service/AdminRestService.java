package com.wol.mock.hplink.web.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wol.mock.hplink.model.Session;
import com.wol.mock.hplink.service.SessionService;
import com.wol.mock.hplink.web.entity.SystemAdminRequest;

@RestController
public class AdminRestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminRestService.class);
	
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(consumes="application/json", path="/admin/system/reset", produces="text/plain")
	public ResponseEntity<String> systemReset(@RequestBody SystemAdminRequest adminRequest, HttpServletRequest request) {
		LOGGER.info("Received systemReset request: ");
		//TODO implement me
		return new ResponseEntity<String>("Submitted system reset request for processing", HttpStatus.OK);
	}
	
	@RequestMapping(path="/admin/system/invalidateSession")
	public ResponseEntity<String> invalidateSession(@RequestParam("token") String token, HttpServletRequest request) {
		String msg = null;
		LOGGER.info("Received invalidateSession request for token [" + token + "]");
		if(StringUtils.isBlank(token)) {
			msg = "Cannot invalidate a session without supplying a token";
			LOGGER.warn(msg);
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		
		Session session = sessionService.findByToken(token);
		
		if(session == null) {
			msg = "No session exists for token [" + token + "]";
			LOGGER.warn(msg);
			return new ResponseEntity<String>(msg, HttpStatus.ACCEPTED);
		}
		
		sessionService.invalidateWithToken(token);
		
		return new ResponseEntity<String>("Successfully invalidated session for token [" + token + "]", HttpStatus.OK);
	}
}
