package quiz;

import java.util.ArrayList;
import java.util.Scanner;

public class cas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> Lista = new ArrayList<Integer>();
		Scanner in = new Scanner(System.in);
		String string;
		while ((string = in.next()) != "\n") {
			Lista.add(Integer.parseInt(string));
		}
		in.close();
		System.out.println(Lista.size());
		for (int i:Lista) {
			System.out.println(i);
		}
		
	}

}
