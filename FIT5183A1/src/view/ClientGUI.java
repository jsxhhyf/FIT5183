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

import javax.print.DocFlavor.STRING;
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

	private String[] columnName = { "FlightNumber", "Airline", "DepartingCity",
			"Depart_Airport", "DestinationCity", "Dest_Airport",
			"DepartingDate", "ArrivingDate", "DepartingTime", "ArrivingTime",
			"Class", "Price", "AvaliableSeats" };
	private Object[][] cellData = null;
	private DefaultTableModel tableModel = new DefaultTableModel(cellData,
			columnName);
	private String[] timeItems = { "00:00 - 06:00", "06:00 - 12:00",
			"12:00 - 18:00", "18:00 - 24:00" };
	private String[] classItems = { "ECO", "BUS", "FIR", "ALL" };
	private String[] airlineItems = { "Airline1", "Airline2", "Airline3", "ALL" };
	private String[] cityItems = { "C_CITY1", "C_CITY2", "C_CITY3", "C_CITY4",
			"C_CITY5", "C_CITY6", "C_CITY7", "C_CITY8", "C_CITY9", "C_CITY10",
			"A_CITY1", "A_CITY2", "A_CITY3", "A_CITY4", "A_CITY5", "A_CITY6",
			"A_CITY7", "A_CITY8", "A_CITY9", "A_CITY10" };
	private String[] yearItems = { "2014" };
	private String[] monthItems = { "03" };

	private Client client;

	private JLabel flJLabel;
	private JLabel dpctJLabel;
	private JLabel dsctJLabel;
	private JLabel dpdtJLabel;
	private JLabel dptmJLabel;
	private JLabel clJLabel;
	private JLabel alJLabel;

	private JTextField flJTextField;
	// private JTextField dpctJTextField;
	// private JTextField dsctJTextField;
	// private JTextField dpdtJTextField;
	private JComboBox<String> timeJComboBox;
	private JComboBox<String> classJComboBox;
	private JComboBox<String> airlineJComboBox;
	private JComboBox<String> dpctJComboBox;
	private JComboBox<String> dsctJComboBox;
	private JComboBox<String> dpyrJComboBox;
	private JComboBox<String> dpmtJComboBox;
	private JComboBox<String> dpdyJComboBox;

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

		client = new Client();

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
		// dpdtJTextField = new JTextField(10);
		// dpctJTextField = new JTextField(10);
		// dsctJTextField = new JTextField(10);

		timeJComboBox = new JComboBox<String>(timeItems);
		classJComboBox = new JComboBox<String>(classItems);
		airlineJComboBox = new JComboBox<String>(airlineItems);
		dpctJComboBox = new JComboBox<String>(cityItems);
		dsctJComboBox = new JComboBox<String>(cityItems);
		dpyrJComboBox = new JComboBox<String>(yearItems);
		dpmtJComboBox = new JComboBox<String>(monthItems);
		dpdyJComboBox = new JComboBox<String>();
		for (int i = 1; i < 32; i++) {
			dpdyJComboBox.addItem("" + i);
		}

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
		rightJPanel.add(dpyrJComboBox, constraints);

		constraints.gridx = 4;
		rightJPanel.add(dpmtJComboBox, constraints);

		constraints.gridx = 5;
		rightJPanel.add(dpdyJComboBox, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1;
		rightJPanel.add(dpctJLabel, constraints);

		constraints.gridx = 1;
		rightJPanel.add(dpctJComboBox, constraints);

		constraints.gridx = 2;
		rightJPanel.add(dsctJLabel, constraints);

		constraints.gridx = 3;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		rightJPanel.add(dsctJComboBox, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		rightJPanel.add(dptmJLabel, constraints);

		constraints.gridx = 1;
		rightJPanel.add(timeJComboBox, constraints);

		constraints.gridx = 2;
		rightJPanel.add(clJLabel, constraints);

		constraints.gridx = 3;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		rightJPanel.add(classJComboBox, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		rightJPanel.add(alJLabel, constraints);

		constraints.gridx = 1;
		rightJPanel.add(airlineJComboBox, constraints);

		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 6;
		constraints.gridheight = GridBagConstraints.REMAINDER;
		constraints.weighty = 1;
		constraints.insets = new Insets(155, 0, 0, 0);
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
			String[] rowStrings = tempString.split("#");
			String[][] strings = new String[rowStrings.length][13];
			int i = 0;
			for (String row : rowStrings) {
				strings[i] = row.split("\\*");
				i++;

			}

			fillTable(strings);
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
		String tempDepartingCityString = dpctJComboBox.getSelectedItem()
				.toString();
		String tempDestinationCityString = dsctJComboBox.getSelectedItem()
				.toString();
		String tempDepartingDateString = dpyrJComboBox.getSelectedItem()
				.toString()
				+ "-"
				+ dpmtJComboBox.getSelectedItem().toString()
				+ "-" + dpdyJComboBox.getSelectedItem().toString();
		classJComboBox.getSelectedItem().toString();

		if (tempFlightNoString.length() == 6) {
			switch (tempAirlineString) {
			case "Airline1":
				return "0*" + tempFlightNoString + "*1*$*$*$*$*";
			case "Airline2":
				return "0*" + tempFlightNoString + "*2*$*$*$*$*";
			case "Airline3":
				return "0*" + tempFlightNoString + "*3*$*$*$*$*";
			default:
				return "0*" + tempFlightNoString + "*0*$*$*$*$*";
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
				return "0*$*1*" + tempDepartingCityString + "*"
						+ tempDestinationCityString + "*" + tempDepartingDateString
						+ "*$*#";
			case "Airline2":
				return "0*$*2*" + tempDepartingCityString + "*"
						+ tempDestinationCityString + "*" + tempDepartingDateString
						+ "*$*#";
			case "Airline3":
				return "0*$*3*" + tempDepartingCityString + "*"
						+ tempDestinationCityString + "*" + tempDepartingDateString
						+ "*$*#";
			default:
				return "0*$*0*" + tempDepartingCityString + "*"
						+ tempDestinationCityString + "*" + tempDepartingDateString
						+ "*$*#";
			}
		} else {
			return null;
		}
	}

	public void fillTable(String[][] strings) {

		DefaultTableModel defaultTableModel = (DefaultTableModel) dataJTable
				.getModel();
		defaultTableModel.setRowCount(0);

		for (int i = 0; i < strings.length; i++) {
			if (strings[i][0].equals("$")) {
				continue;
			}
			tableModel.addRow(strings[i]);
		}

		dataJTable.updateUI();
	}
}
