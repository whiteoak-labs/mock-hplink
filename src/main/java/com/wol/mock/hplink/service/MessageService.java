package com.wol.mock.hplink.service;

import java.util.Set;

import com.wol.mock.hplink.model.Message;

public interface MessageService {

	public Message find(long id);
	
	public Set<Message> findAll();
	
	public Set<Message> findByRecipientEmail(String recipientEmail);
	
	public Set<Message> findUnread();
	
	public Message save(Message message);
	
	public Message delete(Message message);
	
	public int deleteAll();
	
	public Set<Message> filterUnreadByRecipientEmail(String recipientEmail);
}
