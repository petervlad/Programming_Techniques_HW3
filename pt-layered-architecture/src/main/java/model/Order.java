package model;

public class Order {
	private int id;
	private int price;
	private int customerId;
	public Order(int id, int price, int customerId){
		this.id = id;
		this.price = price;
		this.customerId = customerId;
	}
	public Order(int price, int customerId){
		this.price = price;
		this.customerId = customerId;
	}
	public void setId(int id){
		this.id = id;
	}
	public void setPrice(int price){
		this.price = price;
	}
	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}
	public int getId(){
		return this.id;
	}
	public int getPrice(){
		return this.price;
	}
	public int getCustomerId(){
		return this.customerId;
	}
}
