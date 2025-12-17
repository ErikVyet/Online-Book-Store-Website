package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import dto.BookDTO;
import model.Book;
import utility.Hibernate;

public class BookDAO {

	public static List<BookDTO> getList() {
		EntityManager em = Hibernate.getEntityManager();
		List<BookDTO> result = null;
		try {
			List<Book> books = em.createQuery("FROM Book", Book.class).getResultList();
			result = new ArrayList<BookDTO>();
			for (Book book : books) {
				result.add(new BookDTO(book));
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
	
	public static List<BookDTO> getListByTitle(String title) {
		EntityManager em = Hibernate.getEntityManager();
		List<BookDTO> result = null;
		try {
			TypedQuery<Book> query = em.createQuery("FROM Book WHERE title LIKE ?1", Book.class);
			if (title.isEmpty()) {
				query.setParameter(1, "%%");
			}
			else {
				query.setParameter(1, "%" + title + "%");
			}
			
			List<Book> books = query.getResultList();
			result = new ArrayList<BookDTO>();
			for (Book book : books) {
				result.add(new BookDTO(book));
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
	
	public static List<BookDTO> getListByCategory(int categoryId) {
		EntityManager em = Hibernate.getEntityManager();
		List<BookDTO> result = null;
		try {
			TypedQuery<Book> query = em.createQuery("FROM Book WHERE category_id = ?1", Book.class);
			query.setParameter(1, categoryId);
			List<Book> books = query.getResultList();
			result = new ArrayList<BookDTO>();
			for (Book book : books) {
				result.add(new BookDTO(book));
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
	
	public static List<BookDTO> getSortedListById(boolean asc) {
		EntityManager em = Hibernate.getEntityManager();
		List<BookDTO> result = null;
		try {
			List<Book> books = asc ?
				em.createQuery("FROM Book ORDER BY id ASC", Book.class).getResultList() :
				em.createQuery("FROM Book ORDER BY id DESC", Book.class).getResultList();
			result = new ArrayList<BookDTO>();
			for (Book book : books) {
				result.add(new BookDTO(book));
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
	
	public static List<BookDTO> getSortedListById() {
		return getSortedListById(true);
	}
	
	public static List<BookDTO> getSortedListByTitle(boolean asc) {
		EntityManager em = Hibernate.getEntityManager();
		List<BookDTO> result = null;
		try {
			List<Book> books = asc ?
				em.createQuery("FROM Book ORDER BY title ASC", Book.class).getResultList() :
				em.createQuery("FROM Book ORDER BY title DESC", Book.class).getResultList();
			result = new ArrayList<BookDTO>();
			for (Book book : books) {
				result.add(new BookDTO(book));
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
	
	public static List<BookDTO> getSortedListByTitle() {
		return getSortedListByTitle(true);
	}
	
	public static List<BookDTO> getSortedListByPrice(boolean asc) {
		EntityManager em = Hibernate.getEntityManager();
		List<BookDTO> result = null;
		try {
			List<Book> books = asc ?
				em.createQuery("FROM Book ORDER BY price ASC", Book.class).getResultList() :
				em.createQuery("FROM Book ORDER BY price DESC", Book.class).getResultList();
			result = new ArrayList<BookDTO>();
			for (Book book : books) {
				result.add(new BookDTO(book));
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
	
	public static List<BookDTO> getSortedListByPrice(){
		return getSortedListById(true);
	}
	
	public static List<BookDTO> getSortedListByQuantity(boolean asc) {
		EntityManager em = Hibernate.getEntityManager();
		List<BookDTO> result = null;
		try {
			List<Book> books = asc ?
				em.createQuery("FROM Book ORDER BY quantity ASC", Book.class).getResultList() :
				em.createQuery("FROM Book ORDER BY quantity DESC", Book.class).getResultList();
			result = new ArrayList<BookDTO>();
			for (Book book : books) {
				result.add(new BookDTO(book));
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
	
	public static List<BookDTO> getSortedListByQuantity() {
		return getSortedListByQuantity(true);
	}
	
	public static List<BookDTO> getSortedListByRating(boolean asc) {
		EntityManager em = Hibernate.getEntityManager();
		List<BookDTO> result = null;
		try {
			List<Book> books = asc ?
				em.createQuery(
					"SELECT b "
				  + "FROM Book AS b JOIN Review ON id = book_id "
				  + "GROUP BY id "
				  + "ORDER BY AVG(rating)", Book.class).getResultList() :
				em.createQuery(
					"SELECT b "
				  + "FROM Book AS b JOIN Review ON id = book_id "
				  + "GROUP BY id "
				  + "ORDER BY AVG(rating) ", Book.class).getResultList();
			result = new ArrayList<BookDTO>();
			for (Book book : books) {
				result.add(new BookDTO(book));
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
	
	public static List<BookDTO> getSortedListByRating() {
		return getSortedListByRating(true);
	}
	
	public static List<BookDTO> getSortedList(String content, boolean asc) {
		EntityManager em = Hibernate.getEntityManager();
		List<BookDTO> result = new ArrayList<BookDTO>();
		try {
			TypedQuery<Book> query = asc ?
				em.createQuery("FROM Book ORDER BY " + content + " ASC", Book.class) :
				em.createQuery("FROM Book ORDER BY " + content + " DESC", Book.class);
			List<Book> books = query.getResultList();
			for (Book book : books) {
				BookDTO bookDTO = new BookDTO(book);
				result.add(bookDTO);
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
	
	public static BookDTO getLatestBook() {
		EntityManager em = Hibernate.getEntityManager();
		BookDTO result = null;
		try {
			Book book = em.createQuery("FROM Book ORDER BY publication DESC", Book.class).getResultList().get(0);
			result = new BookDTO(book);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static Book get(int id) {
		EntityManager em = Hibernate.getEntityManager();
		Book result = null;
		try {
			result = em.find(Book.class, id);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static BookDTO find(int id) {
		EntityManager em = Hibernate.getEntityManager();
		BookDTO result = null;
		try {
			Book book = em.find(Book.class, id);
			result = new BookDTO(book);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static boolean add(Book book) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.persist(book);
			trans.commit();
		}
		catch(Exception error) {
			flag = false;
			trans.rollback();
			error.printStackTrace();
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
			Book book = em.find(Book.class, id);
			trans.begin();
			em.remove(book);
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
	
	public static boolean update(Book book) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.merge(book);
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
