package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "orderdetail")
public class OrderDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrderDetailId id;
	private int quantity;
	private double price;
	
	@MapsId("orderId")
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	@MapsId("bookId")
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	public OrderDetail() {
		super();
		this.quantity = 0;
		this.price = 0;
		this.order = null;
		this.book = null;
	}

	public OrderDetail(int quantity, double price, Order order, Book book) {
		super();
		this.quantity = quantity;
		this.price = price;
		this.order = order;
		this.book = book;
	}

	public OrderDetailId getId() {
		return id;
	}

	public void setId(OrderDetailId id) {
		this.id = id;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "OrderDetail [id=" + id + ", quantity=" + quantity + ", price=" + price + ", order=" + order.getId() + ", book="
				+ book.getTitle() + "]";
	}
   
}
