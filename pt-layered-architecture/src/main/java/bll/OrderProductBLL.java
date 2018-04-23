package bll;

import dao.OrderProductDAO;
import model.OrderProduct;
import model.Product;

import java.util.ArrayList;
import java.util.NoSuchElementException;


public class OrderProductBLL {
	public int insertOrderProduct(OrderProduct orderProduct){
		return OrderProductDAO.insert(orderProduct);
	}
	public OrderProduct findById(int id){
		OrderProduct orderProduct = OrderProductDAO.findById(id);
		if(orderProduct == null){
			throw new NoSuchElementException("Product with id "+id+" does not exist!");
		}
		return orderProduct;
	}
	public ArrayList<OrderProduct> findAll(){
		ArrayList<OrderProduct> products = OrderProductDAO.findAll();
		if(products.isEmpty()){
			throw new NoSuchElementException("There are no available products");
		}
		return products;
	}
	public void order(int orderId, ArrayList<OrderProduct> orderProducts){
		System.out.println(orderId);
		for(OrderProduct orderProduct : orderProducts){
			orderProduct.setOrderId(orderId);
			System.out.println(orderProduct.getOrderId());
			this.insertOrderProduct(orderProduct);
		}
	}
}
