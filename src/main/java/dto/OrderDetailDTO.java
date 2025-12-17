package dto;

import java.io.Serializable;

import model.OrderDetail;

public class OrderDetailDTO  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int orderId;
	private int bookId;
	private int quantity;
	private double price;
	
	public OrderDetailDTO() {
		this.orderId = -1;
		this.bookId = -1;
		this.quantity = -1;
		this.price = -1;
	}
	
	public OrderDetailDTO(OrderDetail detail) {
		this.orderId = detail.getOrder().getId();
		this.bookId = detail.getBook().getId();
		this.quantity = detail.getQuantity();
		this.price = detail.getPrice();
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
