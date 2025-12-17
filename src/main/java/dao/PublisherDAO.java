package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import dto.PublisherDTO;
import model.Publisher;
import utility.Hibernate;

public class PublisherDAO {

	public static List<PublisherDTO> getList() {
		EntityManager em = Hibernate.getEntityManager();
		List<PublisherDTO> result = null;
		try {
			List<Publisher> publishers = em.createQuery("FROM Publisher", Publisher.class).getResultList();
			result = new ArrayList<PublisherDTO>();
			for (Publisher publisher : publishers) {
				result.add(new PublisherDTO(publisher));
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
	
	public static List<PublisherDTO> getListByName(String name) {
		EntityManager em = Hibernate.getEntityManager();
		List<PublisherDTO> result = null;
		try {
			List<Publisher> publishers = em.createQuery("FROM Publisher WHERE name LIKE '%" + name + "%'", Publisher.class).getResultList();
			result = new ArrayList<PublisherDTO>();
			for (Publisher publisher : publishers) {
				result.add(new PublisherDTO(publisher));
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
	
	public static PublisherDTO find(int id) {
		EntityManager em = Hibernate.getEntityManager();
		PublisherDTO result = null;
		try {
			Publisher publisher = em.find(Publisher.class, id);
			result = new PublisherDTO(publisher);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static Publisher get(int id) {
		EntityManager em = Hibernate.getEntityManager();
		Publisher publisher = null;
		try {
			publisher = em.find(Publisher.class, id);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return publisher;
	}
	
	public static boolean add(Publisher publisher) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.persist(publisher);
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
			Publisher publisher = em.find(Publisher.class, id);
			em.remove(publisher);
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
	
	public static boolean update(Publisher publisher) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.merge(publisher);
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
