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
		
		Util.debug(getCity1("FROMTO AU_Sydney  CN_Shanghai  jjfkk   PuDong_Airport"));
	}
	
	public static String getCity1(String string) {
		Pattern datePattern = Pattern.compile("(AU|CN)_\\w+");
		Matcher m = datePattern.matcher(string);
		if (m.find()) {
			return m.group(0);
		} else {
			return "$";
		}
	}
}
