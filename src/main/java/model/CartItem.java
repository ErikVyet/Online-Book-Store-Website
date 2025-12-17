package model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "cartitem")
public class CartItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CartItemId id;
	
	private int quantity;
	
	@MapsId("cartId")
	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	@MapsId("bookId")
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	public CartItem() {
		super();
		this.id = new CartItemId();
		this.cart = null;
		this.book = null;
		this.quantity = 0;
	}

	public CartItem(int quantity, Cart cart, Book book) {
		super();
		this.id = new CartItemId();
		this.quantity = quantity;
		this.cart = cart;
		this.book = book;
	}

	public CartItemId getId() {
		return id;
	}

	public void setId(CartItemId id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "CartItem [quantity=" + quantity + ", cart=" + cart.getId() + ", book=" + book.getTitle() + "]";
	}
	
}
