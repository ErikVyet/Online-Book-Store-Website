package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date created;
	
	@Enumerated(EnumType.STRING)
	private CartStatus status;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	private List<CartItem> items;

	public Cart() {
		super();
		this.created = null;
		this.status = CartStatus.available;
		this.user = null;
		this.items = new ArrayList<CartItem>();
	}   
	
	public Cart(Date created, CartStatus status, User user, List<CartItem> items) {
		super();
		this.created = created;
		this.status = status;
		this.user = user;
		this.items = items;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public CartStatus getStatus() {
		return status;
	}

	public void setStatus(CartStatus status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", created=" + created + ", status=" + status.toString() + ", user=" + user.getUsername() + "\n\titems=" + items + "\n]";
	}
	
	
   
}
