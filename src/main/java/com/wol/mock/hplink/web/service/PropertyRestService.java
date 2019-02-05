package com.wol.mock.hplink.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wol.mock.hplink.model.Property;
import com.wol.mock.hplink.service.PropertyService;
import com.wol.mock.hplink.service.SessionService;
import com.wol.mock.hplink.web.entity.AddPropertyRequest;
import com.wol.mock.hplink.web.entity.DeletePropertyRequest;

@RestController
@RequestMapping("/properties")
public class PropertyRestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyRestService.class);
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private PropertyService propertyService;
	
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<String> getProperties(@RequestParam("uid") String uid,  HttpServletRequest request) {
		LOGGER.info("Received getProperties request for uid: " + uid);
		
		ResponseEntity<String> sessionVerification = RestServiceUtils.verifySession(sessionService, request, true);
		if(sessionVerification != null) {
			return sessionVerification;
		}
		
		Set<Property> foundProperties = propertyService.findByUID(uid);
		String result = null;
		
		if(!CollectionUtils.isEmpty(foundProperties)) {
			Map<String, String> foundPropertiesMap = new HashMap<String, String>();
			for(Property property : foundProperties) {
			    foundPropertiesMap.put(property.getName(), property.getValue());	
			}
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				result = mapper.writeValueAsString(foundPropertiesMap);
			} 
			catch (JsonProcessingException e) {
				LOGGER.error("Error marshalling properties response: " + e.getMessage(), e);
			}
			
			LOGGER.info("Found properties [" + foundProperties.size() + "] with uid [" + uid + "]");
			LOGGER.debug(result);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(consumes="application/json", method=RequestMethod.POST, produces="text/plain")
	public ResponseEntity<String> addProperties(@RequestBody AddPropertyRequest addPropertyRequest, HttpServletRequest request) {
		LOGGER.info("Received addProperties request");
		
		ResponseEntity<String> sessionVerification = RestServiceUtils.verifySession(sessionService, request, true);
		if(sessionVerification != null) {
			return sessionVerification;
		}
		
		List<Property> properties = addPropertyRequest.getProperties();
		
		if(!CollectionUtils.isEmpty(properties)) {
			for(Property property : properties) {
				propertyService.save(property);
			}
			
		    String result = ("Successfully added properties [" + properties.size() + "]"); 
			LOGGER.info(result);
			return new ResponseEntity<String>(result, HttpStatus.CREATED);
		}
		
		LOGGER.warn("addProperties request contained no properties.");
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(consumes="application/json", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteProperties(@RequestBody DeletePropertyRequest deletePropertyRequest, HttpServletRequest request) {
		LOGGER.info("Received deleteProperties request");
		
		ResponseEntity<String> sessionVerification = RestServiceUtils.verifySession(sessionService, request, true);
		if(sessionVerification != null) {
			return sessionVerification;
		}
		
		List<Property> properties = deletePropertyRequest.getProperties();
		
		if(!CollectionUtils.isEmpty(properties)) {
			for(Property property : properties) {
				propertyService.delete(property);
			}
			
			LOGGER.info("Successfully deleted properties");
			return new ResponseEntity<String>(HttpStatus.OK);	
		}
		
		LOGGER.warn("deleteProperties request contained no properties.");
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
}
