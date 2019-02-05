package com.wol.mock.hplink.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "MHPL_USER")
public class User {

	public enum Role {
		USER("user"), ADMIN("admin");

		private String value;

		private Role(String value) {
			this.value = value;
		}

		public String toString() {
			return this.value;
		}
	}

	public static enum State {
		LOGGED_IN,
		LOGGED_OUT;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(insertable = true, nullable = false, unique = true, updatable = false)
	@JsonIgnore
	private long id;

	@Column
	@JsonProperty
	private String firstName;
	
	@Column
	@JsonProperty
	private String lastName;

	@Column
	@JsonProperty
	private String email;

	@Column
	@JsonIgnore
	private String password;

	@Column
	@JsonProperty
	private Role role = Role.USER;

	@Column
	@JsonProperty
	private State state = State.LOGGED_OUT;
	
	@Column
	@JsonProperty
	private Date lastLogin;
	
	@Column
	@JsonProperty
	private Date created;

	@Column
	@JsonProperty
	private Date updated;
	
	@Transient
	@JsonProperty
	private Session session;
	
	@OneToMany
	private Set<Message> messages;
	
	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public String toString() {
		return new StringBuilder().append("User[")
				.append("email=")
				.append(email)
				.append(", lastLogin=")
				.append(lastLogin)
				.append(", role=")
				.append(role)
				.append("]").toString();
	}
}
