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
import model.Order;

public class OrderDAO {
	protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
	private static final String insertStatement = "INSERT INTO orders (price, customer_id) VALUES (?, ?)";
	private static final String findAllStatement = "SELECT * FROM order";
	private static final String findByIdStatement = "SELECT * FROM order WHERE id = ?";
	private static final String findByCustomerStatement = "SELECT * FROM order WHERE customer_id = ?";
	
	public static Order findById(int id) {
		Order toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findByIdStatement);
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			int price = rs.getInt("price");
			int customerId = rs.getInt("customer_id");
			toReturn = new Order(id, price, customerId);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"OrderDAO: get by id: " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	public static ArrayList<Order> findAll(){
		ArrayList<Order> toReturn = new ArrayList<Order>();
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findAllStatement);
			rs = findStatement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				int price = rs.getInt("price");
				int customerId = rs.getInt("customer_id");
				toReturn.add(new Order(id, price, customerId));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"OrderDAO: get all: " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
		
	}
	public static Order findByCustomer(int customerId) {
		Order toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findByCustomerStatement);
			findStatement.setInt(1, customerId);
			rs = findStatement.executeQuery();
			rs.next();

			int id = rs.getInt("id");
			int price = rs.getInt("price");
			toReturn = new Order(id, price, customerId);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"CustomerDAO: get by name: " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	public static int insert(Order order) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatementPrep = null;
		int insertedId = -1;
		try {
			insertStatementPrep = dbConnection.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
			insertStatementPrep.setInt(1, order.getPrice());
			insertStatementPrep.setInt(2, order.getCustomerId());
			insertStatementPrep.executeUpdate();

			ResultSet rs = insertStatementPrep.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderDAO: insert: " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatementPrep);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}
}
