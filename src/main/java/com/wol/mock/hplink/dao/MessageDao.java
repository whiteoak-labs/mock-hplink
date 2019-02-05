package com.wol.mock.hplink.dao;

import java.util.Set;

import com.wol.mock.hplink.model.Message;

public interface MessageDao extends BasicDao<Message> {

	public Set<Message> findByRecipientEmail(String recipientEmail);
	
	public Set<Message> findUnread();
}
