/**
 * DB_Operator.java
 * FIT5183A1
 * Phillip
 * 2014年3月9日 下午3:30:44
 */
package model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

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
		Flightinfo f = new Flightinfo();

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
	// private Flightinfo flightInfo;

	/**
	 * non-parameter constructor, initialize the connection to database
	 */
	public DB_Operator() {
		// TODO Auto-generated constructor stub
		connection = DBConnector.connect(DBConnector.CONNECT_STRING);
		// resultSet = null;
		// flightInfo = new Flightinfo();
	}

	/**
	 * query the flight by flight number
	 * 
	 * @param flightNoString
	 * @param tableString
	 * @return FlightInfo structure which represents the corresponding flight
	 */
	public Flightinfo queryFlighByNo(String flightNoString, String tableString) {

		Flightinfo flightInfo = null;
		ResultSet resultSet = null;

		if (flightNoString.length() != 6) { // validate the flight number
			Util.debug("Wrong format of flight number!");
			return null;
		} else {
			resultSet = DBConnector.query(connection, QUERY_STRING
					+ tableString + "where FLNO = '" + flightNoString + "'");
			flightInfo = transformToFlightInfo(resultSet);
			return flightInfo;
		}
	}

	/**
	 * query flight by departing city
	 * 
	 * @param deptString
	 * @param tableString
	 * @return the corresponding flight info
	 */
	public Flightinfo queryFlightByDept(String deptString, String tableString) {

		Flightinfo flightinfo = null;
		ResultSet resultSet = null;

		resultSet = DBConnector.query(connection, QUERY_STRING + tableString
				+ "where DPCT = '" + deptString + "'");
		flightinfo = transformToFlightInfo(resultSet);

		return flightinfo;
	}

	/**
	 * query flight by departing and arriving cities
	 * 
	 * @param deptString
	 * @param destString
	 * @param tableString
	 * @return the corresponding flight info
	 */
	public Flightinfo queryFlightByLocation(String deptString,
			String destString, String tableString) {

		Flightinfo flightinfo = null;
		ResultSet resultSet = null;

		resultSet = DBConnector.query(connection, QUERY_STRING + tableString
				+ "where DPCT = '" + deptString + "' and DSCT = '" + destString
				+ "'");
		flightinfo = transformToFlightInfo(resultSet);

		return flightinfo;
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
	public Flightinfo queryFlightByLocaAndDate(String deptString,
			String destString, String dateString, String tableString) {

		Flightinfo flightinfo = null;
		ResultSet resultSet = null;

		resultSet = DBConnector.query(connection, QUERY_STRING + tableString
				+ " where DPCT = '" + deptString + "' and DSCT = '"
				+ destString + "' and TO_DAYS(DPDT) = TO_DAYS('" + dateString
				+ "')");
		flightinfo = transformToFlightInfo(resultSet);

		return flightinfo;
	}

	public Flightinfo queryFlightByLocaAndDateAndTime(String deptString,
			String destString, String dateString, String timeString1,
			String timeString2, String tableString) {

		Flightinfo flightinfo = null;
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
		flightinfo = transformToFlightInfo(resultSet);

		return flightinfo;
	}

	/**
	 * pack the query result into FlightInfo structure for further use
	 * 
	 * @param rSet
	 * @return a FlightInfo
	 */
	public Flightinfo transformToFlightInfo(ResultSet rSet) {

		Flightinfo f = new Flightinfo();
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

/**
 * @author Phillip a structure to store an entry from the database
 */
class Flightinfo {

	private String flightNumString;
	private String deptCityString;
	private String deptAirportString;
	private String destCityString;
	private String destAirportString;
	private Date deptDate;
	private Date arrvDate;
	private Time deptTime;
	private Time arrvTime;
	private String seatClass;
	private BigDecimal priceBigDecimal;
	private int seatAvalible;

	/**
	 * @return the flightNumString
	 */
	public String getFlightNumString() {
		return flightNumString;
	}

	/**
	 * @param flightNumString
	 *            the flightNumString to set
	 */
	public void setFlightNumString(String flightNumString) {
		this.flightNumString = flightNumString;
	}

	/**
	 * @return the deptCityString
	 */
	public String getDeptCityString() {
		return deptCityString;
	}

	/**
	 * @param deptCityString
	 *            the deptCityString to set
	 */
	public void setDeptCityString(String deptCityString) {
		this.deptCityString = deptCityString;
	}

	/**
	 * @return the deptAirportString
	 */
	public String getDeptAirportString() {
		return deptAirportString;
	}

	/**
	 * @param deptAirportString
	 *            the deptAirportString to set
	 */
	public void setDeptAirportString(String deptAirportString) {
		this.deptAirportString = deptAirportString;
	}

	/**
	 * @return the destCityString
	 */
	public String getDestCityString() {
		return destCityString;
	}

	/**
	 * @param destCityString
	 *            the destCityString to set
	 */
	public void setDestCityString(String destCityString) {
		this.destCityString = destCityString;
	}

	/**
	 * @return the destAirportString
	 */
	public String getDestAirportString() {
		return destAirportString;
	}

	/**
	 * @param destAirportString
	 *            the destAirportString to set
	 */
	public void setDestAirportString(String destAirportString) {
		this.destAirportString = destAirportString;
	}

	/**
	 * @return the deptDate
	 */
	public Date getDeptDate() {
		return deptDate;
	}

	/**
	 * @param deptDate
	 *            the deptDate to set
	 */
	public void setDeptDate(Date deptDate) {
		this.deptDate = deptDate;
	}

	/**
	 * @return the arrvDate
	 */
	public Date getArrvDate() {
		return arrvDate;
	}

	/**
	 * @param arrvDate
	 *            the arrvDate to set
	 */
	public void setArrvDate(Date arrvDate) {
		this.arrvDate = arrvDate;
	}

	/**
	 * @return the depTime
	 */
	public Time getDeptTime() {
		return deptTime;
	}

	/**
	 * @param depTime
	 *            the depTime to set
	 */
	public void setDeptTime(Time deptTime) {
		this.deptTime = deptTime;
	}

	/**
	 * @return the arrvTime
	 */
	public Time getArrvTime() {
		return arrvTime;
	}

	/**
	 * @param arrvTime
	 *            the arrvTime to set
	 */
	public void setArrvTime(Time arrvTime) {
		this.arrvTime = arrvTime;
	}

	/**
	 * @return the seatClass
	 */
	public String getSeatClass() {
		return seatClass;
	}

	/**
	 * @param seatClass
	 *            the seatClass to set
	 */
	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	/**
	 * @return the priceBigDecimal
	 */
	public BigDecimal getPriceBigDecimal() {
		return priceBigDecimal;
	}

	/**
	 * @param priceBigDecimal
	 *            the priceBigDecimal to set
	 */
	public void setPriceBigDecimal(BigDecimal priceBigDecimal) {
		this.priceBigDecimal = priceBigDecimal;
	}

	/**
	 * @return the seatAvalible
	 */
	public int getSeatAvalible() {
		return seatAvalible;
	}

	/**
	 * @param seatAvalible
	 *            the seatAvalible to set
	 */
	public void setSeatAvalible(int seatAvalible) {
		this.seatAvalible = seatAvalible;
	}
}