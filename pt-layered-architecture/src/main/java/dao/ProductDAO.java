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
import model.Product;

public class ProductDAO {
	protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
	private static final String insertStatement = "INSERT INTO product (name, unit_price, quantity) VALUES (?, ?, ?)";
	private static final String findAllStatement = "SELECT * FROM product";
	private static final String findByIdStatement = "SELECT * FROM product WHERE id = ?";
	private static final String findByNameStatement = "SELECT * FROM product WHERE name = ?";
	private static final String updateStatement = "UPDATE product set name = ?, unit_price = ?, quantity = ? WHERE id = ?";
	private static final String deleteStatement = "DELETE FROM product WHERE id = ?";
	
	public static Product findById(int id) {
		Product toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findByIdStatement);
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			String name = rs.getString("name");
			int unit_price = rs.getInt("unit_price");
			int quantity = rs.getInt("quantity");
			toReturn = new Product(id, name, unit_price, quantity);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"ProductDAO: get by id: " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	public static ArrayList<Product> findAll(){
		ArrayList<Product> toReturn = new ArrayList<Product>();
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findAllStatement);
			rs = findStatement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int unit_price = rs.getInt("unit_price");
				int quantity = rs.getInt("quantity");
				toReturn.add(new Product(id, name, unit_price, quantity));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"ProductDAO: get all: " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
		
	}
	public static Product findByName(String name) {
		Product toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findByNameStatement);
			findStatement.setString(1, name);
			rs = findStatement.executeQuery();
			rs.next();

			int id = rs.getInt("id");
			int unit_price = rs.getInt("unit_price");
			int quantity = rs.getInt("quantity");
			toReturn = new Product(id, name, unit_price, quantity);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"ProductDAO: get by name: " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	public static int insert(Product product) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatementPrep = null;
		int insertedId = -1;
		try {
			insertStatementPrep = dbConnection.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
			insertStatementPrep.setString(1, product.getName());
			insertStatementPrep.setInt(2, product.getUnitPrice());
			insertStatementPrep.setInt(3, product.getQuantity());
			insertStatementPrep.executeUpdate();

			ResultSet rs = insertStatementPrep.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Product: insert: " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatementPrep);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}
	public static void edit(Product product){
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement editStatement = null;
		try {
			editStatement = dbConnection.prepareStatement(updateStatement);
			editStatement.setString(1, product.getName());
			editStatement.setInt(2, product.getUnitPrice());
			editStatement.setInt(3, product.getQuantity());
			editStatement.setInt(4, product.getId());
			editStatement.execute();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO: edit: " + e.getMessage());
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
			LOGGER.log(Level.WARNING, "ProductDAO: delete: " + e.getMessage());
		} finally {
			ConnectionFactory.close(delStatement);
			ConnectionFactory.close(dbConnection);
		}
		return ;
	}
}
