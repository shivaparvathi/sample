package com.kbmc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.kbmc.dao.UserDAO;
import com.kbmc.model.User;

/**
 * A data access object (DAO) providing persistence and search support for User
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.kbmc.model.User
 * @author ZONE24X7
 */

public class UserDAOImpl extends HibernateDaoSupport implements UserDAO {
	private static final Logger log = Logger.getLogger(UserDAOImpl.class);
	// property constants
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String FIRSTNAME = "firstname";
	public static final String LASTNAME = "lastname";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String RESETCODE = "resetcode";

	protected void initDao() {
		// do nothing
	}

	public void save(User transientInstance) {
		log.debug("saving User instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(User persistentInstance) {
		log.debug("deleting User instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public User findById(Long id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getHibernateTemplate().get(
					"com.kbmc.model.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> findByExample(User instance) {
		log.debug("finding User instance by example");
		try {
			List<User> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> findByProperty(String propertyName, Object value) {
		log.debug("finding User instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from User as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<User> findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	public List<User> findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List<User> findByFirstname(Object firstname) {
		return findByProperty(FIRSTNAME, firstname);
	}

	public List<User> findByLastname(Object lastname) {
		return findByProperty(LASTNAME, lastname);
	}

	public List<User> findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List<User> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List<User> findByResetcode(Object resetcode) {
		return findByProperty(RESETCODE, resetcode);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		log.debug("finding all User instances");
		try {
			String queryString = "from User";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public User merge(User detachedInstance) {
		log.debug("merging User instance");
		try {
			User result = (User) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(User instance) {
		log.debug("attaching dirty User instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(User instance) {
		log.debug("attaching clean User instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public int existUser(String username) {
		List<User> list;
		try {
			String queryString = "from User where username=?";
			list = getHibernateTemplate().find(queryString, username);
		} catch (RuntimeException re) {
			log.error("User exist failed", re);
			throw re;
		}
		return list.size();
	}

	@SuppressWarnings("unchecked")
	public int existResetCode(String resetCode) {
		List<User> list;
		try {
			String queryString = "from User where resetcode=?";
			list = getHibernateTemplate().find(queryString, resetCode);
		} catch (RuntimeException re) {
			log.error("resetcode not available for user", re);
			throw re;
		}
		return list.size();
	}

	@SuppressWarnings("unchecked")
	public List<User> getUserEnabled() {
		log.debug("finding all User instances");
		try {
			String queryString = "from User order by id";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find enabled true", re);
			throw re;
		}
	}

	public static UserDAOImpl getFromApplicationContext(ApplicationContext ctx) {
		return (UserDAOImpl) ctx.getBean("UserDAO");
	}

	@Override
	public List<User> search(String search) {
		log.debug("finding all User instances");
		try {
			String queryString = "from User user WHERE lower(user.username) LIKE lower('%"
					+ search + "%')order by id ";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("finding users error", re);
			throw re;
		}
	}
}
