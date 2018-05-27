package com.kbmc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * UserRole entity. @author ZONE24X7
 */
@Entity
@Table(name = "user_role", schema = "kbmc")
public class UserRole implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private Long roleId;

	// Constructors

	/** default constructor */
	public UserRole() {
	}

	/** full constructor */
	public UserRole(Long id, Long userId, Long roleId) {
		this.id = id;
		this.userId = userId;
		this.roleId = roleId;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name = "kbmc_id_userrole", sequenceName = "kbmc.kbmc_userrole", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kbmc_id_userrole")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "role_id", nullable = false)
	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}