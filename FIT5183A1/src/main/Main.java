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
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		Util.debug(getCity2("FROMTO C_CITY1   jjfkk A_CITY1"));
	}
	
	public static String getCity2(String string) {
		Pattern datePattern = Pattern.compile("[AC]_CITY[0-9]");
		Matcher m = datePattern.matcher(string);
		if (m.find()) {
			m.find();
			return m.group(0);
		} else {
			return "$";
		}
	}

	public void function() {
		System.out.println("«Î ‰»Î£∫");
		boolean flag = false;
		int a = 0;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			a = scanner.nextInt();
		}
		
		
	}
}
