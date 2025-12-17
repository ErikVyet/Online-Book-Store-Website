package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import dto.OrderDetailDTO;
import model.OrderDetail;
import model.OrderDetailId;
import utility.Hibernate;

public class OrderDetailDAO {

	public static List<OrderDetailDTO> getList() {
		EntityManager em = Hibernate.getEntityManager();
		List<OrderDetailDTO> result = null;
		try {
			List<OrderDetail> details = em.createQuery("FROM OrderDetail", OrderDetail.class).getResultList();
			result = new ArrayList<OrderDetailDTO>();
			for (OrderDetail detail : details) {
				result.add(new OrderDetailDTO(detail));
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
	
	public static List<OrderDetailDTO> getListForOrder(int orderId) {
		EntityManager em = Hibernate.getEntityManager();
		List<OrderDetailDTO> result = null;
		try {
			TypedQuery<OrderDetail> query = em.createQuery("FROM OrderDetail WHERE order_id = ?1", OrderDetail.class);
			query.setParameter(1, orderId);
			List<OrderDetail> details = query.getResultList();
			result = new ArrayList<OrderDetailDTO>();
			for (OrderDetail detail : details) {
				result.add(new OrderDetailDTO(detail));
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
	
	public static boolean add(OrderDetail detail) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.persist(detail);
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
	
	public static boolean remove(OrderDetailId id) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			OrderDetail detail = em.find(OrderDetail.class, id);
			em.remove(detail);
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
	
	public static boolean update(OrderDetail detail) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.merge(detail);
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
