package dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import model.Cart;
import model.CartItem;
import model.CartStatus;

public class CartDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int userId;
	private Date created;
	private CartStatus status;
	private List<CartItemDTO> items;
	
	public CartDTO(int userId, Date created, CartStatus status, List<CartItemDTO> items) {
		super();
		this.userId = userId;
		this.created = created;
		this.status = status;
		this.items = items;
	}

	public CartDTO(Cart cart) {
		this.id = cart.getId();
		this.userId = cart.getUser().getId();
		this.created = cart.getCreated();
		this.status = cart.getStatus();
		this.items = new ArrayList<CartItemDTO>();
		for (CartItem item : cart.getItems()) {
			this.items.add(new CartItemDTO(item));
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCreated() {
		return created;
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

	public List<CartItemDTO> getItems() {
		return items;
	}

	public void setItems(List<CartItemDTO> items) {
		this.items = items;
	}
	
}
