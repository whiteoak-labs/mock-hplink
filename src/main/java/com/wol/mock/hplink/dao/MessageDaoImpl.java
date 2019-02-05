package com.wol.mock.hplink.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.wol.mock.hplink.model.Message;

@Repository
public class MessageDaoImpl implements MessageDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageDaoImpl.class);
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Message find(Long id) {
		assert(id != null);
		Query findQuery = entityManager.createQuery("FROM Message where id=:id");
		findQuery.setParameter("id", id);
		Message msg = null;
		
		try {
			msg = (Message) findQuery.getSingleResult();
		}
		catch(NoResultException e) {}
		
		return msg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Message> findAll() {
		Query findQuery = entityManager.createQuery("From Message");
		return new HashSet<Message>(findQuery.getResultList());
	}
	
	@Override
	public Set<Message> findByRecipientEmail(String recipientEmail) {
		assert(StringUtils.isNotBlank(recipientEmail));
		Set<Message> messages = findAll();
		Set<Message> recipientMsgs = new HashSet<Message>();
		
		if(!CollectionUtils.isEmpty(messages)) {
			for(Message message : messages) {
				if(recipientEmail.equals(message.getRecipient().getEmail())) {
					recipientMsgs.add(message);
				}
			}
		}
		
		return recipientMsgs;
	}

	@Override
	public Set<Message> findUnread() {
		Set<Message> messages = findAll();
		Set<Message> unreadMsgs = new HashSet<Message>();
		
		if(!CollectionUtils.isEmpty(messages)) {
			for(Message message : messages) {
				if(!message.isRead()) {
					unreadMsgs.add(message);
				}
			}
		}
		
		LOGGER.debug("User has " + unreadMsgs.size() + " unread messages");
		return unreadMsgs;
	}

	@Override
	@Transactional
	public Message save(Message message) {
		assert(message != null);
		Message existing = find(message.getId());
		
		if(existing != null) {
			message = entityManager.merge(message);
		}
		else {
			entityManager.persist(message);
		}
		
		entityManager.flush();
		LOGGER.debug("Successfully saved " + message);
		return message;
	}

	@Override
	@Transactional
	public Message delete(Message message) {
		assert(message != null);
		entityManager.remove(message);
		entityManager.flush();
		LOGGER.debug("Successfully deleted " + message);
		return message;
	}

	@Override
	public int deleteAll() {
		Set<Message> messages = findAll();
		int count = 0;
		
		if(!CollectionUtils.isEmpty(messages)) {
			for(Message message : messages) {
				entityManager.detach(message);
				count++;
			}
		}
		
		LOGGER.debug("Successfully deleted " + count + " messages");
		return count;
	}

}
