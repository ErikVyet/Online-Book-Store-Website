package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import dto.ReviewDTO;
import model.Book;
import model.Review;
import model.ReviewId;
import model.User;
import utility.Hibernate;

public class ReviewDAO {

	public static List<ReviewDTO> getList() {
		EntityManager em = Hibernate.getEntityManager();
		List<ReviewDTO> result = null;
		try {
			List<Review> reviews = em.createQuery("FROM Review", Review.class).getResultList();
			result = new ArrayList<ReviewDTO>();
			for (Review review : reviews) {
				result.add(new ReviewDTO(review));
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
	
	public static List<ReviewDTO> getListByBookId(int bookId) {
		EntityManager em = Hibernate.getEntityManager();
		List<ReviewDTO> result = null;
		try {
			TypedQuery<Review> query = em.createQuery("FROM Review WHERE book_id = ?1", Review.class);
			query.setParameter(1, bookId);
			List<Review> reviews = query.getResultList();
			result = new ArrayList<ReviewDTO>();
			for (Review review : reviews) {
				result.add(new ReviewDTO(review));
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
	
	public static ReviewDTO find(ReviewId id) {
		EntityManager em = Hibernate.getEntityManager();
		ReviewDTO result = null;
		try {
			Review review = em.find(Review.class, id);
			result = new ReviewDTO(review);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static boolean add(Review review) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.persist(review);
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
	
	public static boolean remove(ReviewId id) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			Review review = em.find(Review.class, id);
			em.remove(review);
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
	
	public static boolean update(Review review) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			review.setBook(em.find(Book.class, review.getId().getBookId()));
			review.setUser(em.find(User.class, review.getId().getUserId()));
			em.merge(review);
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
