package main;

import java.awt.event.WindowListener;
import java.util.ResourceBundle.Control;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
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
	private JButton btnSetContact;
	private JButton btnBookPlay;
	private JComboBox comboPerson;
	private JComboBox comboBand;
	private JComboBox comboBandPlay;
	private JComboBox comboScenePlay;
	private JComboBox comboDatePlay;
	private JComboBox comboTimePlay;
	private Listener buttonListener;
	private String[] timeArray;
	private String[] dateArray;

	public GUI(Controller controller) {
		this.controller = controller;
		init();
	}

	private void init() {
		timeArray = new String[] { "00.00-01.00", "01.00-02.00", "02.00-03.00", "03.00-04.00", "04.00-05.00", "05.00-06.00", "06.00-07.00", "07.00-08.00", "08.00-09.00", "09.00-10.00", "10.00-11.00",
				"11.00-12.00", "12.00-13.00", "13.00-14.00", "14.00-15.00", "15.00-16.00", "16.00-17.00", "17.00-18.00", "18.00-19.00", "19.00-20.00", "20.00-21.00", "21.00-22.00", "22.00-23.00",
				"23.00-00.00" };
		dateArray = new String[] { "torsdag", "fredag", "lördag" };
		buttonListener = new Listener();
		frame = new JFrame("Blomstermåla Rockfestival");
		frame.setPreferredSize(new Dimension(375, 250));
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
		tabs.addTab("Boka Spelningar", initBookPlay());
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
		panelContacts.setLayout(new BoxLayout(panelContacts, BoxLayout.Y_AXIS));
		btnSetContact = new JButton("Delge kontaktperson");
		comboPerson = new JComboBox(controller.getPersons().toArray());
		comboBand = new JComboBox(controller.getBandsWithoutContacts().toArray());
		btnSetContact.addActionListener(buttonListener);
		panelContacts.add(new JLabel("Person"));
		panelContacts.add(comboPerson);
		panelContacts.add(new JLabel("Band"));
		panelContacts.add(comboBand);
		panelContacts.add(btnSetContact);
		btnSetContact.setAlignmentX(Component.LEFT_ALIGNMENT);
		comboPerson.setAlignmentX(Component.LEFT_ALIGNMENT);
		comboBand.setAlignmentX(Component.LEFT_ALIGNMENT);
		return panelContacts;
	}

	private JPanel initBookPlay() {
		JPanel panelPlays = new JPanel();
		panelPlays.setLayout(new BoxLayout(panelPlays, BoxLayout.Y_AXIS));
		comboBandPlay = new JComboBox(controller.getBands().toArray());
		comboScenePlay = new JComboBox(controller.getScenes().toArray());
		comboDatePlay = new JComboBox(dateArray);
		comboTimePlay = new JComboBox(timeArray);
		btnBookPlay = new JButton("Boka spelning");
		btnBookPlay.addActionListener(buttonListener);
		panelPlays.add(new JLabel("Band"));
		panelPlays.add(comboBandPlay);
		panelPlays.add(new JLabel("Scen"));
		panelPlays.add(comboScenePlay);
		panelPlays.add(new JLabel("Dag"));
		panelPlays.add(comboDatePlay);
		panelPlays.add(new JLabel("Tid"));
		panelPlays.add(comboTimePlay);
		panelPlays.add(btnBookPlay);
		comboBandPlay.setAlignmentX(Component.LEFT_ALIGNMENT);
		comboScenePlay.setAlignmentX(Component.LEFT_ALIGNMENT);
		comboDatePlay.setAlignmentX(Component.LEFT_ALIGNMENT);
		comboTimePlay.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnBookPlay.setAlignmentX(Component.LEFT_ALIGNMENT);
		return panelPlays;
	}

	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(frame, message);
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

	private void bookPlay() {
		if (controller.timeSlotTaken(comboDatePlay.getSelectedItem().toString(), comboTimePlay.getSelectedItem().toString()))
			showErrorMessage(Constants.TIME_SLOT_TAKEN);
		else if (controller.bookPlay(comboScenePlay.getSelectedItem().toString(), comboBandPlay.getSelectedItem().toString(), comboDatePlay.getSelectedItem().toString(),
				comboTimePlay.getSelectedItem().toString()))
			JOptionPane.showMessageDialog(frame, Constants.PLAY_BOOKED);
	}

	private void setContact() {
		String[] personSplitter = comboPerson.getSelectedItem().toString().split(" - ");
		String person = personSplitter[0];
		String band = comboBand.getSelectedItem().toString();
		if (controller.setContact(band, person)) {
			JOptionPane.showMessageDialog(frame, Constants.CONTACT_SET);
			comboPerson.setModel(new DefaultComboBoxModel(controller.getPersons().toArray()));
			comboBand.setModel(new DefaultComboBoxModel(controller.getBandsWithoutContacts().toArray()));
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
			if (event.getSource() == btnBookBand)
				bookBand();
			else if (event.getSource() == btnSetContact)
				setContact();
			else if (event.getSource() == btnBookPlay)
				bookPlay();
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
