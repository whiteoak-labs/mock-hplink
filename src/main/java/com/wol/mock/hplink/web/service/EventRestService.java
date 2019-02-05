package com.wol.mock.hplink.web.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wol.mock.hplink.model.Event;
import com.wol.mock.hplink.service.EventService;
import com.wol.mock.hplink.service.SessionService;
import com.wol.mock.hplink.web.entity.AddEventsRequest;

@RestController
public class EventRestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventRestService.class);
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private EventService eventService;
	
	@RequestMapping(consumes="application/json", method=RequestMethod.POST, path="/events", produces="text/plain")
	public ResponseEntity<String> addEvents(@RequestBody AddEventsRequest addEventsRequest, HttpServletRequest request) {
		LOGGER.info("Received an addEvents request");
		
		ResponseEntity<String> sessionVerification = RestServiceUtils.verifySession(sessionService, request, true);
		if(sessionVerification != null) {
			return sessionVerification;
		}
		
		List<Event> eventsToAdd = addEventsRequest.getEvents();
		
		if(!CollectionUtils.isEmpty(eventsToAdd)) {
			for(Event event : eventsToAdd) {
				String errorMsg = null;
				
				if(StringUtils.isBlank(event.getName())) {
					errorMsg = "Name is required to add a new event";
				}
				
				if(StringUtils.isBlank(event.getUid())) {
					errorMsg = "UID is required to add a new event";
				}
				
				if(StringUtils.isBlank(event.getValue())) {
					errorMsg = "Value is required to add a new event";
				}
				
				if(StringUtils.isNotBlank(errorMsg)) {
					return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);					
				}
				
				eventService.save(event);
			}
			
			String result = "Successfully added events [" + eventsToAdd.size() + "]";
			LOGGER.info(result);
			return new ResponseEntity<String>(result, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>("addEventsRequest contained not events to add", HttpStatus.OK);
	}
}
