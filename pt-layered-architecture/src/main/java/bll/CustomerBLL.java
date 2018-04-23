package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.CustomerDAO;
import model.Customer;

public class CustomerBLL {
	
	private List<Validator<Customer>> validators;

	public CustomerBLL() {
		validators = new ArrayList<Validator<Customer>>();
		validators.add(new EmailValidator());
	}
	public int insert(Customer customer){
		for (Validator<Customer> v : validators) {
			v.validate(customer);
		}
		return CustomerDAO.insert(customer);
	}
	public Customer findByName(String name){
		Customer customer = CustomerDAO.findByName(name);
		if(customer == null){
			throw new NoSuchElementException("Customer with name "+name+"does not exist!");
		}
		return customer;
	}
	public Customer findById(int id){
		Customer customer = CustomerDAO.findById(id);
		if(customer == null){
			throw new NoSuchElementException("Customer with id "+id+" does not exist!");
		}
		return customer;
	}
	public ArrayList<Customer> findAll(){
		ArrayList<Customer> customers = CustomerDAO.findAll();
		if(customers.isEmpty()){
			throw new NoSuchElementException("There are no available customers");
		}
		return customers;
	}
	public void update(int id, String name, String email, String address){
		Customer customer = this.findById(id);
		if(!name.isEmpty()){
			System.out.println("name if not empty:  " + name);
			customer.setName(name);
		}
		if(!email.isEmpty()){
			System.out.println("email is not empty " + email);
			customer.setEmail(email);
			for (Validator<Customer> v : validators) {
				v.validate(customer);
			}
		}
		if(!address.isEmpty()){
			System.out.println("address is not empty " + address);
			customer.setAddress(address);
		}
		System.out.println(customer);
		CustomerDAO.edit(customer);
	}
	public void delete(Customer customer){
		CustomerDAO.delete(customer.getId());
	}
	public void delete(int id){
		CustomerDAO.delete(id);
	}
	public void delete(String name){
		Customer customer = CustomerDAO.findByName(name);
		CustomerDAO.delete(customer.getId());
	}
}
