package com.wol.mock.hplink.web.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wol.mock.hplink.model.Session;
import com.wol.mock.hplink.service.SessionService;

public class RestServiceUtils {

	public static ResponseEntity<String> verifySession(SessionService sessionService, HttpServletRequest request, boolean sessionRequired) {
		String bearer = request.getHeader("Authorization");
		if(StringUtils.isBlank(bearer)) {
			return new ResponseEntity<String>("Your client is not authorized", 
					HttpStatus.UNAUTHORIZED);
		}
		
		String token = bearer.replaceAll("Bearer ", "");
		Session session = sessionService.findByToken(token);
		
		if(sessionRequired && null == session) {
			return new ResponseEntity<String>("No session exists for token: " + token, HttpStatus.UNAUTHORIZED);
		}
		
		if(session != null && !sessionService.isSessionValid(session)) {
			return new ResponseEntity<String>("Your session is no longer valid. Please request new token.", 
					HttpStatus.UNAUTHORIZED);
		}
		
		return null;
	}
}
