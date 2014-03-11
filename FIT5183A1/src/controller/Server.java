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
		String tablenameString = "airline" + args[0];
		int PORT = 10010 + Integer.valueOf(args[0]);
		
		ServerSocket serverSocket = null;
		try {
			serverSocket=new ServerSocket(PORT);
			Util.debug(PORT);Util.debug(serverSocket.getInetAddress().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Util.debug("Airline " + args[0] + " database server is running!");
		while (true) {
			Socket incoming = null;
			try {
				incoming = serverSocket.accept();
				Util.debug(1);
				Util.debug(incoming.getInetAddress().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			new SocketHandler(incoming, tablenameString).start();
		}
	}

}

/**
 * @author Phillip
 *
 */
class SocketHandler extends Thread {
	
	String tablename = "";
	Socket incomingSocket;
	DB_Operator dbOperator = new DB_Operator();
	FlightEntity flightEntity = null;
	
	String syncString = new String();
	
	SocketHandler(Socket incoming, String tableName) {
		this.incomingSocket = incoming;
		this.tablename = tableName;
		Util.debug(2);
	}
	
	public void run() {
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(incomingSocket.getInputStream()));
			PrintStream printStream = new PrintStream(incomingSocket.getOutputStream());
			
			while (true) {
				Util.debug(3);
				String messageString = reader.readLine();
				Util.debug(messageString);

				if (messageString.trim().equalsIgnoreCase("BYE")) {
					break;
				}
				String[] strings = messageString.split("\\*");
				/*
				 * the format of the incoming message is
				 * Operation0*FlightNo1*Airline2*DepatingCity3*DestinationCity4
				 * *DepatingDate5*DepartingTime6*Class7*#
				 */
				if (strings[0].equals("0")) { // 0 for query
					if (!strings[1].equals("")) {
						flightEntity = dbOperator.queryFlighByNo(strings[1],
								tablename);
						printStream.println(flightEntity.getSeatAvalible());
					} else {
						flightEntity = dbOperator.queryFlightByLocaAndDate(
								strings[3], strings[4], strings[5], tablename);
						printStream.println(flightEntity.getSeatAvalible());
					}
				} else if (strings[0].equals("1")){ // 1 for booking
					synchronized (syncString) {
						if (dbOperator.book(strings[1], strings[7], tablename)) {
							printStream.println("booked");
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