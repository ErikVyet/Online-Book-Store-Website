package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import dto.AuthorDTO;
import model.Author;
import utility.Hibernate;

public class AuthorDAO {
	
	
	public static List<AuthorDTO> getList() {
		EntityManager em = Hibernate.getEntityManager();
		List<AuthorDTO> result = null;
		try {
			List<Author> authors = em.createQuery("FROM Author", Author.class).getResultList();
			result = new ArrayList<AuthorDTO>();
			for (Author author : authors) {
				result.add(new AuthorDTO(author));
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
	
	public static List<AuthorDTO> getListByName(String name) {
		EntityManager em = Hibernate.getEntityManager();
		List<AuthorDTO> result = null;
		try {
			List<Author> authors = em.createQuery("FROM Author WHERE name LIKE '%" + name + "%'", Author.class).getResultList();
			result = new ArrayList<AuthorDTO>();
			for(Author author : authors) {
				result.add(new AuthorDTO(author));
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
	
	public static AuthorDTO find(int id) {
		EntityManager em = Hibernate.getEntityManager();
		AuthorDTO result = null;
		try {
			Author author = em.find(Author.class, id);
			result = new AuthorDTO(author);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static Author get(int id) {
		EntityManager em = Hibernate.getEntityManager();
		Author author = null;
		try {
			author = em.find(Author.class, id);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return author;
	}
	
	public static boolean add(Author author) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.persist(author);
			trans.commit();
		}
		catch (Exception error) {
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
			Author author = em.find(Author.class, id);
			trans.begin();
			em.remove(author);
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
	
	public static boolean update(Author author) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.merge(author);
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
