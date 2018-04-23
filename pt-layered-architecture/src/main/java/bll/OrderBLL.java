package bll;

import dao.OrderDAO;
import model.Order;

import java.util.ArrayList;
import java.util.NoSuchElementException;


public class OrderBLL {
	public int insertOrder(Order order){
		return OrderDAO.insert(order);
	}
	public Order findByName(int customerId){
		Order order = OrderDAO.findByCustomer(customerId);
		if(order == null){
			throw new NoSuchElementException("Order with customerId "+customerId+"does not exist!");
		}
		return order;
	}
	public Order findById(int id){
		Order order = OrderDAO.findById(id);
		if(order == null){
			throw new NoSuchElementException("Order with id "+id+" does not exist!");
		}
		return order;
	}
	public ArrayList<Order> findAll(){
		ArrayList<Order> orders = OrderDAO.findAll();
		if(orders.isEmpty()){
			throw new NoSuchElementException("There are no available orders");
		}
		return orders;
	}
}
