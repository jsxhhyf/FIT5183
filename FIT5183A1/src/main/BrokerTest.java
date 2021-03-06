/**
 * BrokerTest.java
 * FIT5183A1
 * Phillip
 * 2014��3��11�� ����9:49:44
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
//	public static final int PORT = 10010;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int i = 0; i < 10; i++) {
			new MultiThread().start();
		}
	}

}

class MultiThread extends Thread {
	final int PORT = 10010;
	public void run() {
		Util.debug(Thread.currentThread().getName() + " starting...");
		InetAddress address = null;
		try {
			address = InetAddress.getByName("localhost");
//			Util.debug(address.toString());
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
			//String string = "1*CA1220*1*C_CITY1*A_CITY1*2014-03-01*$*FIR*#";
			String string = "1*CA1220*1*2014-03-01*FIR*#";
			Util.debug(Thread.currentThread().getName() + " ready to send request");
			out.println(string);
			Util.debug(Thread.currentThread().getName() + " request sent");
			String temp = null;
			temp = reader.readLine();
			Util.debug(Thread.currentThread().getName() + " " + temp);
			out.println("BYE");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
