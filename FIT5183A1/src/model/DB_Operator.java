/**
 * DB_Operator.java
 * FIT5183A1
 * Phillip
 * 2014年3月9日 下午3:30:44
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		DB_Operator db_Operator = new DB_Operator();
		FlightEntity f = new FlightEntity();

		f = db_Operator.queryFlightByLocaAndDateAndTime("C_CITY1", "A_CITY1",
				"2014-03-01", "00:00", "20:00", "airline1");

		if (db_Operator.connection != null) {
			try {
				db_Operator.connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (f != null) {
			Util.debug(f.getDeptAirportString());
		}

	}

	private final String QUERY_STRING = "select * from ";
	// private final String QUERY_BY_NUMBER_STRING = " where FLNO = '";

	private Connection connection;

	// private ResultSet resultSet;
	// private FlightEntity FlightEntity;

	/**
	 * non-parameter constructor, initialize the connection to database
	 */
	public DB_Operator() {
		// TODO Auto-generated constructor stub
		connection = DBConnector.connect(DBConnector.CONNECT_STRING);
		// resultSet = null;
		// FlightEntity = new FlightEntity();
	}

	/**
	 * query the flight by flight number
	 * 
	 * @param flightNoString
	 * @param tableString
	 * @return FlightEntity structure which represents the corresponding flight
	 */
	public FlightEntity queryFlighByNo(String flightNoString, String tableString) {

		FlightEntity FlightEntity = null;
		ResultSet resultSet = null;

		if (flightNoString.length() != 6) { // validate the flight number
			Util.debug("Wrong format of flight number!");
			return null;
		} else {
			resultSet = DBConnector.query(connection, QUERY_STRING
					+ tableString + " where FLNO = '" + flightNoString + "'");
			FlightEntity = transformToFlightEntity(resultSet);
			return FlightEntity;
		}
	}

	/**
	 * query flight by departing city
	 * 
	 * @param deptString
	 * @param tableString
	 * @return the corresponding flight info
	 */
	public FlightEntity queryFlightByDept(String deptString, String tableString) {

		FlightEntity FlightEntity = null;
		ResultSet resultSet = null;

		resultSet = DBConnector.query(connection, QUERY_STRING + tableString
				+ "where DPCT = '" + deptString + "'");
		FlightEntity = transformToFlightEntity(resultSet);

		return FlightEntity;
	}

	/**
	 * query flight by departing and arriving cities
	 * 
	 * @param deptString
	 * @param destString
	 * @param tableString
	 * @return the corresponding flight info
	 */
	public FlightEntity queryFlightByLocation(String deptString,
			String destString, String tableString) {

		FlightEntity FlightEntity = null;
		ResultSet resultSet = null;

		resultSet = DBConnector.query(connection, QUERY_STRING + tableString
				+ "where DPCT = '" + deptString + "' and DSCT = '" + destString
				+ "'");
		FlightEntity = transformToFlightEntity(resultSet);

		return FlightEntity;
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
	public FlightEntity queryFlightByLocaAndDate(String deptString,
			String destString, String dateString, String tableString) {

		FlightEntity FlightEntity = null;
		ResultSet resultSet = null;

		resultSet = DBConnector.query(connection, QUERY_STRING + tableString
				+ " where DPCT = '" + deptString + "' and DSCT = '"
				+ destString + "' and TO_DAYS(DPDT) = TO_DAYS('" + dateString
				+ "')");
		FlightEntity = transformToFlightEntity(resultSet);

		return FlightEntity;
	}

	public FlightEntity queryFlightByLocaAndDateAndTime(String deptString,
			String destString, String dateString, String timeString1,
			String timeString2, String tableString) {

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
		FlightEntity = transformToFlightEntity(resultSet);

		return FlightEntity;
	}
	
	public boolean book(String flightNumer, String classString, String tableString) {
		int temp = queryFlighByNo(flightNumer, tableString).getSeatAvalible();
		temp--;
		String updateString = "update " + tableString + " set SEAT = " + temp + " where FLNO = '" + flightNumer + "'";
		int rows = DBConnector.update(connection, updateString);
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
	public FlightEntity transformToFlightEntity(ResultSet rSet) {

		FlightEntity f = new FlightEntity();
		if (rSet == null) {
			Util.debug("ResultSet is null during the transformation!");
			return null;
		}
		try {
			rSet.next(); // must add this, the cursor of ResultSet is before the
							// first line initially
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
			f.setPriceBigDecimal(rSet.getBigDecimal("PRIC"));
			f.setSeatAvalible(rSet.getInt("SEAT"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return f;
	}

}

