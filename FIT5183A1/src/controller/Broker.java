/**
 * Broker.java
 * FIT5183A1
 * Phillip
 * 2014��3��9�� ����3:16:08
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import util.Util;

/**
 * @author Phillip
 * 
 */
public class Broker {
	
	public static int BROKER_PORT = 10010;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(BROKER_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		Util.debug("Broke Server is running!");
		while (true) {
			Socket incoming = null;
			try {
				incoming = serverSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			new Handler(incoming).start();
		}
	}

}

/**
 * @author Phillip 
 * a thread to deal with the client request
 */
class Handler extends Thread {

	public final int[] SERVER_PORT = { 10011, 10012, 10013 };
	InetAddress SERVER_ADDRESS = null;
	Socket incomingSocket;
	Socket[] outgoingSockets = new Socket[3];
	BufferedReader reader = null;
	PrintStream printer = null;
	BufferedReader[] bufferedReaders = new BufferedReader[3];
	PrintStream[] printStreams = new PrintStream[3];
	BufferedReader bufferedReader = null;
	PrintStream printStream = null;
	String incomingString;
	String[] messageStrings;

	/**
	 * @param socket
	 * constructor with the client socket as the parameter
	 * initialize the sockets and input and output objects
	 */
	public Handler(Socket socket) {
		// TODO Auto-generated constructor stub
		this.incomingSocket = socket;
		try {
			SERVER_ADDRESS = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			for (int i = 0; i < 3; i++) {
				outgoingSockets[i] = new Socket(SERVER_ADDRESS, SERVER_PORT[i]);
				
				printStream = new PrintStream(outgoingSockets[i].getOutputStream());

				bufferedReader = new BufferedReader(new InputStreamReader(
						outgoingSockets[i].getInputStream()));
			}

			reader = new BufferedReader(new InputStreamReader(
					incomingSocket.getInputStream()));

			printer = new PrintStream(incomingSocket.getOutputStream());

			

			for (int i1 = 0; i1 < 3; i1++) {
				bufferedReaders[i1] = new BufferedReader(new InputStreamReader(
						outgoingSockets[i1].getInputStream()));
				printStreams[i1] = new PrintStream(
						outgoingSockets[i1].getOutputStream());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		try {
			
			while (true) {
				this.incomingString = reader.readLine();
				if (incomingString.trim().equalsIgnoreCase("BYE")) {
					break;
				}
				this.messageStrings = incomingString.split("\\*");
				/*
				 * the format of the incoming message is
				 * Operation0*FlightNo1*Airline2*DepatingCity3*DestinationCity4
				 * *DepatingDate5*DepartingTime6*Class7*#
				 */
				if (!messageStrings[2].equals("")) { // if it has a certain
														// airline
					printer.print(forward(Integer.valueOf(messageStrings[2]),
							incomingString));
				} else {
					printer.print(forward(0, incomingString));
				}
			}
			incomingSocket.close();
			for( int i = 0; i < 3; i++) {
				outgoingSockets[i].close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param i
	 * @param msgString
	 * @return the query result
	 * forward the query string to the correct database server
	 */
	public String forward(int i, String msgString) {
		if (i != 0) {
			String tempString = "";
			printStream.print(msgString);
			try {
				tempString = bufferedReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tempString;

		} else {
			String tempString = "";
			for(int i1 = 0; i1 < 3; i1++) {
				printStreams[i1].print(msgString);
				try {
					tempString += bufferedReaders[i1].readLine();
					tempString += "*";
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return tempString;
		}
	}

}