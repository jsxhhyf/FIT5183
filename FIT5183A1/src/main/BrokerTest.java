/**
 * BrokerTest.java
 * FIT5183A1
 * Phillip
 * 2014年3月11日 下午9:49:44
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
public class BrokerTest {
	public static final int PORT = 10010;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InetAddress address = null;
		try {
			address = InetAddress.getByName("localhost");
			Util.debug(address.toString());
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
			// Operation0*FlightNo1*Airline2*DepatingCity3*
			// DestinationCity4*DepatingDate5*Class6*#
			String string = "0*CA1220*0*C_CITY1*A_CITY1*2014-03-01*$*FIR*#";
			out.println(string);
			String temp = null;
			temp = reader.readLine();
			Util.debug(temp);
			out.println("BYE");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

}
