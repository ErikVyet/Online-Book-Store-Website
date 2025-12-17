package dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import model.Order;
import model.OrderDetail;
import model.OrderStatus;
import model.Payment;

public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int userId;
	private Date date;
	private double total;
	private OrderStatus status;
	private Payment payment;
	private String address;
	private List<OrderDetailDTO> details;
	private ShippingDTO shipping;
	
	public OrderDTO() {
		this.id = -1;
		this.userId = -1;
		this.date = null;
		this.total = -1;
		this.status = null;
		this.payment = null;
		this.address = null;
		this.details = new ArrayList<OrderDetailDTO>();
		this.shipping = null;
	}
	
	public OrderDTO(Order order) {
		this.id = order.getId();
		this.userId = order.getUser().getId();
		this.date = order.getDate();
		this.total = order.getTotal();
		this.status = order.getStatus();
		this.payment = order.getPayment();
		this.address = order.getAddress();
		this.details = new ArrayList<OrderDetailDTO>();
		for (OrderDetail detail : order.getDetails()) {
			this.details.add(new OrderDetailDTO(detail));
		}
		this.shipping = new ShippingDTO(order.getShipping());
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

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
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

	public List<OrderDetailDTO> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetailDTO> details) {
		this.details = details;
	}

	public ShippingDTO getShipping() {
		return shipping;
	}

	public void setShipping(ShippingDTO shipping) {
		this.shipping = shipping;
	}
	
}
