package com.kbmc.dao;

import java.util.List;

import com.kbmc.model.UserRole;

/**
 * @author Zone24X7
 * 
 */
public interface UserRoleDAO {
	public void save(UserRole transientInstance);

	public void delete(UserRole persistentInstance);

	public UserRole findById(java.lang.Long id);

	public List<UserRole> findByExample(UserRole instance);

	public List<UserRole> findByProperty(String propertyName, Object value);

	public List<UserRole> findByUserId(Object userId);

	public List<UserRole> findByRoleId(Object roleId);

	public List<UserRole> findAll();

	public UserRole merge(UserRole detachedInstance);

	public void attachDirty(UserRole instance);

	public void attachClean(UserRole instance);

}
