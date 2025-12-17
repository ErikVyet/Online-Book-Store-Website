package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import dto.OrderDTO;
import dto.OrderDetailDTO;
import model.Book;
import model.Order;
import model.OrderDetail;
import model.OrderDetailId;
import model.OrderStatus;
import model.Shipping;
import model.ShippingStatus;
import model.User;
import utility.Hibernate;

public class OrderDAO {

	public static List<OrderDTO> getList() {
		EntityManager em = Hibernate.getEntityManager();
		List<OrderDTO> result = null;
		try {
			List<Order> orders = em.createQuery("FROM Order", Order.class).getResultList();
			result = new ArrayList<OrderDTO>();
			for (Order order : orders) {
				result.add(new OrderDTO(order));
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
	
	public static List<OrderDTO> getListForUser(int userId) {
		EntityManager em = Hibernate.getEntityManager();
		List<OrderDTO> result = null;
		try {
			TypedQuery<Order> query = em.createQuery("FROM Order WHERE user_id = ?1", Order.class);
			query.setParameter(1, userId);
			List<Order> orders = query.getResultList();
			result = new ArrayList<OrderDTO>();
			for (Order order : orders) {
				result.add(new OrderDTO(order));
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
	
	public static Order get(int id) {
		EntityManager em = Hibernate.getEntityManager();
		Order order = null;
		try {
			order = em.find(Order.class, id);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return order;
	}
	
	public static OrderDTO find(int id) {
		EntityManager em = Hibernate.getEntityManager();
		OrderDTO result = null;
		try {
			Order order = em.find(Order.class, id);
			result = new OrderDTO(order);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static boolean add(OrderDTO orderDTO) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			Order order = new Order();
			User user = em.find(User.class, orderDTO.getUserId());
			order.setUser(user);
			order.setDate(orderDTO.getDate());
			order.setTotal(orderDTO.getTotal());
			order.setStatus(orderDTO.getStatus());
			order.setPayment(orderDTO.getPayment());
			order.setAddress(user.getAddress());
			for(OrderDetailDTO detailDTO : orderDTO.getDetails()) {
				Book book = em.find(Book.class, detailDTO.getBookId());
				OrderDetail detail = new OrderDetail();
				detail.setId(new OrderDetailId());
				detail.setOrder(order);
				detail.setBook(book);
				detail.setQuantity(detailDTO.getQuantity());
				book.setQuantity(book.getQuantity() - detailDTO.getQuantity());
				em.merge(book);
				detail.setPrice(book.getPrice());
				order.getDetails().add(detail);
			}
			Shipping shipping = new Shipping();
			shipping.setOrder(order);
			shipping.setMethod(orderDTO.getShipping().getMethod());
			shipping.setStatus(ShippingStatus.Pending);
			order.setShipping(shipping);
			
			trans.begin();
			em.persist(order);
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
	
	public static boolean add(Order order) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.persist(order);
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
			trans.begin();
			Order order = em.find(Order.class, id);
			em.persist(order);
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
	
	public static boolean cancel(int id) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			Order order = em.find(Order.class, id);
			order.setStatus(OrderStatus.Cancelled);
			order.getShipping().setStatus(ShippingStatus.Cancelled);
			for (OrderDetail detail : order.getDetails()) {
				Book book = detail.getBook();
				book.setQuantity(book.getQuantity() + detail.getQuantity());
				em.merge(book);
			}
			em.merge(order);
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
	
	public static boolean update(Order order) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.merge(order);
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
