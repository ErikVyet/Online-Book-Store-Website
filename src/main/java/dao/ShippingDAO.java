package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import dto.ShippingDTO;
import model.Shipping;
import model.ShippingStatus;
import utility.Hibernate;

public class ShippingDAO {
	
	public static List<ShippingDTO> getList() {
		EntityManager em = Hibernate.getEntityManager();
		List<ShippingDTO> result = null;
		try {
			List<Shipping> shippings = em.createQuery("FROM Shipping", Shipping.class).getResultList();
			result = new ArrayList<ShippingDTO>();
			for (Shipping shipping : shippings) {
				result.add(new ShippingDTO(shipping));
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
	
	public static List<ShippingDTO> getDeliveredShippingList() {
		EntityManager em = Hibernate.getEntityManager();
		List<ShippingDTO> result = null;
		try {
			TypedQuery<Shipping> query = em.createQuery("FROM Shipping WHERE status = ?1", Shipping.class);
			query.setParameter(1, ShippingStatus.Delivered);
			List<Shipping> shippings = query.getResultList();
			result = new ArrayList<ShippingDTO>();
			for (Shipping shipping : shippings) {
				result.add(new ShippingDTO(shipping));
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
	
	public static ShippingDTO find(int id) {
		EntityManager em = Hibernate.getEntityManager();
		ShippingDTO result = null;
		try {
			Shipping shipping = em.find(Shipping.class, id);
			result = new ShippingDTO(shipping);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return result;
	}
	
	public static Shipping get(int id) {
		EntityManager em = Hibernate.getEntityManager();
		Shipping shipping = null;
		try {
			shipping = em.find(Shipping.class, id);
		}
		catch(Exception error) {
			error.printStackTrace();
		}
		finally {
			em.close();
		}
		return shipping;
	}
	
	public static boolean add(Shipping shipping) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.persist(shipping);
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
			Shipping shipping = em.find(Shipping.class, id);
			em.remove(shipping);
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
	
	public static boolean update(Shipping shipping) {
		EntityManager em = Hibernate.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		boolean flag = true;
		try {
			trans.begin();
			em.merge(shipping);
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
