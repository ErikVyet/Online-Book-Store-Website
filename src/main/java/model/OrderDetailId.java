package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class OrderDetailId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int orderId;
	private int bookId;
	
	public OrderDetailId() {
		super();
	}

	public OrderDetailId(int orderId, int bookId) {
		super();
		this.orderId = orderId;
		this.bookId = bookId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "OrderDetailId [orderId=" + orderId + ", bookId=" + bookId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId, orderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetailId other = (OrderDetailId) obj;
		return bookId == other.bookId && orderId == other.orderId;
	}

}
