import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.codec.digest.DigestUtils;

import dao.UserDAO;
import model.User;
import utility.Hibernate;

public class Test {

	public static void main(String[] args) {
		EntityManager em = Hibernate.getEntityManager();
		List<User> users = em.createQuery("FROM User", User.class).getResultList();
		for (User user : users) {
			String password = DigestUtils.sha256Hex("123" + user.getEmail());
			//user.setPassword(password);
			//UserDAO.update(user);
			System.out.println(String.format("{\n\tUser: %s,\n\tPassword: %s \n}\n", user.getFullname(), user.getPassword()));
		}
		em.close();
 	}

}
