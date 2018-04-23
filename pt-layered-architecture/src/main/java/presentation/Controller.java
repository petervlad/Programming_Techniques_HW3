package presentation;

import java.util.ArrayList;

import javax.swing.JTable;

import bll.CustomerBLL;
import bll.OrderBLL;
import bll.OrderProductBLL;
import bll.ProductBLL;
import model.Customer;
import model.Order;
import model.OrderProduct;
import model.Product;

public class Controller {
	private ProductBLL productBLL = new ProductBLL();
	private CustomerBLL customerBLL = new CustomerBLL();
	private OrderBLL orderBLL = new OrderBLL();
	private OrderProductBLL orderProductBLL = new OrderProductBLL();
	public String[] getProducts(){
		ArrayList<Product> products = productBLL.findAll();
		int size = products.size();
		int i = 0;
		String[] getReturn = new String[size];
		for(Product product : products){
			getReturn[i] = product.getName();
			i++;
		}
		return getReturn;
	}
	public String[] getCustomers(){
		ArrayList<Customer> customers = customerBLL.findAll();
		int size = customers.size();
		int i = 0;
		String[] getReturn = new String[size];
		for(Customer customer : customers){
			getReturn[i] = customer.getName();
			i++;
		}
		return getReturn;
	}
	public JTable getCustomersT(){
		System.out.println("lalala");
		ArrayList<Customer> customers = customerBLL.findAll();
		int size = customers.size();
		int i = 0;
		System.out.println("lalala");
		Object[][] row = new Object[size][3];
		Object[] column = new Object[3];
		column[0] = "Name";
		column[1] = "Email";
		column[2] = "Address";
		for(Customer customer : customers){
			row[i][0] = customer.getName();
			row[i][1] = customer.getEmail();
			row[i][2] = customer.getAddress();
			i++;
		}
		JTable table = new JTable(row, column);
		return table;
	}
	public JTable getProductsT(){
		ArrayList<Product> products = productBLL.findAll();
		int size = products.size();
		int i = 0;
		Object[][] row = new Object[size][3];
		Object[] column = new Object[3];
		column[0] = "Name";
		column[1] = "Unit Price";
		column[2] = "Quantity";
		for(Product product : products){
			row[i][0] = product.getName();
			row[i][1] = product.getUnitPrice();
			row[i][2] = product.getQuantity();
		}
		JTable table = new JTable(row, column);
		return table;
	}
	public void insertCustomer(String name, String email, String address){
		Customer customer = new Customer(name, email, address);
		customerBLL.insert(customer);
	}
	public void insertProduct(String name, int unit_price, int quantity){
		Product product = new Product(name, unit_price, quantity);
		productBLL.insertProduct(product);
	}
	public void deleteCustomer(String name){
		customerBLL.delete(name);
	}
	public void deleteProduct(String name){
		productBLL.delete(name);
	}
	public void editCustomer(String prevName, String name, String email, String address){
		int id = customerBLL.findByName(prevName).getId();
		customerBLL.update(id, name, email, address);
	}
	public void editProduct(String prevName, String name, int unit_price, int quantity){
		int id = productBLL.findByName(prevName).getId();
		productBLL.edit(id, name, unit_price, quantity);
	}
	public Product addProductToOrder(String name, int quantity){
		Product product = productBLL.findByName(name);
		boolean ok = productBLL.checkStock(product, quantity);
		if(ok == true){
			return product;
		}
		return null;
	}
	public void order(ArrayList<OrderProduct> orderProducts, String customerName){
		int customerId = customerBLL.findByName(customerName).getId();
		int price = 0;
		for(OrderProduct orderProduct : orderProducts){
			price += productBLL.findById(orderProduct.getProductId()).getUnitPrice()*orderProduct.getQuantity();
		}
		Order order = new Order(price, customerId);
		int orderId = orderBLL.insertOrder(order);
		orderProductBLL.order(orderId, orderProducts);
		productBLL.updateStock(orderProducts);
	}
}
