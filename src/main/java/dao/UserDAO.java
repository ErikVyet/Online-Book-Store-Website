package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import dto.UserDTO;
import model.User;
import utility.Hibernate;

public class UserDAO {
	
	public static List<UserDTO> getList() {
		EntityManager em = Hibernate.getEntityManager();
		List<UserDTO> result = null;
		try {
			List<User> users = em.createQuery("FROM User", User.class).getResultList();
			result = new ArrayList<UserDTO>();
			for (User user : users) {
				result.add(new UserDTO(user));
			}
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static UserDTO getUser(int id) {
		EntityManager em = Hibernate.getEntityManager();
		TypedQuery<User> query = em.createQuery("FROM User WHERE id = ?1", User.class);
		query.setParameter(1, id);
		UserDTO result = null;
		try {
			User user = query.getSingleResult();
			result = new UserDTO(user);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static UserDTO getUser(String username, String password, String email) {
		EntityManager em = Hibernate.getEntityManager();
		TypedQuery<User> query = em.createQuery("FROM User WHERE username = ?1 AND password = ?2 AND email = ?3", User.class);
		query.setParameter(1, username);
		query.setParameter(2, password);
		query.setParameter(3, email);
		UserDTO result = null;
		try {
			User user = query.getSingleResult();
			result = new UserDTO(user);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static User find(int id) {
		EntityManager em = Hibernate.getEntityManager();
		User user = null;
		try {
			user = em.find(User.class, id);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return user;
	}

	public static boolean add(User user) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.persist(user);
			trans.commit();
		}
		catch(Exception error) {
			flag = false;
			trans.rollback();
		}
		finally {
			em.close();
		}
		return flag;
	}
	
	public static boolean remove(int id) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			User user = em.find(User.class, id);
			em.remove(user);
			trans.commit();
		}
		catch(Exception error) {
			flag = false;
			trans.rollback();
		}
		finally {
			em.close();
		}
		return flag;
	}
	
	public static boolean update(User user) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.merge(user);
			trans.commit();
		}
		catch(Exception error) {
			flag = false;
			trans.rollback();
		}
		finally {
			em.close();
		}
		return flag;
	}
	
	public static boolean updatePassword(String email, String password) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			TypedQuery<User> query = em.createQuery("FROM User WHERE email = ?1", User.class);
			query.setParameter(1, email);
			User user = query.getSingleResult();
			user.setPassword(password);
			em.merge(user);
			trans.commit();
		}
		catch(Exception error) {
			flag = false;
			trans.rollback();
		}
		finally {
			em.close();
		}
		return flag;
	}
	
}
