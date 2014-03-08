package syncAndSocket;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ResourceServer {
	private static ServerSocket serverSocket;
	private static final int PORT = 1234;

	public static void main(String[] args) throws IOException {
		try {
			serverSocket = new ServerSocket(PORT);
		}
		catch (IOException e) {
			System.out.println("\nUnable to set up port!");
			System.exit(1);
		}

		Resource item    = new Resource(1);
		Producer produce = new Producer(item);
		produce.start();

		do {
			Socket client = serverSocket.accept();
			System.out.println("\nNew client accept.\n");

			ClientThread handler = new ClientThread(client,item);
			handler.start();
		}
		while(true);
	}
}

class Producer extends Thread {
	private Resource item;
	public Producer(Resource resource) {
		item = resource;
	}

	public void run() {
		int pause;
		int newLevel;

		do {
			try {
				newLevel = item.addOne();
				System.out.println("<Producer> New level: " + newLevel);
				pause    = (int)(Math.random() * 5000);
				sleep(pause);
			}
			catch (InterruptedException interruptEx) {
				System.out.println(interruptEx);
			}
		}
		while (true);
	}
}

class Resource {
	private int numResources;
	private final int MAX = 5;

	public Resource(int startLevel) {
		numResources = startLevel;
	}

	public int getLevel() {
		return numResources;
	}

	public synchronized int addOne() {
		try {
			while (numResources >= MAX) {
				wait();
			}
			numResources++;
			notifyAll();
		}
		catch (InterruptedException interruptEx) {
			System.out.println(interruptEx);
		}
		return numResources;
	}

	public synchronized int takeOne() {
		try {
			while (numResources == 0) {
				wait();
			}
			numResources--;
			System.out.println("takeOne! " + Thread.currentThread().getName());
		}
		catch (InterruptedException interruptEx) {
			System.out.println(interruptEx);
		}
		return numResources;
	}
}

class ClientThread extends Thread {
	private Socket client;
	private Resource item;
	private Scanner input;
	private PrintWriter output;
	
	public ClientThread(Socket socket, Resource resource) {
		// TODO Auto-generated constructor stub
		this.client = socket;
		this.item = resource;
		
		try {
			this.input = new Scanner(this.client.getInputStream());
			this.output = new PrintWriter(this.client.getOutputStream(),true);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	public void run() {
		String requestString = "";
		do {
			requestString = this.input.nextLine();
			if (requestString.equals("1")) {
				item.takeOne();
				output.println("Request granted.");
			}
		} while (!requestString.equals("0"));
		try {
			System.out.println("Closing down connection " + getName());
			client.close();
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Unable to close connection to client!");
		}
	}
}