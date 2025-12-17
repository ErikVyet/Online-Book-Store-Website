package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import dto.CartItemDTO;
import model.Book;
import model.Cart;
import model.CartItem;
import model.CartItemId;
import utility.Hibernate;

public class CartItemDAO {

	public static List<CartItemDTO> getList() {
		EntityManager em = Hibernate.getEntityManager();
		List<CartItemDTO> result = null;
		try {
			List<CartItem> items = em.createQuery("FROM CartItem", CartItem.class).getResultList();
			result = new ArrayList<CartItemDTO>();
			for (CartItem item : items) {
				result.add(new CartItemDTO(item));
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
	
	public static List<CartItemDTO> getListForCart(int cartId) {
		EntityManager em = Hibernate.getEntityManager();
		List<CartItemDTO> result = null;
		try {
			TypedQuery<CartItem> query = em.createQuery("FROM CartItem WHERE cart_id = ?1", CartItem.class);
			query.setParameter(1, cartId);
			List<CartItem> items = query.getResultList();
			result = new ArrayList<CartItemDTO>();
			for (CartItem item : items) {
				result.add(new CartItemDTO(item));
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
	
	public static CartItemDTO find(CartItemId id) {
		EntityManager em = Hibernate.getEntityManager();
		CartItemDTO result = null;
		try {
			CartItem item = em.find(CartItem.class, id);
			result = new CartItemDTO(item);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static boolean add(CartItem item) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			Book book = em.find(Book.class, item.getBook().getId());
			Cart cart = em.find(Cart.class, item.getCart().getId());
			item.setBook(book);
			item.setCart(cart);
			em.persist(item);
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
	
	public static boolean add(CartItemDTO item) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			CartItem data = new CartItem();
			data.setId(new CartItemId(item.getCartId(), item.getBookId()));
			data.setQuantity(item.getQuantity());
			data.setCart(em.find(Cart.class, item.getCartId()));
			data.setBook(em.find(Book.class, item.getBookId()));
			em.persist(data);
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
	
	public static boolean remove(CartItemId id) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			CartItem item = em.find(CartItem.class, id);
			em.remove(item);
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
	
	public static boolean update(CartItem item) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			item.setCart(em.find(Cart.class, item.getId().getCartId()));
			item.setBook(em.find(Book.class, item.getId().getBookId()));
			em.merge(item);
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
