package com.kbmc.dao;

import java.util.List;

import com.kbmc.model.User;

/**
 * @author Zone24X7
 * 
 */
public interface UserDAO {
	public void save(User transientInstance);

	public void delete(User persistentInstance);

	public User findById(Long id);

	public List<User> findByExample(User instance);

	public List<User> findByProperty(String propertyName, Object value);

	public List<User> findByUsername(Object username);

	public List<User> findByPassword(Object password);

	public List<User> findByFirstname(Object firstname);

	public List<User> findByLastname(Object lastname);

	public List<User> findByCreatedBy(Object createdBy);

	public List<User> findByModifiedBy(Object modifiedBy);

	public List<User> findAll();

	public User merge(User detachedInstance);

	public void attachDirty(User instance);

	public void attachClean(User instance);

	public int existUser(String username);

	public int existResetCode(String resetCode);

	public List<User> getUserEnabled();
	
	public List<User> search(String search);

}
