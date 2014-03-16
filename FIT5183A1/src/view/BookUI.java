/**
 * BookUI.java
 * FIT5183A1
 * Phillip
 * 2014年3月14日 下午9:12:08
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.Client;

/**
 * @author Phillip
 * 
 */
public class BookUI extends JFrame {

	private Client client;

	private JLabel nameJLabel;
	private JLabel phoneJLabel;
	private JLabel emailJLabel;
	private JLabel creditJLabel;
	private JLabel text1JLabel;
	private JLabel text2JLabel;
	private JLabel text3JLabel;
	private JLabel myJLabel;

	private JTextField nameJTextField;
	private JTextField phoneJTextField;
	private JTextField emailJTextField;
	private JTextField creditJTextField;

	private JButton confirmJButton;

	private JTable ticketJTable;
	private JScrollPane jScrollPane;

	private String[] columnNameStrings = { "Depart", "Fligth Details",
			"Arrive", "Passenger Info" };
	private Object[][] cellData = null;
	private DefaultTableModel tableModel = new DefaultTableModel(cellData,
			columnNameStrings);

	private String[] flightInfoStrings;
	private String[] flightInfoStrings2;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BookUI("1", "2", new Client());
	}

	public BookUI(String bookinfoString, String bookinfoString2, Client client) {

		flightInfoStrings = bookinfoString.split("\\*");
		flightInfoStrings2 = bookinfoString2.split("\\*");

		this.client = client;

		this.setTitle("MY FLIGHT BOOKING CENTRE");
		this.setLayout(new GridBagLayout());
		this.setSize(1200, 600);

		myJLabel = new JLabel("MY FLIGHT BOOKING CENTRE");
		nameJLabel = new JLabel("Name");
		phoneJLabel = new JLabel("Mobile");
		emailJLabel = new JLabel("E-mail");
		creditJLabel = new JLabel("Credit");
		text1JLabel = new JLabel("Please input your personal information:");
		text2JLabel = new JLabel("Are you sure to book a flight from ");
		text3JLabel = new JLabel();

		text3JLabel.setText(flightInfoStrings[2] + flightInfoStrings[3] + " "
				+ " a " + flightInfoStrings[4] + " ticket, the price is "
				+ flightInfoStrings[5] + " ?");

		nameJTextField = new JTextField(10);
		nameJTextField.setText("HuYifei");
		phoneJTextField = new JTextField(10);
		phoneJTextField.setText("18021245594");
		emailJTextField = new JTextField(10);
		emailJTextField.setText("jsxhhyf@gmail.com");
		creditJTextField = new JTextField(10);
		creditJTextField.setText("0123456789012345");

		ticketJTable = new JTable(tableModel);
		jScrollPane = new JScrollPane(ticketJTable);

		confirmJButton = new JButton("Confirm!");

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		c.insets = new Insets(20, 10, 20, 10);
		this.add(myJLabel, c);

		c.gridx = 4;
		c.gridwidth = 8;
		c.gridheight = 5;
		c.weightx = 2;
		c.ipadx = 500;
		this.add(jScrollPane, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.gridwidth = 4;
		c.gridheight = 1;
		c.ipadx = 0;
		c.insets = new Insets(100, 10, 20, 10);
		this.add(text1JLabel, c);

		c.gridy = 2;
		c.gridwidth = 1;
		c.insets = new Insets(20, 10, 20, 10);
		this.add(nameJLabel, c);

		c.gridx = 1;
		this.add(nameJTextField, c);

		c.gridx = 2;
		this.add(phoneJLabel, c);

		c.gridx = 3;
		this.add(phoneJTextField, c);

		c.gridx = 0;
		c.gridy = 3;
		this.add(emailJLabel, c);

		c.gridx = 1;
		this.add(emailJTextField, c);

		c.gridx = 2;
		this.add(creditJLabel, c);

		c.gridx = 3;
		this.add(creditJTextField, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 8;
		c.insets = new Insets(50, 10, 0, 10);
		this.add(text2JLabel, c);

		c.gridy = 5;
		c.gridwidth = 4;
		c.insets = new Insets(0, 10, 20, 10);
		this.add(text3JLabel, c);

		c.gridy = 6;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.insets = new Insets(20, 10, 20, 10);
		this.add(confirmJButton, c);

		this.setResizable(false);
		this.setVisible(true);

		confirmJButton.addActionListener(new confirmButtonHandler());
	}

	private class confirmButtonHandler implements ActionListener {
		private String resultString;
		private String resultString2;

		public void actionPerformed(ActionEvent event) {
			if (!validateInfo()) {
				return;
			}

			switch (flightInfoStrings[1]) {
			case "Airline1":
				client.query("1*" + flightInfoStrings[0] + "*" + "1*"
						+ getDate1(flightInfoStrings[3]) + "*"
						+ flightInfoStrings[4]);
				break;
			case "Airline2":
				client.query("1*" + flightInfoStrings[0] + "*" + "2*"
						+ getDate1(flightInfoStrings[3]) + "*"
						+ flightInfoStrings[4]);
				break;

			case "Airline3":
				client.query("1*" + flightInfoStrings[0] + "*" + "3*"
						+ getDate1(flightInfoStrings[3]) + "*"
						+ flightInfoStrings[4]);
				break;
			default:
				break;
			}

			resultString = client.getResultString();

			switch (flightInfoStrings2[1]) {
			case "Airline1":
				client.query("1*" + flightInfoStrings2[0] + "*" + "1*"
						+ getDate1(flightInfoStrings2[3]) + "*"
						+ flightInfoStrings2[4]);
				break;
			case "Airline2":
				client.query("1*" + flightInfoStrings2[0] + "*" + "2*"
						+ getDate1(flightInfoStrings2[3]) + "*"
						+ flightInfoStrings2[4]);
				break;

			case "Airline3":
				client.query("1*" + flightInfoStrings2[0] + "*" + "3*"
						+ getDate1(flightInfoStrings2[3]) + "*"
						+ flightInfoStrings2[4]);
				break;
			default:
				break;
			}
			resultString2 = client.getResultString();

			if (resultString.equals("false") || resultString2.equals("false")) {
				JOptionPane.showMessageDialog(null, "Oops... Booking failed!",
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String[][] strings = new String[11][4];
				strings[0][0] = getCity1(flightInfoStrings[2]);
				strings[0][1] = flightInfoStrings[1];
				strings[0][2] = getCity2(flightInfoStrings[2]);
				strings[0][3] = nameJTextField.getText();
				strings[1][0] = "Terminal 2";
				strings[1][1] = flightInfoStrings[0];
				strings[1][2] = "Terminal 1";
				strings[1][3] = phoneJTextField.getText();
				strings[2][0] = getAirport(flightInfoStrings[2]);
				strings[2][1] = flightInfoStrings[4];
				strings[2][2] = getAirport2(flightInfoStrings[2]);
				strings[2][3] = "Price is :　" + flightInfoStrings[5];
				strings[3][0] = getDate1(flightInfoStrings[3]);
				strings[3][2] = getDate2(flightInfoStrings[3]);
				strings[4][0] = getTime(flightInfoStrings[3]);
				strings[4][2] = getTime2(flightInfoStrings[3]);

				strings[6][0] = getCity1(flightInfoStrings2[2]);
				strings[6][1] = flightInfoStrings2[1];
				strings[6][2] = getCity2(flightInfoStrings2[2]);
				strings[6][3] = nameJTextField.getText();
				strings[7][0] = "Terminal 2";
				strings[7][1] = flightInfoStrings2[0];
				strings[7][2] = "Terminal 1";
				strings[7][3] = phoneJTextField.getText();
				strings[8][0] = getAirport(flightInfoStrings2[2]);
				strings[8][1] = flightInfoStrings2[4];
				strings[8][2] = getAirport2(flightInfoStrings2[2]);
				strings[8][3] = "Price is :　" + flightInfoStrings2[5];
				strings[9][0] = getDate1(flightInfoStrings2[3]);
				strings[9][2] = getDate2(flightInfoStrings2[3]);
				strings[10][0] = getTime(flightInfoStrings2[3]);
				strings[10][2] = getTime2(flightInfoStrings2[3]);

				fillTable(strings);
			}
		}
	}

	public void fillTable(String[][] strings) {

		DefaultTableModel defaultTableModel = (DefaultTableModel) ticketJTable
				.getModel();
		defaultTableModel.setRowCount(0);

		for (int i = 0; i < strings.length; i++) {

			tableModel.addRow(strings[i]);
		}

		ticketJTable.updateUI();
	}

	public boolean validateInfo() {
		Pattern emailPattern = Pattern
				.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
		Pattern phonePattern = Pattern.compile("[0-9]{11}");
		Pattern creditPattern = Pattern.compile("[0-9]{16}");

		Matcher emailMatcher = emailPattern.matcher(emailJTextField.getText());
		Matcher phoneMatcher = phonePattern.matcher(phoneJTextField.getText());
		Matcher creditMatcher = creditPattern.matcher(creditJTextField
				.getText());

		if (!emailMatcher.matches()) {
			JOptionPane.showMessageDialog(null,
					"Please input the right format of Email!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (!phoneMatcher.matches()) {
			JOptionPane
					.showMessageDialog(
							null,
							"Please input the right format of phone number (11 digits)!",
							"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (!creditMatcher.matches()) {
			JOptionPane
					.showMessageDialog(
							null,
							"Please input the right format of credit number (16 digits)!",
							"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			return true;
		}

	}

	public String getDate1(String string) {
		Pattern datePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		Matcher m = datePattern.matcher(string);
		if (m.find()) {
			return m.group(0);
		} else {
			return "$";
		}
	}

	public String getDate2(String string) {
		Pattern datePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		Matcher m = datePattern.matcher(string);
		if (m.find()) {
			m.find();
			return m.group(0);
		} else {
			return "$";
		}
	}

	public String getCity1(String string) {
		Pattern datePattern = Pattern.compile("[AC]_CITY[0-9]");
		Matcher m = datePattern.matcher(string);
		if (m.find()) {
			return m.group(0);
		} else {
			return "$";
		}
	}

	public String getCity2(String string) {
		Pattern datePattern = Pattern.compile("[AC]_CITY[0-9]");
		Matcher m = datePattern.matcher(string);
		if (m.find()) {
			m.find();
			return m.group(0);
		} else {
			return "$";
		}
	}

	public String getAirport(String string) {
		Pattern datePattern = Pattern.compile("\\w{2}_Airport");
		Matcher m = datePattern.matcher(string);
		if (m.find()) {
			return m.group(0);
		} else {
			return "$";
		}
	}

	public String getAirport2(String string) {
		Pattern datePattern = Pattern.compile("\\w{2}_Airport");
		Matcher m = datePattern.matcher(string);
		if (m.find()) {
			m.find();
			return m.group(0);
		} else {
			return "$";
		}
	}
	
	public String getTime(String string) {
		Pattern datePattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
		Matcher m = datePattern.matcher(string);
		if (m.find()) {
			return m.group(0);
		} else {
			return "$";
		}
	}
	
	public String getTime2(String string) {
		Pattern datePattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
		Matcher m = datePattern.matcher(string);
		if (m.find()) {
			m.find();
			return m.group(0);
		} else {
			return "$";
		}
	}
}
