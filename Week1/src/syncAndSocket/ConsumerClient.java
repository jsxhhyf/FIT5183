package syncAndSocket;

import java.io.*;
import java.net.*;
import java.util.*;

public class ConsumerClient extends Thread{
	private static InetAddress hostAddress;
	private static final int PORT = 1234;
	private Scanner networkInputScanner;
	private PrintWriter networkOutputPrintWriter;
	
	public static void main(String[] arg) {
		try {
			hostAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO: handle exception
			System.out.println("\nHost ID not found!\n");
			System.exit(1);
		}
		for(int i=0;i<5;i++) {
			new ConsumerClient().start();
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		
	}
	
	public void run() {
		Socket socket = null;
		String response;
		try {
			socket = new Socket(hostAddress,PORT);
			networkInputScanner = new Scanner(socket.getInputStream());
			networkOutputPrintWriter = new PrintWriter(socket.getOutputStream(),true);
			networkOutputPrintWriter.println("1");
			response = networkInputScanner.nextLine();
			System.out.println("Server> " + response);
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			try {
				System.out.println("\nClosing connection...");
				networkOutputPrintWriter.println("0");
				socket.close();
			} catch (IOException e2) {
				// TODO: handle exception
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	}
}

