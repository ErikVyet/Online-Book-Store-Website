package utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Hibernate {
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Online Book Store Website");

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
}
