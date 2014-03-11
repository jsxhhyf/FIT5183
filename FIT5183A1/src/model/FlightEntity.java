/**
 * FlightEntity.java
 * FIT5183A1
 * Phillip
 * 2014年3月11日 下午7:05:29
 */
package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

/**
 * @author Phillip
 *
 */
public class FlightEntity {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

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
