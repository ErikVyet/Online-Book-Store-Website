package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import dto.CategoryDTO;
import model.Category;
import utility.Hibernate;

public class CategoryDAO {

	public static List<CategoryDTO> getList() {
		EntityManager em = Hibernate.getEntityManager();
		List<CategoryDTO> result = null;
		try {
			List<Category> categories = em.createQuery("FROM Category", Category.class).getResultList();
			result = new ArrayList<CategoryDTO>();
			for (Category category : categories) {
				result.add(new CategoryDTO(category));
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
	
	public static List<CategoryDTO> getListByName(String name) {
		EntityManager em = Hibernate.getEntityManager();
		List<CategoryDTO> result = null;
		try {
			List<Category> categories = em.createQuery("FROM Category WHERE name LIKE '%" + name + "%'", Category.class).getResultList();
			result = new ArrayList<CategoryDTO>();
			for (Category category : categories) {
				result.add(new CategoryDTO(category));
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
	
	public static CategoryDTO find(int id) {
		EntityManager em = Hibernate.getEntityManager();
		CategoryDTO result = null;
		try {
			Category category = em.find(Category.class, id);
			result = new CategoryDTO(category);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static Category get(int id) {
		EntityManager em = Hibernate.getEntityManager();
		Category category = null;
		try {
			category = em.find(Category.class, id);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return category;
	}
	
	public static boolean add(Category category) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.persist(category);
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
			Category category = em.find(Category.class, id);
			em.remove(category);
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
	
	public static boolean update(Category category) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.merge(category);
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
