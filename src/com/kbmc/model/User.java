package com.kbmc.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * User entity. @author ZONE24X7
 */
@Entity
@Table(name = "user", schema = "kbmc")
public class User implements java.io.Serializable {

	// Fields

	private Long id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private Date created;
	private Date modified;
	private Long createdBy;
	private Long modifiedBy;
	private Boolean enabled;
	private String resetcode;
	private Date resetDate;

	@Column(name = "resetcode")
	public String getResetcode() {
		return resetcode;
	}

	public void setResetcode(String resetcode) {
		this.resetcode = resetcode;
	}

	@Column(name = "resetdate")
	public Date getResetDate() {
		return resetDate;
	}

	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	private String email;

	@Column(name = "email", length = 45)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// Constructors
	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(Long id, String username, String password, String firstname,
			String lastname, Timestamp created, Timestamp modified,
			Long createdBy, Long modifiedBy, Long environmentId,
			boolean enabled, String email, String avatar, String resetcode,
			Date resetDate) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.created = created;
		this.modified = modified;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.enabled = enabled;
		this.resetcode = resetcode;
		this.resetDate = resetDate;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "kbmc_id_user", sequenceName = "kbmc.kbmc_user", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kbmc_id_user")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "username", length = 45)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 45)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "firstname", length = 45)
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "lastname", length = 45)
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "created")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "modified")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Column(name = "created_by")
	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "modified_by")
	public Long getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "enabled")
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
