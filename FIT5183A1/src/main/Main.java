/**
 * 
 */
package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import util.Util;

/**
 * @author Phillip
 * 
 */
public class Main {

	public static final int PORT = 10011;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Socket socket = null;
		try {
			socket = new Socket(address, PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InputStream in = null;
		PrintStream out = null;
		try {
			in = socket.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			out = new PrintStream(socket.getOutputStream());
			// Operation0*FlightNo1*Airline2*DepatingCity3*DestinationCity4*DepatingDate5*DepartingTime6*Class7*#
			String string = "CA1220";
			out.print(string);
			String temp = null;
			temp = reader.readLine();
			Util.debug(temp);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

}
