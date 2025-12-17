package dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import model.Cart;
import model.Order;
import model.Review;
import model.Role;
import model.User;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String username;
	private String password;
	private String fullname;
	private String email;
	private String phone;
	private String address;
	private Role role;
	private Date created;
	private List<CartDTO> carts;
	private List<ReviewDTO> reviews;
	private List<OrderDTO> orders;
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.fullname = user.getFullname();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.address = user.getAddress();
		this.role = user.getRole();
		this.created = user.getCreated();
		this.carts = new ArrayList<CartDTO>();
		for (Cart cart : user.getCarts()) {
			this.carts.add(new CartDTO(cart));
		}
		this.reviews = new ArrayList<ReviewDTO>();
		for (Review review : user.getReviews()) {
			this.reviews.add(new ReviewDTO(review));
		}
		this.orders = new ArrayList<OrderDTO>();
		for (Order order : user.getOrders()) {
			this.orders.add(new OrderDTO(order));
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<CartDTO> getCarts() {
		return carts;
	}

	public void setCarts(List<CartDTO> carts) {
		this.carts = carts;
	}

	public List<ReviewDTO> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewDTO> reviews) {
		this.reviews = reviews;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}

}
