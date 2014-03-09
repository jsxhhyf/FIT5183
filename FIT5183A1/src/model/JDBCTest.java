/**
 * 
 */
package model;

import java.sql.*;
/**
 * @author Phillip
 * TODO test JDBC connection
 */
public class JDBCTest {

	static Connection connection;
	static Statement statement;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		query();
	}

	public static void query() {
		connection = getConnection();
		try {
			String sqlString = "select * from Airline1";
			statement = (Statement) connection.createStatement();
			ResultSet rSet = statement.executeQuery(sqlString);
			while (rSet.next()) {
				String classString = rSet.getString("CLAS");
				System.out.println(classString);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("fail");
		}
	}
	
	/**
	 * @return connection to database according to the baseURL
	 */
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/id25466232", "fit5183a1", "");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return con;
	}
}
