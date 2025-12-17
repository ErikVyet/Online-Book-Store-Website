package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date date;
	private double total;
	private String address;
	
	@Enumerated(EnumType.STRING)
	private Payment payment;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderDetail> details;
	
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
	private Shipping shipping;
	
	public Order() {
		super();
		this.date = null;
		this.total = 0;
		this.payment = null;
		this.address = null;
		this.status = null;
		this.user = null;
		this.details = new ArrayList<OrderDetail>();
		this.shipping = null;
	}

	public Order(Date date, double total, Payment payment, String address, OrderStatus status, User user,
			List<OrderDetail> details, Shipping shipping) {
		super();
		this.date = date;
		this.total = total;
		this.payment = payment;
		this.address = address;
		this.status = status;
		this.user = user;
		this.details = details;
		this.shipping = shipping;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", total=" + total + ", payment=" + payment + ", address="
				+ address + ", status=" + status + ", user=" + user.getUsername() + ", shipping=" + shipping.getId() 
				+ "\n\tdetails=" + details + "\n]";
	}
	
}
