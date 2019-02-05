package com.wol.mock.hplink.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="MHPLINK_MESSAGE")
public class Message {

	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Id
	@Column(nullable=false, unique=true)
	@JsonIgnore
	private Long id;
	
	@ManyToOne
	@JoinColumn
	@JsonProperty
	private User recipient;
	
	@ManyToOne
	@JoinColumn
	@JsonProperty
	private User sender;
	
	@Column
	@JsonProperty
	private Date sent;
	
	@Column
	@JsonProperty
	private String text;
	
	@Column
	@JsonProperty
	private boolean read;
	
	public Message() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Date getSent() {
		return sent;
	}

	public void setSent(Date sent) {
		this.sent = sent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
	
	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public String toString() {
		return new StringBuilder().append("Message[")
				.append("sender=").append(sender.getEmail())
				.append(", recipient=").append(recipient.getEmail())
				.append(", sent=").append(sent).toString();
	}
}
