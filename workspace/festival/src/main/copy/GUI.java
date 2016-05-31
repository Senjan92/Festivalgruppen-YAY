package main.copy;

import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.Dimension;

import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.copy.Constants;
import main.copy.Constants;

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
		tabs.addTab("Boka Band", initBookBand());
		tabs.addTab("Delge Kontaktpersoner", initAssignContacts());
		return tabs;
	}

	private JPanel initBookBand() {
		JPanel panelBands = new JPanel();
		panelBands.setLayout(new BoxLayout(panelBands, BoxLayout.Y_AXIS));
		
		btnBookBand = new JButton("Visa Spelningar");
		btnBookBand.addActionListener(buttonListener);
		btnBookBand.setMinimumSize(new Dimension(370, 25));
		btnBookBand.setMaximumSize(new Dimension(370, 25));
		panelBands.add(btnBookBand);
		
		return panelBands;
	}

	private JPanel initAssignContacts() {
		JPanel panelContacts = new JPanel();
		btnSetContact = new JButton("Visa Info");
		comboBand = new JComboBox(controller.getBands().toArray());
		panelContacts.setLayout(new BoxLayout(panelContacts, BoxLayout.Y_AXIS));
		btnSetContact.addActionListener(buttonListener);
		btnSetContact.setMinimumSize(new Dimension(370, 20));
		btnSetContact.setMaximumSize(new Dimension(370, 20));
		comboBand.setMinimumSize(new Dimension(370, 20));
		comboBand.setMaximumSize(new Dimension(370, 20));
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

		private void resetBandFields() {
		txtAreaBandName.setText("");
		txtAreaBandStartYear.setText("");
		txtAreaBandOrigin.setText("");
		txtAreaBandGenre.setText("");
	}

	private class Listener implements ActionListener {
		@Override public void actionPerformed(ActionEvent event) {
			if (event.getSource() == btnBookBand) {
				controller.getBandPlayInfo();
			} else if (event.getSource() == btnSetContact) {
				setContact();
			}
		}
	}

	@Override public void windowActivated(WindowEvent e) {
	}

	public void setContact() {
		// TODO Auto-generated method stub
		
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