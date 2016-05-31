package main;

import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.Dimension;

import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI implements WindowListener {
	private JFrame frame;
	private Controller controller;
	private JTextField txtAreaBandName;
	private JTextField txtAreaBandStartYear;
	private JTextField txtAreaBandGenre;
	private JTextField txtAreaBandOrigin;
	private JButton btnBookBand;
	private JComboBox comboPerson;
	private JComboBox comboBand;
	private JButton btnSetContact;
	private Listener buttonListener;

	public GUI(Controller controller) {
		this.controller = controller;
		init();
	}

	private void init() {
		buttonListener = new Listener();
		frame = new JFrame("Blomstermåla Rockfestival");
		frame.setPreferredSize(new Dimension(375, 500));
		frame.add(initContent());
		frame.setResizable(false);
		frame.addWindowListener(this);
		frame.pack();
		frame.setVisible(true);
	}

	private JTabbedPane initContent() {
		JTabbedPane tabs = new JTabbedPane();
		JPanel bookScene = new JPanel();
		tabs.addTab("Boka Band", initBookBand());
		tabs.addTab("Delge Kontaktpersoner", initAssignContacts());
		tabs.addTab("Boka Spelningar", bookScene);
		return tabs;
	}

	private JPanel initBookBand() {
		JPanel panelBands = new JPanel();
		panelBands.setLayout(new BoxLayout(panelBands, BoxLayout.Y_AXIS));
		txtAreaBandName = new JTextField();
		txtAreaBandGenre = new JTextField();
		txtAreaBandOrigin = new JTextField();
		txtAreaBandStartYear = new JTextField();
		btnBookBand = new JButton("Boka Band");
		btnBookBand.addActionListener(buttonListener);
		btnBookBand.setMinimumSize(new Dimension(370, 25));
		btnBookBand.setMaximumSize(new Dimension(370, 25));
		txtAreaBandName.setMinimumSize(new Dimension(370, 20));
		txtAreaBandName.setMaximumSize(new Dimension(370, 20));
		txtAreaBandStartYear.setMinimumSize(new Dimension(370, 20));
		txtAreaBandStartYear.setMaximumSize(new Dimension(370, 20));
		txtAreaBandOrigin.setMinimumSize(new Dimension(370, 20));
		txtAreaBandOrigin.setMaximumSize(new Dimension(370, 20));
		txtAreaBandGenre.setMinimumSize(new Dimension(370, 20));
		txtAreaBandGenre.setMaximumSize(new Dimension(370, 20));
		panelBands.add(new JLabel("Bandnamn"));
		panelBands.add(txtAreaBandName);
		panelBands.add(new JLabel("Startår"));
		panelBands.add(txtAreaBandStartYear);
		panelBands.add(new JLabel("Ursprung"));
		panelBands.add(txtAreaBandOrigin);
		panelBands.add(new JLabel("Genre"));
		panelBands.add(txtAreaBandGenre);
		panelBands.add(btnBookBand);
		return panelBands;
	}

	private JPanel initAssignContacts() {
		JPanel panelContacts = new JPanel();
		btnSetContact = new JButton("Delge kontaktperson");
		comboPerson = new JComboBox(controller.getPersons().toArray());
		comboBand = new JComboBox(controller.getBands().toArray());
		panelContacts.setLayout(new BoxLayout(panelContacts, BoxLayout.Y_AXIS));
		btnSetContact.addActionListener(buttonListener);
		btnSetContact.setMinimumSize(new Dimension(370, 20));
		btnSetContact.setMaximumSize(new Dimension(370, 20));
		comboPerson.setMinimumSize(new Dimension(370, 20));
		comboPerson.setMaximumSize(new Dimension(370, 20));
		comboBand.setMinimumSize(new Dimension(370, 20));
		comboBand.setMaximumSize(new Dimension(370, 20));
		panelContacts.add(new JLabel("Person"));
		panelContacts.add(comboPerson);
		panelContacts.add(new JLabel("Band"));
		panelContacts.add(comboBand);
		panelContacts.add(btnSetContact);
		return panelContacts;
	}

	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(frame, message);
	}

	public void showErrorMessage(String message, String exception) {
		JOptionPane.showMessageDialog(frame, message + "\n\n" + exception);
	}

	private void bookBand() {
		String name = txtAreaBandName.getText();
		String startYear = txtAreaBandStartYear.getText();
		String origin = txtAreaBandOrigin.getText();
		String genre = txtAreaBandGenre.getText();

		if (name.isEmpty() || startYear.isEmpty() || origin.isEmpty() || genre.isEmpty()) {
			showErrorMessage(Constants.EMPTY_FIELDS);
			return;
		}
		try {
			int year = Integer.parseInt(startYear);
			if (controller.bookBand(name, year, origin, genre)) {
				JOptionPane.showMessageDialog(frame, Constants.BAND_BOOKED);
				resetBandFields();
			}
		} catch (NumberFormatException e) {
			showErrorMessage(Constants.NOT_A_VALID_YEAR);
		}
	}

	private void setContact() {
		String[] personSplitter = comboPerson.getSelectedItem().toString().split(" - ");
		String person = personSplitter[0];
		String band = comboBand.getSelectedItem().toString();
		if (controller.bandHasContact(band))
			showErrorMessage(Constants.BAND_HAS_CONTACT);
		else if (controller.setContact(band, person)) {
			JOptionPane.showMessageDialog(frame, Constants.CONTACT_SET);
			comboPerson.setModel(new DefaultComboBoxModel(controller.getPersons().toArray()));
			comboBand.setModel(new DefaultComboBoxModel(controller.getBands().toArray()));
		}
	}

	private void resetBandFields() {
		txtAreaBandName.setText("");
		txtAreaBandStartYear.setText("");
		txtAreaBandOrigin.setText("");
		txtAreaBandGenre.setText("");
	}

	private class Listener implements ActionListener {
		@Override public void actionPerformed(ActionEvent event) {
			if (event.getSource() == btnBookBand) {
				bookBand();
			} else if (event.getSource() == btnSetContact) {
				setContact();
			}
		}
	}

	@Override public void windowActivated(WindowEvent e) {
	}

	@Override public void windowClosed(WindowEvent e) {
	}

	@Override public void windowClosing(WindowEvent e) {
		controller.exit();
		System.exit(1);
	}

	@Override public void windowDeactivated(WindowEvent e) {
	}

	@Override public void windowDeiconified(WindowEvent e) {
	}

	@Override public void windowIconified(WindowEvent e) {
	}

	@Override public void windowOpened(WindowEvent e) {
	}
}