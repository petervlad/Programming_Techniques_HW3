package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Customer;

public class CustomerDAO {
	protected static final Logger LOGGER = Logger.getLogger(CustomerDAO.class.getName());
	private static final String insertStatement = "INSERT INTO customer (name, email, address) VALUES (?, ?, ?)";
	private static final String findAllStatement = "SELECT * FROM customer";
	private static final String findByIdStatement = "SELECT * FROM customer WHERE id = ?";
	private static final String findByNameStatement = "SELECT * FROM customer WHERE name = ?";
	private static final String updateStatement = "UPDATE customer SET name = ?, email = ?, address = ? where id = ?";
	private static final String deleteStatement = "DELETE FROM customer WHERE id = ?";
	
	public static Customer findById(int customerId) {
		Customer toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findByIdStatement);
			findStatement.setLong(1, customerId);
			rs = findStatement.executeQuery();
			rs.next();

			String name = rs.getString("name");
			String address = rs.getString("address");
			String email = rs.getString("email");
			toReturn = new Customer(customerId, name, address, email);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"CustomerDAO: get by id: " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	public static ArrayList<Customer> findAll(){
		ArrayList<Customer> toReturn = new ArrayList<Customer>();
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findAllStatement);
			rs = findStatement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String address = rs.getString("address");
				toReturn.add(new Customer(id, name, email, address));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"CustomerDAO: get all: " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
		
	}
	public static Customer findByName(String name) {
		Customer toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findByNameStatement);
			findStatement.setString(1, name);
			rs = findStatement.executeQuery();
			rs.next();

			int id = rs.getInt("id");
			String address = rs.getString("address");
			String email = rs.getString("email");
			toReturn = new Customer(id, name, address, email);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"CustomerDAO: get by name: " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	public static int insert(Customer customer) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatementPrep = null;
		int insertedId = -1;
		try {
			insertStatementPrep = dbConnection.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
			insertStatementPrep.setString(1, customer.getName());
			insertStatementPrep.setString(2, customer.getAddress());
			insertStatementPrep.setString(3, customer.getEmail());
			insertStatementPrep.executeUpdate();

			ResultSet rs = insertStatementPrep.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "CustomerDAO: insert: " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatementPrep);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}
	public static void edit(Customer customer){
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement editStatement = null;
		try {
			editStatement = dbConnection.prepareStatement(updateStatement);
			editStatement.setString(1, customer.getName());
			editStatement.setString(2, customer.getEmail());
			editStatement.setString(3, customer.getAddress());
			editStatement.setInt(4, customer.getId());
			editStatement.execute();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "CustomerDAO: edit: " + e.getMessage());
		} finally {
			ConnectionFactory.close(editStatement);
			ConnectionFactory.close(dbConnection);
		}
		return ;
	}
	public static void delete(int id){
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement delStatement = null;
		try {
			delStatement = dbConnection.prepareStatement(deleteStatement);
			delStatement.setInt(1, id);
			delStatement.execute();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "CustomerDAO: delete: " + e.getMessage());
		} finally {
			ConnectionFactory.close(delStatement);
			ConnectionFactory.close(dbConnection);
		}
		return ;
	}
}
