/**
 * Broker.java
 * FIT5183A1
 * Phillip
 * 2014年3月9日 下午3:16:08
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import util.Util;

/**
 * @author Phillip
 * 
 */
public class Broker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String temp = "0**abc".split("\\*")[1];
		if (temp.equals("")) {
			Util.debug("111");
		}

	}

}

/**
 * @author Phillip a thread to deal with the client request
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

			this.incomingString = reader.readLine();
			this.messageStrings = incomingString.split("\\*");
			/*
			 * the format of the incoming message is
			 * Operation0*FlightNo1*Airline2*DepatingCity3*DestinationCity4
			 * *DepatingDate5*DepartingTime6*Class7*#
			 */
			if (!messageStrings[2].equals("")) { // if it has a certain airline
				printer.print(forward(Integer.valueOf(messageStrings[2]),
						incomingString));
			} else {
				printer.print(forward(0, incomingString));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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