package com.kbmc.dao;

import java.util.List;

import com.kbmc.model.Role;

/**
 * @author Zone24X7
 * 
 */
public interface RoleDAO {
	public void save(Role transientInstance);

	public void delete(Role persistentInstance);

	public Role findById(java.lang.Long id);

	public List<Role> findByExample(Role instance);

	public List<Role> findByProperty(String propertyName, Object value);

	public List<Role> findAll();

	public List<Role> findByName(Object name);

	public Role merge(Role detachedInstance);

	public void attachDirty(Role instance);

	public void attachClean(Role instance);

}
