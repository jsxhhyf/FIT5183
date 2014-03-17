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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.print.DocFlavor.STRING;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import util.Util;
import controller.Client;

/**
 * @author Phillip
 * 
 */
public class ClientGUI extends JFrame {

	private String[] columnName = { "FlightNumber", "Airline", "City", "Date",
			"Class", "Price", "Vacancy" };
	private Object[][] cellData = null;
	private DefaultTableModel tableModel = new DefaultTableModel(cellData,
			columnName);
	private DefaultTableModel tableModel2 = new DefaultTableModel(cellData,
			columnName);
	private String[] timeItems = { "ALL", "00:00 - 06:00", "06:00 - 12:00",
			"12:00 - 18:00", "18:00 - 24:00" };
	private String[] classItems = { "ALL", "ECO", "BUS", "FIR" };
	private String[] airlineItems = { "Air China", "Qantas", "China Eastern" };
	private String[] cityItems = { "CN_Shanghai", "CN_Beijing", "CN_Guangzhou",
			"CN_Shenzhen", "AU_Sydney", "AU_Canberra", "AU_Melbourne",
			"AU_Adelaide" };
	private String[] city2Items = { "AU_Sydney", "AU_Canberra", "AU_Melbourne",
			"AU_Adelaide", "CN_Shanghai", "CN_Beijing", "CN_Guangzhou",
			"CN_Shenzhen" };
	private String[] yearItems = { "2014" };
	private String[] monthItems = { "03" };

	private Client client;

	private JLabel flJLabel;
	private JLabel fl2JLabel;
	private JLabel dpctJLabel;
	private JLabel dsctJLabel;
	private JLabel dpdtJLabel;
	private JLabel bcdtJLabel;
	private JLabel clJLabel;
	private JLabel cl2JLabel;
	private JLabel alJLabel;
	// private JLabel al2JLabel;

	private JTextField flJTextField;
	private JTextField fl2JTextField;
	// private JTextField dpctJTextField;
	// private JTextField dsctJTextField;
	// private JTextField dpdtJTextField;
	private JComboBox<String> bcyrJComboBox;
	private JComboBox<String> bcmtJComboBox;
	private JComboBox<String> bcdyJComboBox;
	private JComboBox<String> classJComboBox;
	private JComboBox<String> class2JComboBox;
	private JComboBox<String> airlineJComboBox;
	// private JComboBox<String> airline2JComboBox;
	private JComboBox<String> dpctJComboBox;
	private JComboBox<String> dsctJComboBox;
	private JComboBox<String> dpyrJComboBox;
	private JComboBox<String> dpmtJComboBox;
	private JComboBox<String> dpdyJComboBox;

	private JTable dataJTable;
	private JTable data2JTable;

	private JScrollPane jScrollPane;
	private JScrollPane jScroll2Pane;

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
		this.setSize(1500, 1000);
		// this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		flJLabel = new JLabel("Flight Number");
		fl2JLabel = new JLabel("Flight Number");
		dpdtJLabel = new JLabel("Departing Date");
		dpctJLabel = new JLabel("Departing City");
		dsctJLabel = new JLabel("Destination City");
		bcdtJLabel = new JLabel("Returning Date");
		clJLabel = new JLabel("Class");
		cl2JLabel = new JLabel("Class");
		alJLabel = new JLabel("Airline");
		// al2JLabel = new JLabel("Airline");

		flJTextField = new JTextField(10);
		fl2JTextField = new JTextField(10);
		flJTextField.setText("CA0835");
		fl2JTextField.setText("AC0612");
		// dpdtJTextField = new JTextField(10);
		// dpctJTextField = new JTextField(10);
		// dsctJTextField = new JTextField(10);

		bcyrJComboBox = new JComboBox<String>(yearItems);
		bcmtJComboBox = new JComboBox<String>(monthItems);
		bcdyJComboBox = new JComboBox<String>();
		classJComboBox = new JComboBox<String>(classItems);
		class2JComboBox = new JComboBox<String>(classItems);
		airlineJComboBox = new JComboBox<String>(airlineItems);
		// airline2JComboBox = new JComboBox<String>(airlineItems);
		dpctJComboBox = new JComboBox<String>(cityItems);
		dsctJComboBox = new JComboBox<String>(city2Items);
		dpyrJComboBox = new JComboBox<String>(yearItems);
		dpmtJComboBox = new JComboBox<String>(monthItems);
		dpdyJComboBox = new JComboBox<String>();
		for (int i = 1; i < 32; i++) {
			dpdyJComboBox.addItem("" + i);
			bcdyJComboBox.addItem("" + i);
		}

		rightJPanel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		leftJPanel = new JPanel();
		leftJPanel.setLayout(new GridBagLayout());
		// leftUpJPanel = new JPanel();

		rightJPanel.setVisible(true);
		leftJPanel.setVisible(true);
		// leftUpJPanel.setVisible(true);

		dataJTable = new JTable(tableModel) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		dataJTable.setRowHeight(40);
		TableColumnModel tcm = dataJTable.getColumnModel();
		TableColumn tc = tcm.getColumn(2);
		tc.setPreferredWidth(195);
		tc.setCellRenderer(new TextAreaCellRenderer());
		tc = tcm.getColumn(3);
		tc.setPreferredWidth(160);
		tc.setCellRenderer(new TextAreaCellRenderer());
		jScrollPane = new JScrollPane(dataJTable);

		data2JTable = new JTable(tableModel2) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		data2JTable.setRowHeight(40);
		TableColumnModel tcm2 = data2JTable.getColumnModel();
		TableColumn tc2 = tcm2.getColumn(2);
		tc2.setPreferredWidth(195);
		tc2.setCellRenderer(new TextAreaCellRenderer());
		tc2 = tcm2.getColumn(3);
		tc2.setPreferredWidth(160);
		tc2.setCellRenderer(new TextAreaCellRenderer());
		jScroll2Pane = new JScrollPane(data2JTable);

		bookJButton = new JButton("BOOK NOW!");
		queryJButton = new JButton("QUERY!");

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.ipadx = 775;
		constraints.ipady = 435;
		// constraints.weighty = 0.05;
		leftJPanel.add(jScrollPane, constraints);
		constraints.gridy = 1;
		leftJPanel.add(jScroll2Pane, constraints);
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.ipady = 20;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		leftJPanel.add(bookJButton, constraints);

		// ------------------------------------------------- line 1

		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weighty = 1;
		constraints.insets = new Insets(20, 10, 20, 10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		rightJPanel.add(flJLabel, constraints);

		constraints.gridx = 1;
		constraints.gridwidth = 3;
		rightJPanel.add(flJTextField, constraints);

		constraints.gridx = 4;
		constraints.gridwidth = 1;
		rightJPanel.add(fl2JLabel, constraints);

		constraints.gridx = 5;
		constraints.gridwidth = 3;
		rightJPanel.add(fl2JTextField, constraints);

		// ------------------------------------------------- line 2

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		rightJPanel.add(alJLabel, constraints);

		constraints.gridx = 1;
		constraints.gridwidth = 3;
		rightJPanel.add(airlineJComboBox, constraints);

		// constraints.gridx = 4;
		// constraints.gridwidth = 1;
		// rightJPanel.add(al2JLabel, constraints);
		//
		// constraints.gridx = 5;
		// constraints.gridwidth = 3;
		// rightJPanel.add(airline2JComboBox, constraints);

		// --------------------------------------------------- line 3

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		rightJPanel.add(dpctJLabel, constraints);

		constraints.gridx = 1;
		constraints.gridwidth = 3;
		rightJPanel.add(dpctJComboBox, constraints);

		constraints.gridx = 4;
		constraints.gridwidth = 1;
		rightJPanel.add(dsctJLabel, constraints);

		constraints.gridx = 5;
		constraints.gridwidth = 3;
		rightJPanel.add(dsctJComboBox, constraints);

		// --------------------------------------------------- line 4

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		rightJPanel.add(dpdtJLabel, constraints);

		constraints.gridx = 1;
		rightJPanel.add(dpyrJComboBox, constraints);

		constraints.gridx = 2;
		rightJPanel.add(dpmtJComboBox, constraints);

		constraints.gridx = 3;
		rightJPanel.add(dpdyJComboBox, constraints);

		constraints.gridx = 4;
		constraints.gridwidth = 1;
		rightJPanel.add(bcdtJLabel, constraints);

		constraints.gridx = 5;
		rightJPanel.add(bcyrJComboBox, constraints);

		constraints.gridx = 6;
		rightJPanel.add(bcmtJComboBox, constraints);

		constraints.gridx = 7;
		rightJPanel.add(bcdyJComboBox, constraints);

		// --------------------------------------------------- line 5

		constraints.gridx = 0;
		constraints.gridy = 4;
		rightJPanel.add(clJLabel, constraints);

		constraints.gridx = 1;
		constraints.gridwidth = 3;
		rightJPanel.add(classJComboBox, constraints);

		constraints.gridx = 4;
		constraints.gridwidth = 1;
		rightJPanel.add(cl2JLabel, constraints);

		constraints.gridx = 5;
		constraints.gridwidth = 3;
		rightJPanel.add(class2JComboBox, constraints);

		// --------------------------------------------------- line 6

		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 8;
		constraints.gridheight = GridBagConstraints.REMAINDER;
		constraints.weighty = 1;
		constraints.ipady = 20;
		constraints.insets = new Insets(600, 0, 0, 0);
		constraints.gridheight = GridBagConstraints.REMAINDER;
		rightJPanel.add(queryJButton, constraints);

		this.add(leftJPanel, "Center");
		this.add(rightJPanel, "East");

		this.setResizable(false);
		this.setVisible(true);

		queryJButton.addActionListener(new queryButtonHandler());
		bookJButton.addActionListener(new bookButtonHandler());
		
		dpdyJComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					bcdyJComboBox.removeAllItems();
					for(int i = Integer.parseInt(arg0.getItem().toString()); i < 32; i++) {
						bcdyJComboBox.addItem("" + i);
					}
					bcdyJComboBox.setSelectedItem(arg0.getItem());
				}
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				client.query("BYE");
			}
		});
	}

	private class queryButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String tempString = validateQueryInput1();
			String[][] strings = null;
			String[] rowStrings = null;
			String[] temp;

			if (tempString != null) {
				client.query(tempString);

				tempString = client.getResultString();

				Util.debug("从client返回的查询结果:" + tempString);

				rowStrings = tempString.split("#");
				strings = new String[rowStrings.length][7];

				int i = 0;
				for (String row : rowStrings) {
					temp = row.split("\\*");
					if (temp[0].equals("$")) {
						strings[i] = temp;
						i++;
						continue;
					}
					temp[2] = "FROM " + temp[2] + " " + temp[3] + " TO "
							+ temp[4] + " " + temp[5];
					temp[3] = "DEPT AT " + temp[6] + " " + temp[8]
							+ " ARRIVE AT " + temp[7] + " " + temp[9];
					temp[4] = temp[10];
					temp[5] = temp[11];
					temp[6] = temp[12];
					strings[i] = temp;
					i++;

				}

				for (String[] strings4 : strings) {
					for (String string : strings4) {
						Util.debug(string);
					}
				}
			}
			// =============================================== return ticket

			tempString = validateQueryInput2();
			String[][] strings2 = null;
			if (tempString != null) {
				client.query(tempString);

				tempString = client.getResultString();

				Util.debug("从client返回的查询结果:" + tempString);

				rowStrings = tempString.split("#");
				strings2 = new String[rowStrings.length][7];
				int i = 0;
				for (String row : rowStrings) {
					temp = row.split("\\*");
					if (temp[0].equals("$")) {
						strings2[i] = temp;
						i++;
						continue;
					}
					temp[2] = "FROM " + temp[2] + " " + temp[3] + " TO "
							+ temp[4] + " " + temp[5];
					temp[3] = "DEPT AT " + temp[6] + " " + temp[8]
							+ " ARRIVE AT " + temp[7] + " " + temp[9];
					temp[4] = temp[10];
					temp[5] = temp[11];
					temp[6] = temp[12];
					strings2[i] = temp;
					i++;

				}

				for (String[] strings3 : strings2) {
					for (String string : strings3) {
						Util.debug(string);
					}
				}
			}
			// ======================================================

			fillTable1(strings);
			fillTable2(strings2);
		}
	}

	private class bookButtonHandler implements ActionListener {
		private String flightEntry = "";
		private String flightEntry2 = "";

		public void actionPerformed(ActionEvent event) {
			if (dataJTable.getSelectedRow() == -1
					|| data2JTable.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null,
						"Please select the flight you want to book", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			for (int i = 0; i < 7; i++) {
				flightEntry += dataJTable.getValueAt(
						dataJTable.getSelectedRow(), i);
				flightEntry2 += data2JTable.getValueAt(
						data2JTable.getSelectedRow(), i);
				flightEntry += "*";
				flightEntry2 += "*";
			}

			Util.debug(flightEntry);

			BookUI bookUI = new BookUI(flightEntry, flightEntry2, client);

		}
	}

	/**
	 * validate the input and return the query string which is sent to Broker
	 * server
	 * 
	 * @return query string
	 */
	public String validateQueryInput1() {

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
		String classString = classJComboBox.getSelectedItem().toString();

		if (tempFlightNoString.length() == 6) {
			switch (tempAirlineString) {
			case "Air China":
				return "0*" + tempFlightNoString + "*1*$*$*"
						+ tempDepartingDateString + "*" + classString + "*#";
			case "Qantas":
				return "0*" + tempFlightNoString + "*2*$*$*"
						+ tempDepartingDateString + "*" + classString + "*#";
			case "China Eastern":
				return "0*" + tempFlightNoString + "*3*$*$*"
						+ tempDepartingDateString + "*" + classString + "*#";
			default:
				return "0*" + tempFlightNoString + "*0*$*$*"
						+ tempDepartingDateString + "*" + classString + "*#";
			}
		} else if (!tempFlightNoString.equals("")
				&& tempFlightNoString.length() != 6) {
			JOptionPane.showMessageDialog(null,
					"Please input the right flight number!", "Error",
					JOptionPane.ERROR_MESSAGE);
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
			case "Air China":
				return "0*$*1*" + tempDepartingCityString + "*"
						+ tempDestinationCityString + "*"
						+ tempDepartingDateString + "*" + classString + "*#";
			case "Qantas":
				return "0*$*2*" + tempDepartingCityString + "*"
						+ tempDestinationCityString + "*"
						+ tempDepartingDateString + "*" + classString + "*#";
			case "China Eastern":
				return "0*$*3*" + tempDepartingCityString + "*"
						+ tempDestinationCityString + "*"
						+ tempDepartingDateString + "*" + classString + "*#";
			default:
				return "0*$*0*" + tempDepartingCityString + "*"
						+ tempDestinationCityString + "*"
						+ tempDepartingDateString + "*" + classString + "*#";
			}
		} else {
			return null;
		}
	}

	public String validateQueryInput2() {

		// Operation0*FlightNo1*Airline2*DepartingCity3*DestinationCity4*
		// DepatingDate5*Class6*#
		String tempFlightNoString = fl2JTextField.getText();
		String tempAirlineString = airlineJComboBox.getSelectedItem()
				.toString();
		String tempDepartingCityString = dsctJComboBox.getSelectedItem()
				.toString();
		String tempDestinationCityString = dpctJComboBox.getSelectedItem()
				.toString();
		String tempDepartingDateString = bcyrJComboBox.getSelectedItem()
				.toString()
				+ "-"
				+ bcmtJComboBox.getSelectedItem().toString()
				+ "-" + bcdyJComboBox.getSelectedItem().toString();
		String classString = class2JComboBox.getSelectedItem().toString();

		if (tempFlightNoString.length() == 6) {
			switch (tempAirlineString) {
			case "Air China":
				return "0*" + tempFlightNoString + "*1*$*$*"
						+ tempDepartingDateString + "*" + classString + "*#";
			case "Qantas":
				return "0*" + tempFlightNoString + "*2*$*$*"
						+ tempDepartingDateString + "*" + classString + "*#";
			case "China Eastern":
				return "0*" + tempFlightNoString + "*3*$*$*"
						+ tempDepartingDateString + "*" + classString + "*#";
			default:
				return "0*" + tempFlightNoString + "*0*$*$*"
						+ tempDepartingDateString + "*" + classString + "*#";
			}
		} else if (!tempFlightNoString.equals("")
				&& tempFlightNoString.length() != 6) {
			JOptionPane.showMessageDialog(null,
					"Please input the right flight number!", "Error",
					JOptionPane.ERROR_MESSAGE);
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
			case "Air China":
				return "0*$*1*" + tempDepartingCityString + "*"
						+ tempDestinationCityString + "*"
						+ tempDepartingDateString + "*" + classString + "*#";
			case "Qantas":
				return "0*$*2*" + tempDepartingCityString + "*"
						+ tempDestinationCityString + "*"
						+ tempDepartingDateString + "*" + classString + "*#";
			case "China Eastern":
				return "0*$*3*" + tempDepartingCityString + "*"
						+ tempDestinationCityString + "*"
						+ tempDepartingDateString + "*" + classString + "*#";
			default:
				return "0*$*0*" + tempDepartingCityString + "*"
						+ tempDestinationCityString + "*"
						+ tempDepartingDateString + "*" + classString + "*#";
			}
		} else {
			return null;
		}
	}

	public void fillTable1(String[][] strings) {

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

	public void fillTable2(String[][] strings) {

		DefaultTableModel defaultTableModel = (DefaultTableModel) data2JTable
				.getModel();
		defaultTableModel.setRowCount(0);

		for (int i = 0; i < strings.length; i++) {
			if (strings[i][0].equals("$")) {
				continue;
			}
			tableModel2.addRow(strings[i]);
		}

		data2JTable.updateUI();
	}
}
