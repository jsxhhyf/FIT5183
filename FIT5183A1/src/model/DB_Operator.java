/**
 * DB_Operator.java
 * FIT5183A1
 * Phillip
 * 2014年3月9日 下午3:30:44
 */
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DBConnector;
import util.Util;

/**
 * @author Phillip
 * 
 */
public class DB_Operator {

	/**
	 * just for test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		DB_Operator db_Operator = new DB_Operator();
//		FlightEntity f = new FlightEntity();
//
//		f = db_Operator.queryFlightByLocaAndDateAndTime("C_CITY1", "A_CITY1",
//				"2014-03-01", "00:00", "20:00", "airline1");
//
//		if (db_Operator.connection != null) {
//			try {
//				db_Operator.connection.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		if (f != null) {
//			Util.debug(f.getDeptAirportString());
//		}

	}

	private final String QUERY_STRING = "select * from ";
	// private final String QUERY_BY_NUMBER_STRING = " where FLNO = '";

//	private Connection connection;

	// private ResultSet resultSet;
	// private FlightEntity FlightEntity;

	/**
	 * non-parameter constructor, initialize the connection to database
	 */
//	public DB_Operator() {
//		 TODO Auto-generated constructor stub
//		connection = DBConnector.connect(DBConnector.CONNECT_STRING);
//		 resultSet = null;
//		 FlightEntity = new FlightEntity();
//	}

	/**
	 * query the flight by flight number
	 * 
	 * @param flightNoString
	 * @param tableString
	 * @return FlightEntity structure which represents the corresponding flight
	 */
	public ArrayList<FlightEntity> queryFlighByNo(String flightNoString, String tableString) {

		Connection connection = DBConnector.connect(DBConnector.CONNECT_STRING);

		ArrayList<FlightEntity> flightEntities = null;
		ResultSet resultSet = null;

		if (flightNoString.length() != 6) { // validate the flight number
			Util.debug("Wrong format of flight number!");
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
			resultSet = DBConnector.query(connection, QUERY_STRING
					+ tableString + " where FLNO = '" + flightNoString + "'");
			try {
				
				if (!resultSet.next()) {
					return null;
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			flightEntities = transformToFlightEntity(resultSet);
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return flightEntities;
		}
	}

	/**
	 * query flight by departing city
	 * 
	 * @param deptString
	 * @param tableString
	 * @return the corresponding flight info
	 */
	public ArrayList<FlightEntity> queryFlightByDept(String deptString, String tableString) {

		Connection connection = DBConnector.connect(DBConnector.CONNECT_STRING);
//		FlightEntity FlightEntity = null;
		ResultSet resultSet = null;

		resultSet = DBConnector.query(connection, QUERY_STRING + tableString
				+ "where DPCT = '" + deptString + "'");
		if (resultSet == null) {
			return null;
		}

		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transformToFlightEntity(resultSet);
	}

	/**
	 * query flight by departing and arriving cities
	 * 
	 * @param deptString
	 * @param destString
	 * @param tableString
	 * @return the corresponding flight info
	 */
	public List<FlightEntity> queryFlightByLocation(String deptString,
			String destString, String tableString) {

		Connection connection = DBConnector.connect(DBConnector.CONNECT_STRING);
//		FlightEntity FlightEntity = null;
		ResultSet resultSet = null;

		resultSet = DBConnector.query(connection, QUERY_STRING + tableString
				+ "where DPCT = '" + deptString + "' and DSCT = '" + destString
				+ "'");
		if (resultSet == null) {
			return null;
		}

		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transformToFlightEntity(resultSet);
	}

	/**
	 * query flight by departing and arriving cities and the departing date
	 * 
	 * @param deptString
	 * @param destString
	 * @param dateString
	 * @param tableString
	 * @return the corresponding flight info
	 */
	public ArrayList<FlightEntity> queryFlightByLocaAndDate(String deptString,
			String destString, String dateString, String tableString) {

		Connection connection = DBConnector.connect(DBConnector.CONNECT_STRING);
		FlightEntity FlightEntity = null;
		ResultSet resultSet = null;

		resultSet = DBConnector.query(connection, QUERY_STRING + tableString
				+ " where DPCT = '" + deptString + "' and DSCT = '"
				+ destString + "' and TO_DAYS(DPDT) = TO_DAYS('" + dateString
				+ "')");
		if (resultSet == null) {
			return null;
		}

		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transformToFlightEntity(resultSet);
	}

	public ArrayList<FlightEntity> queryFlightByLocaAndDateAndTime(String deptString,
			String destString, String dateString, String timeString1,
			String timeString2, String tableString) {

		Connection connection = DBConnector.connect(DBConnector.CONNECT_STRING);
		FlightEntity FlightEntity = null;
		ResultSet resultSet = null;

		resultSet = DBConnector
				.query(connection,
						QUERY_STRING
								+ tableString
								+ " where DPCT = '"
								+ deptString
								+ "' and DSCT = '"
								+ destString
								+ "' and UNIX_TIMESTAMP(CONCAT(DPDT,' ',DPTM)) < UNIX_TIMESTAMP('"
								+ dateString
								+ " "
								+ timeString2
								+ "') and UNIX_TIMESTAMP(CONCAT(DPDT,' ',DPTM)) > UNIX_TIMESTAMP('"
								+ dateString + " " + timeString1 + "')");
		if (resultSet == null) {
			return null;
		}

		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transformToFlightEntity(resultSet);
	}
	
	public boolean book(String flightNumer, String classString, String tableString) {
		Connection connection = DBConnector.connect(DBConnector.CONNECT_STRING);
		int temp = queryFlighByNo(flightNumer, tableString).get(0).getSeatAvalible();
		temp--;
		String updateString = "update " + tableString + " set SEAT = " + temp + " where FLNO = '" + flightNumer + "'";
		int rows = DBConnector.update(connection, updateString);
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * pack the query result into FlightEntity structure for further use
	 * 
	 * @param rSet
	 * @return a FlightEntity
	 */
	public ArrayList<FlightEntity> transformToFlightEntity(ResultSet rSet) {

		ArrayList<FlightEntity> list = new ArrayList<FlightEntity>();
		
		try {
			do {
				FlightEntity f = new FlightEntity();
				f.setFlightNumString(rSet.getString("FLNO"));
				
				f.setDeptCityString(rSet.getString("DPCT"));
				f.setDeptAirportString(rSet.getString("DPAP"));
				f.setDestCityString(rSet.getString("DSCT"));
				f.setDestAirportString(rSet.getString("DSAP"));
				f.setDeptDate(rSet.getDate("DPDT"));
				f.setArrvDate(rSet.getDate("ARDT"));
				f.setDeptTime(rSet.getTime("DPTM"));
				f.setArrvTime(rSet.getTime("ARTM"));
				f.setSeatClass(rSet.getString("CLAS"));
//				Util.debug(f.getSeatClass());
				f.setPriceBigDecimal(rSet.getBigDecimal("PRIC"));
				f.setSeatAvalible(rSet.getInt("SEAT"));
				list.add(f);
				//Util.debug(list.size());
			} while (rSet.next());
//			Util.debug(list.get(0).getSeatClass());
//			Util.debug(list.get(1).getSeatClass());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}

