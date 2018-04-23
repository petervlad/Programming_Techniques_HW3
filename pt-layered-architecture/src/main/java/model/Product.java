package model;

public class Product {
	private int id;
	private String name;
	private int unit_price;
	private int quantity;
	
	public Product(int id, String name, int unit_price, int quantity){
		this.id = id;
		this.name = name;
		this.unit_price = unit_price;
		this.quantity = quantity;
	}
	public Product(String name, int unit_price, int quantity){
		this.name = name;
		this.unit_price = unit_price;
		this.quantity = quantity;
	}
	public void setId(int id){
		this.id = id;
	}
	public void setUnitPrice(int unit_price){
		this.unit_price = unit_price;
	}
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	public void setName(String name){
		this.name = name;
	}
	public int getId(){
		return this.id;
	}
	public int getUnitPrice(){
		return this.unit_price;
	}
	public int getQuantity(){
		return this.quantity;
	}
	public String getName(){
		return this.name;
	}
}
