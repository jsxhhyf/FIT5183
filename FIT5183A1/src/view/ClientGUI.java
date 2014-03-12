/**
 * ClientGUI.java
 * FIT5183A1
 * Phillip
 * 2014年3月9日 下午3:41:41
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.Client;

/**
 * @author Phillip
 * 
 */
public class ClientGUI extends JFrame {

	private String[] columnName = { "FlightNumber", "DepartingCity",
			"Depart_Airport", "DestinationCity", "Dest_Airport",
			"DepartingDate", "ArrivingDate", "DepartingTime", "ArrivingTime",
			"Class", "Price", "AvaliableSeats" };
	private Object[][] cellData = null;
	private DefaultTableModel tableModel = new DefaultTableModel(cellData,
			columnName);
	private String[] timeItems = { "00:00 - 06:00", "06:00 - 12:00",
			"12:00 - 18:00", "18:00 - 24:00" };
	private String[] classItems = { "ECO", "BUS", "FIR" };
	private String[] airlineItems = { "Airline1", "Airline2", "Airline3", "ALL" };

	private Client client;

	private JLabel flJLabel;
	private JLabel dpctJLabel;
	private JLabel dsctJLabel;
	private JLabel dpdtJLabel;
	private JLabel dptmJLabel;
	private JLabel clJLabel;
	private JLabel alJLabel;

	private JTextField flJTextField;
	private JTextField dpctJTextField;
	private JTextField dsctJTextField;
	private JTextField dpdtJTextField;
	private JComboBox<String> timeJComboBox;
	private JComboBox<String> classJComboBox;
	private JComboBox<String> airlineJComboBox;

	private JTable dataJTable;

	private JScrollPane jScrollPane;

	private JButton queryJButton;
	private JButton bookJButton;

	private JPanel rightJPanel;
	private JPanel leftJPanel;

	// private JPanel leftUpJPanel;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new ClientGUI();
	}

	public ClientGUI() {

		// client = new Client();

		this.setTitle("MY FLIGHT BOOKING CENTRE");
		this.setLayout(new BorderLayout());
		this.setSize(1500, 600);
		// this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		flJLabel = new JLabel("Flight Number");
		dpdtJLabel = new JLabel("Departing Date");
		dpctJLabel = new JLabel("Departing City");
		dsctJLabel = new JLabel("Destination City");
		dptmJLabel = new JLabel("Departing Time");
		clJLabel = new JLabel("Class");
		alJLabel = new JLabel("Airline");

		flJTextField = new JTextField(10);
		dpdtJTextField = new JTextField(10);
		dpctJTextField = new JTextField(10);
		dsctJTextField = new JTextField(10);

		timeJComboBox = new JComboBox<String>(timeItems);
		classJComboBox = new JComboBox<String>(classItems);
		airlineJComboBox = new JComboBox<String>(airlineItems);

		rightJPanel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		leftJPanel = new JPanel();
		leftJPanel.setLayout(new BorderLayout());
		// leftUpJPanel = new JPanel();

		rightJPanel.setVisible(true);
		leftJPanel.setVisible(true);
		// leftUpJPanel.setVisible(true);

		dataJTable = new JTable(tableModel);
		jScrollPane = new JScrollPane(dataJTable);

		bookJButton = new JButton("BOOK NOW!");
		queryJButton = new JButton("QUERY!");

		leftJPanel.add(jScrollPane, "North");
		leftJPanel.add(bookJButton, "Center");

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(20, 10, 20, 10);
		rightJPanel.add(flJLabel, constraints);

		constraints.gridx = 1;
		rightJPanel.add(flJTextField, constraints);

		constraints.gridx = 2;
		rightJPanel.add(dpdtJLabel, constraints);

		constraints.gridx = 3;
		rightJPanel.add(dpdtJTextField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		rightJPanel.add(dpctJLabel, constraints);

		constraints.gridx = 1;
		rightJPanel.add(dpctJTextField, constraints);

		constraints.gridx = 2;
		rightJPanel.add(dsctJLabel, constraints);

		constraints.gridx = 3;
		rightJPanel.add(dsctJTextField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		rightJPanel.add(dptmJLabel, constraints);

		constraints.gridx = 1;
		rightJPanel.add(timeJComboBox, constraints);

		constraints.gridx = 2;
		rightJPanel.add(clJLabel, constraints);

		constraints.gridx = 3;
		rightJPanel.add(classJComboBox, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		rightJPanel.add(alJLabel, constraints);

		constraints.gridx = 1;
		rightJPanel.add(airlineJComboBox, constraints);

		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 4;
		constraints.gridheight = GridBagConstraints.REMAINDER;
		constraints.weighty = 1;
		constraints.insets = new Insets(20, 0, 0, 0);
		rightJPanel.add(queryJButton, constraints);

		this.add(leftJPanel, "Center");
		this.add(rightJPanel, "East");

		this.setResizable(false);
		this.setVisible(true);

		queryJButton.addActionListener(new queryButtonHandler());
		bookJButton.addActionListener(new bookButtonHandler());
	}

	private class queryButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String tempString = validateQueryInput();
			if (tempString != null) {
				client.query(tempString);
			}
			tempString = client.getResultString();
			
//			fillTable(strings);
		}
	}

	private class bookButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

		}
	}

	/**
	 * validate the input and return the query string which is sent to Broker
	 * server
	 * 
	 * @return query string
	 */
	public String validateQueryInput() {

		// Operation0*FlightNo1*Airline2*DepartingCity3*DestinationCity4*
		// DepatingDate5*Class6*#
		String tempFlightNoString = flJTextField.getText();
		String tempAirlineString = airlineJComboBox.getSelectedItem()
				.toString();
		String tempDepartingCityString = dpctJTextField.getText();
		String tempDestinationCityString = dsctJTextField.getText();
		String tempDepartingDateString = dpdtJTextField.getText();
		classJComboBox.getSelectedItem().toString();

		if (tempFlightNoString.length() == 6) {
			switch (tempAirlineString) {
			case "Airline1":
				return "0*" + tempFlightNoString + "*1* * * * *";
			case "Airline2":
				return "0*" + tempFlightNoString + "*2* * * * *";
			case "Airline3":
				return "0*" + tempFlightNoString + "*3* * * * *";
			default:
				return "0*" + tempFlightNoString + "*0* * * * *";
			}
		} else if (!tempFlightNoString.equals("")
				&& tempFlightNoString.length() != 6) {
			return null;
		} else if (tempFlightNoString.equals("")) {
			if (tempDepartingCityString.equals("")
					|| tempDestinationCityString.equals("")) {
				return null;
			} else {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					format.parse(tempDepartingDateString);
				} catch (ParseException e) {
					return null;
				}
			}
			switch (tempAirlineString) {
			case "Airline1":
				return "0* *1*" + tempDepartingCityString
						+ tempDestinationCityString + tempDepartingDateString
						+ "* *#";
			case "Airline2":
				return "0* *2*" + tempDepartingCityString
						+ tempDestinationCityString + tempDepartingDateString
						+ "* *#";
			case "Airline3":
				return "0* *3*" + tempDepartingCityString
						+ tempDestinationCityString + tempDepartingDateString
						+ "* *#";
			default:
				return "0* *0*" + tempDepartingCityString
						+ tempDestinationCityString + tempDepartingDateString
						+ "* *#";
			}
		} else {
			return null;
		}
	}

	public void fillTable(String[][] strings) {
		
		DefaultTableModel defaultTableModel = (DefaultTableModel) dataJTable
				.getModel();
		defaultTableModel.setRowCount(0);
		
		for (int i = 0; i < strings.length; i ++) {
			tableModel.addRow(strings[i]);
		}
		
		dataJTable.updateUI();
	}
}
