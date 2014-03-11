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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Util.debug("Airline " + args[0] + " database server is running!");
		while (true) {
			Socket incoming = null;
			try {
				incoming = serverSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			new SocketHandler(incoming, tablenameString);
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
	
	SocketHandler(Socket incoming, String tableName) {
		this.incomingSocket = incoming;
		this.tablename = tableName;
	}
	
	public void run() {
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(incomingSocket.getInputStream()));
			PrintStream printStream = new PrintStream(incomingSocket.getOutputStream());
			
			while (true) {
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
				if (!strings[0].equals("")) {
					flightEntity = dbOperator.queryFlighByNo(strings[0],
							tablename);
					printStream.print(flightEntity.getSeatAvalible());
				} else {
					flightEntity = dbOperator.queryFlightByLocaAndDate(
							strings[3], strings[4], strings[5], tablename);
					printStream.print(flightEntity.getSeatAvalible());
				}
			}
			incomingSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}