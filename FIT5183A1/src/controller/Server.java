/**
 * Server.java
 * FIT5183A1
 * Phillip
 * 2014年3月9日 下午3:21:30
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import model.DB_Operator;
import model.FlightEntity;
import util.Util;

/**
 * @author Phillip
 * 
 */
public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String tablenameString = "airline" + args[0];
		int PORT = 10010 + Integer.valueOf(args[0]);

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT);
			Util.debug(PORT);
			// Util.debug(serverSocket.getInetAddress().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Util.debug("Airline " + args[0] + " database server is running!");
		while (true) {
			Socket incoming = null;
			try {
				incoming = serverSocket.accept();
				// Util.debug(1);
				// Util.debug(incoming.getInetAddress().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			new SocketHandler(incoming, Integer.parseInt(args[0])).start();
		}
	}

}

/**
 * @author Phillip
 * 
 */
class SocketHandler extends Thread {

	int tablename = 0;
	Socket incomingSocket;
	DB_Operator dbOperator = new DB_Operator();
	ArrayList<FlightEntity> flightEntities = null;

	static String syncString = new String();

	SocketHandler(Socket incoming, int tableName) {
		this.incomingSocket = incoming;
		this.tablename = tableName;
		// Util.debug(2);
	}

	public void run() {
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					incomingSocket.getInputStream()));
			PrintStream printStream = new PrintStream(
					incomingSocket.getOutputStream());

			while (true) {
				// Util.debug(3);
				String messageString = reader.readLine();
				Util.debug(messageString);

				if (messageString.trim().equalsIgnoreCase("BYE")) {
					break;
				}
				String[] strings = messageString.split("\\*");
				/*
				 * the format of the incoming message is
				 * Operation0*FlightNo1*Airline2*DepatingCity3*DestinationCity4
				 * *DepatingDate5*Class6*#
				 * 
				 * the format of the response message is
				 * FlightNo0*Airline1*DepartingCity2*DepartingAirport3
				 * *DestinationCity4*ArrivingAirport5
				 * *DepartingDate6*ArrivingDate7*
				 * DepartingTime8*ArrivingTime9*Class10*Price11*AvalibleSeat12*#
				 */
				if (strings[0].equals("0")) { // 0 for query
					if (!strings[1].equals("$")) {
						switch (strings[6]) {
						case "ALL":
							flightEntities = dbOperator.queryFlightByNoAndDate(
									strings[1], strings[5], "airline" + tablename);
							break;

						default:
							flightEntities = dbOperator
									.queryFlightByNoAndDateAndClass(strings[1], strings[5], 
											strings[6], "airline" + tablename);
							break;
						}

						if (flightEntities == null) {
							Util.debug("no match");
							printStream.println("$#");
						} else {
							String tempAirline = "";
							switch (tablename) {
							case 1:
								tempAirline = "Air China";
								break;
							case 2:
								tempAirline = "Qantas";
								break;
							case 3:
								tempAirline = "China Eastern";
								break;
							default:
								break;
							}
							for (FlightEntity flightEntity : flightEntities) {
								// Util.debug(flightEntity.getSeatClass());
								String flightInfo = flightEntity
										.getFlightNumString()
										+ "*"
										+ tempAirline
										+ "*"
										+ flightEntity.getDeptCityString()
										+ "*"
										+ flightEntity.getDeptAirportString()
										+ "*"
										+ flightEntity.getDestCityString()
										+ "*"
										+ flightEntity.getDestAirportString()
										+ "*"
										+ flightEntity.getDeptDate()
										+ "*"
										+ flightEntity.getArrvDate()
										+ "*"
										+ flightEntity.getDeptTime()
										+ "*"
										+ flightEntity.getArrvTime()
										+ "*"
										+ flightEntity.getSeatClass()
										+ "*"
										+ flightEntity.getPriceBigDecimal()
										+ "*"
										+ flightEntity.getSeatAvalible()
										+ "*#";
								printStream.print(flightInfo);
							}
							printStream.println();

						}

					} else {
						if (strings[6].equals("ALL")) {
							flightEntities = dbOperator.queryFlightByLocaAndDate(
									strings[3], strings[4], strings[5], "airline"
											+ tablename);
						} else {
							flightEntities = dbOperator.queryByLocaAndDateAndClass(
								strings[3], strings[4], strings[5], strings[6], "airline"
										+ tablename);
						}
						
						if (flightEntities == null) {
							Util.debug("not matched!");
							printStream.println("$#");
						} else {
							String tempAirline = "";
							switch (tablename) {
							case 1:
								tempAirline = "Air China";
								break;
							case 2:
								tempAirline = "Qantas";
								break;
							case 3:
								tempAirline = "China Eastern";
								break;
							default:
								break;
							}
							for (FlightEntity flightEntity : flightEntities) {
								String flightInfo = flightEntity
										.getFlightNumString()
										+ "*"
										+ tempAirline
										+ "*"
										+ flightEntity.getDeptCityString()
										+ "*"
										+ flightEntity.getDeptAirportString()
										+ "*"
										+ flightEntity.getDestCityString()
										+ "*"
										+ flightEntity.getDestAirportString()
										+ "*"
										+ flightEntity.getDeptDate()
										+ "*"
										+ flightEntity.getArrvDate()
										+ "*"
										+ flightEntity.getDeptTime()
										+ "*"
										+ flightEntity.getArrvTime()
										+ "*"
										+ flightEntity.getSeatClass()
										+ "*"
										+ flightEntity.getPriceBigDecimal()
										+ "*"
										+ flightEntity.getSeatAvalible()
										+ "*#";
								printStream.print(flightInfo);
							}
							printStream.println();
						}

					}
				} else if (strings[0].equals("1")) { // 1 for booking
					synchronized (syncString) {
						Util.debug("Server get book request");
						if (dbOperator.book(strings[1], strings[3], strings[4],
								"airline" + tablename)) {
							printStream.println("booked");
							Util.debug("book successful!(run)");
						} else {
							printStream.println("failed");
						}
					}
				} else if(strings[0].equals("2")) { // 2 for undo
					synchronized (syncString) {
						Util.debug("Server get undo request");
						if (dbOperator.undo(strings[1], strings[3], strings[4],
								"airline" + tablename)) {
							printStream.println("booked");
							Util.debug("undo successful!(run)");
						} else {
							printStream.println("failed");
						}
					}
				}

			}
			incomingSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}