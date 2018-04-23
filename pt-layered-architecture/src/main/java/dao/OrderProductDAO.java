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
import model.OrderProduct;

public class OrderProductDAO {
	protected static final Logger LOGGER = Logger.getLogger(OrderProductDAO.class.getName());
	private static final String insertStatement = "INSERT INTO order_product (order_id, product_id, quantity) VALUES (?, ?, ?)";
	private static final String findAllStatement = "SELECT * FROM order_product";
	private static final String findByIdStatement = "SELECT * FROM order_product WHERE id = ?";
	
	public static OrderProduct findById(int id) {
		OrderProduct toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findByIdStatement);
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			int orderId = rs.getInt("order_id");
			int productId = rs.getInt("product_id");
			int quantity = rs.getInt("quantity");
			toReturn = new OrderProduct(orderId, productId, quantity);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"OrderProductDAO: get by id: " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	public static ArrayList<OrderProduct> findAll(){
		ArrayList<OrderProduct> toReturn = new ArrayList<OrderProduct>();
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findAllStatement);
			rs = findStatement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				int orderId = rs.getInt("order_id");
				int productId = rs.getInt("product_id");
				int quantity = rs.getInt("quantity");
				toReturn.add(new OrderProduct(id, orderId, productId, quantity));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"OrderProductDAO: get all: " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
		
	}
	public static int insert(OrderProduct orderProduct) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatementPrep = null;
		int insertedId = -1;
		try {
			insertStatementPrep = dbConnection.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
			insertStatementPrep.setInt(1, orderProduct.getOrderId());
			insertStatementPrep.setInt(2, orderProduct.getProductId());
			insertStatementPrep.setInt(3, orderProduct.getQuantity());
			insertStatementPrep.executeUpdate();

			ResultSet rs = insertStatementPrep.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderProductDAO: insert: " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatementPrep);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}
}
