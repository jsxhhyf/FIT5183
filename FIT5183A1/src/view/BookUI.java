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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BookUI("1", new Client());
	}

	public BookUI(String bookinfoString, Client client) {

		flightInfoStrings = bookinfoString.split("\\*");

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

		text3JLabel.setText("from " + flightInfoStrings[2] + " to "
				+ flightInfoStrings[4] + " on " + flightInfoStrings[6] + " "
				+ flightInfoStrings[8] + " a " + flightInfoStrings[10]
				+ " ticket, the price is " + flightInfoStrings[11] + " ?");

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
		this.add(jScrollPane, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.gridwidth = 4;
		c.gridheight = 1;
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
		c.gridwidth = 6;
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

		public void actionPerformed(ActionEvent event) {
			if (!validateInfo()) {
				return;
			}
			
			switch (flightInfoStrings[1]) {
			case "airline1":
				client.query("1*" + flightInfoStrings[0] + "*"
						+  "1*" + flightInfoStrings[6] + "*"
						+ flightInfoStrings[10]);
				break;
			case "airline2":
				client.query("1*" + flightInfoStrings[0] + "*"
						+  "2*" + flightInfoStrings[6] + "*"
						+ flightInfoStrings[10]);
				break;
			
			case "airline3":
				client.query("1*" + flightInfoStrings[0] + "*"
						+  "3*" + flightInfoStrings[6] + "*"
						+ flightInfoStrings[10]);
				break;
			default:
				break;
			}
			
			resultString = client.getResultString();
			if (resultString.equals("false")) {
				JOptionPane.showMessageDialog(null,
						"Oops... Booking failed!", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				String[][] strings = new String[5][4];
				strings[0][0] = flightInfoStrings[2];
				strings[0][1] = flightInfoStrings[1];
				strings[0][2] = flightInfoStrings[4];
				strings[0][3] = nameJTextField.getText();
				strings[1][0] = "Terminal 2";
				strings[1][1] = flightInfoStrings[0];
				strings[1][2] = "Terminal 1";
				strings[1][3] = phoneJTextField.getText();
				strings[2][0] = flightInfoStrings[3];
				strings[2][1] = flightInfoStrings[10];
				strings[2][2] = flightInfoStrings[5];
				strings[2][3] = flightInfoStrings[11];
				strings[3][0] = flightInfoStrings[6];
				strings[3][2] = flightInfoStrings[7];
				strings[4][0] = flightInfoStrings[8];
				strings[4][2] = flightInfoStrings[9];
				
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
		Pattern emailPattern = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
		Pattern phonePattern = Pattern.compile("[0-9]{11}");
		Pattern creditPattern = Pattern.compile("[0-9]{16}");
		
		Matcher emailMatcher = emailPattern.matcher(emailJTextField.getText());
		Matcher phoneMatcher = phonePattern.matcher(phoneJTextField.getText());
		Matcher creditMatcher = creditPattern.matcher(creditJTextField.getText());
		
		if (!emailMatcher.matches()) {
			JOptionPane.showMessageDialog(null,
					"Please input the right format of Email!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (!phoneMatcher.matches()) {
			JOptionPane.showMessageDialog(null,
					"Please input the right format of phone number (11 digits)!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (!creditMatcher.matches()) {
			JOptionPane.showMessageDialog(null,
					"Please input the right format of credit number (16 digits)!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			return true;
		}
		
	}
}
