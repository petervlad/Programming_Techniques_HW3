package bll;

import dao.CustomerDAO;
import dao.ProductDAO;
import model.OrderProduct;
import model.Product;

import java.util.ArrayList;
import java.util.NoSuchElementException;


public class ProductBLL {
	public int insertProduct(Product product){
		return ProductDAO.insert(product);
	}
	public Product findByName(String name){
		Product product = ProductDAO.findByName(name);
		if(product == null){
			throw new NoSuchElementException("Product with name "+name+"does not exist!");
		}
		return product;
	}
	public Product findById(int id){
		Product product = ProductDAO.findById(id);
		if(product == null){
			throw new NoSuchElementException("Product with id "+id+" does not exist!");
		}
		return product;
	}
	public ArrayList<Product> findAll(){
		ArrayList<Product> products = ProductDAO.findAll();
		if(products.isEmpty()){
			throw new NoSuchElementException("There are no available products");
		}
		return products;
	}
	public void edit(int id, String name, int unit_price, int quantity){
		Product product = this.findById(id);
		if(!name.isEmpty()){
			product.setName(name);
		}
		if(unit_price != -1){
			product.setUnitPrice(unit_price);
		}
		if(quantity != -1){
			product.setQuantity(quantity);
		}
		ProductDAO.edit(product);
	}
	public void delete(Product product){
		ProductDAO.delete(product.getId());
	}
	public void delete(int id){
		ProductDAO.delete(id);
	}
	public void delete(String name){
		ProductDAO.delete(CustomerDAO.findByName(name).getId());
	}
	public boolean checkStock(Product product, int quantity){
		if(product.getQuantity() < quantity){
			return false;
		}
		return true;
	}
	public void updateStock(ArrayList<OrderProduct> orderProducts){
		for(OrderProduct orderProduct : orderProducts){
			Product product = this.findById(orderProduct.getProductId());
			product.setQuantity(product.getQuantity() - orderProduct.getQuantity());
			this.edit(product.getId(), product.getName(), product.getUnitPrice(), product.getQuantity());
		}
	}
}
