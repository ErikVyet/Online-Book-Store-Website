package model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "shipping")
public class Shipping implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date shipped_date;
	
	@Enumerated(EnumType.STRING)
	private ShippingMethod method;
	
	@Enumerated(EnumType.STRING)
	private ShippingStatus status;
	
	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	public Shipping() {
		super();
		this.method = null;
		this.shipped_date = null;
		this.status = null;
		this.order = null;
	}

	public Shipping(ShippingMethod method, Date shipped_date, ShippingStatus status, Order order) {
		super();
		this.method = method;
		this.shipped_date = shipped_date;
		this.status = status;
		this.order = order;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ShippingMethod getMethod() {
		return method;
	}

	public void setMethod(ShippingMethod method) {
		this.method = method;
	}

	public Date getShipped_date() {
		return shipped_date;
	}

	public void setShipped_date(Date shipped_date) {
		this.shipped_date = shipped_date;
	}

	public ShippingStatus getStatus() {
		return status;
	}

	public void setStatus(ShippingStatus status) {
		this.status = status;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Shipping [id=" + id + ", method=" + method + ", shipped_date=" + shipped_date + ", status=" + status
				+ ", order=" + order.getId() + "]";
	}
   
}
