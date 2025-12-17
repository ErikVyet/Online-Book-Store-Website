package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class CartItemId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer cartId;
	private Integer bookId;
	
	public CartItemId() {
		super();
	}

	public CartItemId(Integer cartId, Integer bookId) {
		super();
		this.cartId = cartId;
		this.bookId = bookId;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId, cartId);
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    CartItemId other = (CartItemId) obj;
	    return Objects.equals(cartId, other.cartId)
	        && Objects.equals(bookId, other.bookId);
	}

	
}
