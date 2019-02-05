package com.wol.mock.hplink.web.entity;

import java.util.ArrayList;
import java.util.List;

import com.wol.mock.hplink.model.Event;

public class AddEventsRequest {

	private List<Event> events;
	
	public AddEventsRequest() {
		this.events = new ArrayList<Event>();
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
}
