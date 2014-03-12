/**
 * ClientGUI.java
 * FIT5183A1
 * Phillip
 * 2014年3月9日 下午3:41:41
 */
package view;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 * @author Phillip
 * 
 */
public class ClientGUI extends JFrame {

	String[] columnName = { "FlightNumber", "DepartingCity",
			"DepartingAirport", "DestinationCity", "DestinationAirport",
			"DepartingDate", "ArrivingDate", "DepartingTime", "ArrivingTime",
			"Class", "Price", "AvaliableSeats" };
	DefaultTableModel tableModel = new DefaultTableModel(columnName,0);
	
	private JLabel jLabel;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ClientGUI clientGUI = new ClientGUI();
	}

	public ClientGUI() {
		this.setTitle("MY FLIGHT BOOKING　CENTRE");
//		this.setLayout(new FlowLayout());
		this.setSize(300, 200);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(getJLabel(),null);
	}
	
	public JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(34,49,53,18);
			jLabel.setText("Flight Number");
		}
		return jLabel;
	}

}
