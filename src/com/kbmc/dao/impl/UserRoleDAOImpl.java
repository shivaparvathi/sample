package com.kbmc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.kbmc.dao.UserRoleDAO;
import com.kbmc.model.UserRole;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserRole entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.kbmc.model.UserRole
 * @author ZONE24X7
 */

public class UserRoleDAOImpl extends HibernateDaoSupport implements UserRoleDAO {
	private static final Logger log = Logger.getLogger(UserRoleDAOImpl.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String ROLE_ID = "roleId";

	protected void initDao() {
		// do nothing
	}

	public void save(UserRole transientInstance) {
		log.debug("saving UserRole instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserRole persistentInstance) {
		log.debug("deleting UserRole instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserRole findById(java.lang.Long id) {
		log.debug("getting UserRole instance with id: " + id);
		try {
			UserRole instance = (UserRole) getHibernateTemplate().get(
					"com.kbmc.model.UserRole", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserRole> findByExample(UserRole instance) {
		log.debug("finding UserRole instance by example");
		try {
			List<UserRole> results = getHibernateTemplate().findByExample(
					instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserRole> findByProperty(String propertyName, Object value) {
		log.debug("finding UserRole instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from UserRole as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<UserRole> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<UserRole> findByRoleId(Object roleId) {
		return findByProperty(ROLE_ID, roleId);
	}

	@SuppressWarnings("unchecked")
	public List<UserRole> findAll() {
		log.debug("finding all UserRole instances");
		try {
			String queryString = "from UserRole";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserRole merge(UserRole detachedInstance) {
		log.debug("merging UserRole instance");
		try {
			UserRole result = (UserRole) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserRole instance) {
		log.debug("attaching dirty UserRole instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserRole instance) {
		log.debug("attaching clean UserRole instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserRoleDAOImpl getFromApplicationContext(
			ApplicationContext ctx) {
		return (UserRoleDAOImpl) ctx.getBean("UserRoleDAO");
	}
}