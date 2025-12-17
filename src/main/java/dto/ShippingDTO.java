package dto;

import java.io.Serializable;
import java.sql.Date;

import model.Shipping;
import model.ShippingMethod;
import model.ShippingStatus;

public class ShippingDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int orderId;
	private ShippingMethod method;
	private ShippingStatus status;
	private Date shipped_date;
	
	public ShippingDTO() {
		this.id = -1;
		this.orderId = -1;
		this.method = null;
		this.status = null;
		this.shipped_date = null;
	}
	
	public ShippingDTO(Shipping shipping) {
		this.id = shipping.getId();
		this.orderId = shipping.getOrder().getId();
		this.method = shipping.getMethod();
		this.status = shipping.getStatus();
		this.shipped_date = shipping.getShipped_date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public ShippingMethod getMethod() {
		return method;
	}

	public void setMethod(ShippingMethod method) {
		this.method = method;
	}

	public ShippingStatus getStatus() {
		return status;
	}

	public void setStatus(ShippingStatus status) {
		this.status = status;
	}

	public Date getShipped_date() {
		return shipped_date;
	}

	public void setShipped_date(Date shipped_date) {
		this.shipped_date = shipped_date;
	}

}
