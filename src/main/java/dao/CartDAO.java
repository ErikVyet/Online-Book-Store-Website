package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import dto.CartDTO;
import dto.CartItemDTO;
import model.Book;
import model.Cart;
import model.CartItem;
import model.User;
import utility.Hibernate;

public class CartDAO {

	public static List<CartDTO> getList() {
		EntityManager em = Hibernate.getEntityManager();
		List<CartDTO> result = null;
		try {
			List<Cart> carts = em.createQuery("FROM Cart", Cart.class).getResultList();
			result = new ArrayList<CartDTO>();
			for (Cart cart : carts) {
				result.add(new CartDTO(cart));
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
	
	public static List<CartDTO> getListForUser(int userId) {
		EntityManager em = Hibernate.getEntityManager();
		List<CartDTO> result = null;
		try {
			TypedQuery<Cart> query = em.createQuery("FROM Cart WHERE user_id = ?1", Cart.class);
			query.setParameter(1, userId);
			List<Cart> carts = query.getResultList();
			result = new ArrayList<CartDTO>();
			for (Cart cart : carts) {
				result.add(new CartDTO(cart));
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
	
	public static Cart get(int id) {
		EntityManager em = Hibernate.getEntityManager();
		Cart cart = null;
		try {
			cart = em.find(Cart.class, id);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return cart;
	}
	
	public static CartDTO find(int id) {
		EntityManager em = Hibernate.getEntityManager();
		CartDTO result = null;
		try {
			Cart cart = em.find(Cart.class, id);
			result = new CartDTO(cart);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static boolean add(Cart cart) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.persist(cart);
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
	
	public static boolean add(CartDTO cart) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			Cart data = new Cart();
			data.setCreated(cart.getCreated());
			data.setStatus(cart.getStatus());
			data.setUser(em.find(User.class, cart.getUserId()));
			if (cart.getItems() != null && !cart.getItems().isEmpty()) {
				for (CartItemDTO dto : cart.getItems()) {
					CartItem item = new CartItem();
					item.setCart(data);
					item.setBook(em.find(Book.class, dto.getBookId()));
					item.setQuantity(dto.getQuantity());
					
					data.getItems().add(item);
				}
			}
			else {
				data.setItems(new ArrayList<CartItem>());
			}
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
	
	public static boolean remove(int id) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			Cart cart = em.find(Cart.class, id);
			em.remove(cart);
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
	
	public static boolean update(Cart cart) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.merge(cart);
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
