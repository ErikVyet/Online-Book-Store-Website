package dto;

import java.io.Serializable;

import model.CartItem;

public class CartItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int cartId;
	private int bookId;
	private int quantity;
	
	public CartItemDTO(int cartId, int bookId, int quantity) {
		super();
		this.cartId = cartId;
		this.bookId = bookId;
		this.quantity = quantity;
	}

	public CartItemDTO(CartItem item) {
		this.cartId = item.getCart().getId();
		this.bookId = item.getBook().getId();
		this.quantity = item.getQuantity();
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
