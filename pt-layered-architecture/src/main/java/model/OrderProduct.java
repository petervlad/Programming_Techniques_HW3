package model;

public class OrderProduct {
	private int id;
	private int orderId;
	private int productId;
	private int quantity;
	
	public OrderProduct(int id, int orderId, int productId, int quantity){
		this.id = id;
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}
	public OrderProduct(int orderId, int productId, int quantity){
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}
	public int getId(){
		return this.id;
	}
	public int getOrderId(){
		return this.orderId;
	}
	public int getProductId(){
		return this.productId;
	}
	public int getQuantity(){
		return this.quantity;
	}
	public void setId(int id){
		this.id = id;
	}
	public void setOrderId(int orderId){
		this.orderId = orderId;
	}
	public void setProductId(int productId){
		this.productId = productId;
	}
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
}
