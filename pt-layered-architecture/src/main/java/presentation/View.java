package presentation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;

import model.OrderProduct;
import model.Product;

public class View {
	private Controller controller = new Controller();
	public View(){
		JFrame main = new JFrame();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setTitle("Main");
		main.setSize(500, 300);
		main.setLocationRelativeTo(null);
		JPanel buttons = new JPanel();
		JButton customer = new JButton("Customers");
		JButton products = new JButton("Products");
		JButton orders = new JButton("Orders");
		buttons.add(customer);
		buttons.add(products);
		buttons.add(orders);
		main.add(buttons);
		main.setVisible(true);
		orders.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				order();
			}
		});
		customer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				manageCustomers();
			}
		});
		products.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				manageProducts();
			}
		});
	}
	public void manageCustomers(){
		GridLayout layout = new GridLayout(0, 2);
		
		final JFrame frameOrder = new JFrame();
		frameOrder.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameOrder.setTitle("Manage Customers");
		frameOrder.setSize(300, 250);
		frameOrder.setLocationRelativeTo(null);
		String[] customers = controller.getCustomers();
		final JPanel Operations = new JPanel();
		Operations.setLayout(layout);
		final JComboBox customersComboBox = new JComboBox(customers);
		JButton delete = new JButton("Delete Customer");
		final JComboBox editCustomers = new JComboBox(customers);
		JButton edit = new JButton("Edit Customer");
		JLabel name = new JLabel("Name:");
		final JTextArea nameText = new JTextArea();
		JLabel address = new JLabel("Address:");
		final JTextArea addressText = new JTextArea();
		JLabel email = new JLabel("Email:");
		final JTextArea emailText = new JTextArea();
		JButton insert = new JButton("Add New");
		Operations.add(customersComboBox);
		Operations.add(delete);
		Operations.add(editCustomers);
		Operations.add(edit);
		Operations.add(name);
		Operations.add(nameText);
		Operations.add(address);
		Operations.add(addressText);
		Operations.add(email);
		Operations.add(emailText);
		Operations.add(insert);
		JTable customersTable = controller.getCustomersT();
		frameOrder.add(Operations, BorderLayout.NORTH);
		frameOrder.add(customersTable, BorderLayout.CENTER);
		
		frameOrder.setVisible(true);
		insert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = nameText.getText();
				String address = addressText.getText();
				String email = emailText.getText();
				controller.insertCustomer(name, email, address);
				frameOrder.dispose();
			}
		});
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = (String) customersComboBox.getSelectedObjects()[0];
				controller.deleteCustomer(name);
				frameOrder.dispose();
			}
		});
		edit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = nameText.getText();
				String address = addressText.getText();
				String email = emailText.getText();
				String prevName = (String) editCustomers.getSelectedObjects()[0];
				controller.editCustomer(prevName, name, email, address);
				frameOrder.dispose();
			}
		});
	}
	
	public void manageProducts(){
		GridLayout layout = new GridLayout(0, 2);
		
		final JFrame frameProduct = new JFrame();
		frameProduct.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameProduct.setTitle("Manage Products");
		frameProduct.setSize(300, 250);
		frameProduct.setLocationRelativeTo(null);
		String[] products = controller.getProducts();
		final JPanel Operations = new JPanel();
		Operations.setLayout(layout);
		final JComboBox productsComboBox = new JComboBox(products);
		JButton delete = new JButton("Delete Product");
		final JComboBox editProducts = new JComboBox(products);
		JButton edit = new JButton("Edit Product");
		JLabel name = new JLabel("Name:");
		final JTextArea nameText = new JTextArea();
		JLabel price = new JLabel("Price:");
		final JTextArea priceText = new JTextArea();
		JLabel quantity = new JLabel("Quantity:");
		final JTextArea quantityText = new JTextArea();
		JButton insert = new JButton("Add New");
		Operations.add(productsComboBox);
		Operations.add(delete);
		Operations.add(editProducts);
		Operations.add(edit);
		Operations.add(name);
		Operations.add(nameText);
		Operations.add(price);
		Operations.add(priceText);
		Operations.add(quantity);
		Operations.add(quantityText);
		Operations.add(insert);
		JTable productsTable = controller.getProductsT();
		frameProduct.add(Operations, BorderLayout.NORTH);
		frameProduct.add(productsTable, BorderLayout.CENTER);
		
		frameProduct.setVisible(true);
		insert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = nameText.getText();
				int price = Integer.parseInt(priceText.getText());
				int quantity = Integer.parseInt(quantityText.getText());
				controller.insertProduct(name, price, quantity);
				frameProduct.dispose();
			}
		});
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = (String) productsComboBox.getSelectedObjects()[0];
				controller.deleteProduct(name);
				frameProduct.dispose();
			}
		});
		edit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = nameText.getText();
				String pr = priceText.getText();
				int price = -1;
				if(!pr.isEmpty()){
					price = Integer.parseInt(pr);
				}	
				
				int quantity = -1;
				String qu = quantityText.getText();
				if(!qu.isEmpty()){
					quantity = Integer.parseInt(qu);
				}
				String prevName = (String) editProducts.getSelectedObjects()[0];
				controller.editProduct(prevName, name, price, quantity);
				frameProduct.dispose();
			}
		});
	}
	
	public void order(){
		GridLayout layout = new GridLayout(0, 2);
		final ArrayList<OrderProduct> listOrderProduct = new ArrayList<OrderProduct>();
		final int price = 0;
		
		final JFrame frameOrder = new JFrame();
		frameOrder.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameOrder.setTitle("Order");
		frameOrder.setSize(300, 250);
		frameOrder.setLocationRelativeTo(null);
		JPanel customerPanel = new JPanel();
		customerPanel.setLayout(layout);
		JLabel customerLab = new JLabel("Customer:");
		String[] customers = controller.getCustomers();
		final String[] products = controller.getProducts();
		final JComboBox customer = new JComboBox(customers);
		customerPanel.add(customerLab);
		customerPanel.add(customer);
		frameOrder.add(customerPanel, BorderLayout.NORTH);
		
		final JPanel productPanel = new JPanel();
		productPanel.setLayout(layout);
		JLabel name = new JLabel("Product");
		JLabel qty = new JLabel("Quantity");
		productPanel.add(name);
		productPanel.add(qty);
		final JComboBox product = new JComboBox(products);
		final JTextArea quantity = new JTextArea();
		productPanel.add(product);
		productPanel.add(quantity);
		frameOrder.add(productPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(layout);
		JButton add = new JButton("Add");
		JButton order = new JButton("Order");
		buttonPanel.add(add);
		buttonPanel.add(order);
		frameOrder.add(buttonPanel, BorderLayout.SOUTH);
		
		frameOrder.setVisible(true);
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String prodName = (String) product.getSelectedObjects()[0];
				String qu = (String) quantity.getText();
				int quantity = -1;
				if(!qu.isEmpty()){
					quantity = Integer.parseInt(qu);
				}
				Product prod = controller.addProductToOrder(prodName, quantity);
				if(prod != null){
					listOrderProduct.add(new OrderProduct(0, prod.getId(), quantity));
					System.out.println("Added product to order");
				}else{
					System.out.println("Not enough products to add to order. Select different quantity");
				}
			}
		});
		order.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String customerName = (String) customer.getSelectedObjects()[0];
				controller.order(listOrderProduct, customerName);
				frameOrder.dispose();
			}
		});
	}
}
