/**
 * DBConnector.java
 * FIT5183A1
 * Phillip
 * 2014年3月9日 下午3:40:57
 */
package util;

import java.sql.*;

/**
 * @author Phillip
 *
 */
public class DBConnector {

	/**
	 * @param args
	 */
	public static void main(String[] args) { //for unit test
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * build up the connection to the database and it is a static method
	 * @param sqlString
	 * @return the connection build up to the database based on sqlString 
	 */
	public static Connection connect(String sqlString) {
		
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); //throw ClassNotFoundException
			connection = DriverManager.getConnection(sqlString, "fit5183a1", ""); //throw SQLException
		} catch (Exception e) { // combine two different exceptions
			// TODO: handle exception
			System.out.println("Fail to build connection!");
		}
		return connection;
	}
	
	/**
	 * execute the corresponding query according to the queryString
	 * @param queryString
	 * @return the query result
	 */
	public static ResultSet query(Connection connection, String queryString) {
		
		ResultSet resultSet = null;
		
		if (connection == null) {
			System.out.println("Please establish connection first!");
			return null;
		}
		try {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(queryString);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return resultSet;
		
	}
	
	public static int update(Connection connection, String queryString) {
		
		//ResultSet resultSet = null;
		int rows = 0;
		
		if (connection == null) {
			System.out.println("Please establish connection first!");
			return 0;
		}
		try {
			Statement statement = connection.createStatement();
			rows = statement.executeUpdate(queryString);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rows;
		
	}
	
	
}
