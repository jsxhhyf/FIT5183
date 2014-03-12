/**
 * Client.java
 * FIT5183A1
 * Phillip
 * 2014年3月9日 下午3:21:09
 */
package controller;

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
public class Client {

	private final int PORT = 10010;
	private Socket clientSocket = null;
	private BufferedReader reader;
	private PrintStream printer;
	private String resultString;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub


	}

	/**
	 * default constructor initialize the socket and input output streams
	 */
	public Client() {

		InetAddress address = null;
		try {
			address = InetAddress.getByName("localhost");
			Util.debug(address.toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			setClientSocket(new Socket(address, PORT));
			reader = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			printer = new PrintStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @return the reader
	 */
	public BufferedReader getReader() {
		return reader;
	}

	/**
	 * @param reader the reader to set
	 */
	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	/**
	 * @return the printer
	 */
	public PrintStream getPrinter() {
		return printer;
	}

	/**
	 * @param printer the printer to set
	 */
	public void setPrinter(PrintStream printer) {
		this.printer = printer;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	/**
	 * @return the resultString
	 */
	public String getResultString() {
		return resultString;
	}

	/**
	 * @param resultString the resultString to set
	 */
	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public void query(String queryString) {
		printer.println(queryString);
		try {
			setResultString(reader.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
