package besokare_applikation;

import java.awt.event.WindowListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI implements WindowListener {
	private JFrame frame;
	private Controller controller;
	private JButton btnUpdatePlayTimes;
	private JComboBox comboBand;
	private JButton btnShowBandInfo;
	private Listener buttonListener;
	private JTextArea playTimes;

	public GUI(Controller controller) {
		this.controller = controller;
		init();
	}

	private void init() {
		buttonListener = new Listener();
		frame = new JFrame("Blomstermåla Rockfestival");
		frame.setPreferredSize(new Dimension(550, 300));
		frame.add(initContent());
		frame.setResizable(false);
		frame.addWindowListener(this);
		frame.pack();
		frame.setVisible(true);
	}

	private JTabbedPane initContent() {
		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Visa spelningar", initShowPlayTimes());
		tabs.addTab("Visa bandinformation", initShowBandInfo());
		return tabs;
	}

	private JPanel initShowPlayTimes() {
		JPanel panelBands = new JPanel();
		panelBands.setLayout(new BoxLayout(panelBands, BoxLayout.Y_AXIS));
		btnUpdatePlayTimes = new JButton("Uppdatera Spelningar");
		btnUpdatePlayTimes.addActionListener(buttonListener);
		btnUpdatePlayTimes.setMinimumSize(new Dimension(370, 25));
		btnUpdatePlayTimes.setMaximumSize(new Dimension(370, 25));
		playTimes = new JTextArea(controller.getBandPlayInfo());
		playTimes.setLineWrap(true);
		panelBands.add(playTimes);
		panelBands.add(btnUpdatePlayTimes);
		return panelBands;
	}

	private JPanel initShowBandInfo() {
		JPanel panelContacts = new JPanel();
		btnShowBandInfo = new JButton("Visa Info");
		panelContacts.setLayout(new BoxLayout(panelContacts, BoxLayout.Y_AXIS));
		btnShowBandInfo.addActionListener(buttonListener);
		btnShowBandInfo.setMinimumSize(new Dimension(370, 20));
		btnShowBandInfo.setMaximumSize(new Dimension(370, 20));
		comboBand = new JComboBox(controller.getBandInfo().toArray());
		comboBand.setMinimumSize(new Dimension(370, 20));
		comboBand.setMaximumSize(new Dimension(370, 20));
		panelContacts.add(new JLabel("Band"));
		panelContacts.add(comboBand);
		panelContacts.add(btnShowBandInfo);
		return panelContacts;
	}

	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(frame, message);
	}

	public void showOutputMessage(String message) {
		JOptionPane.showMessageDialog(frame, message);
	}

	private class Listener implements ActionListener {
		@Override public void actionPerformed(ActionEvent event) {
			if (event.getSource() == btnUpdatePlayTimes)
				playTimes.setText(controller.getBandPlayInfo());
			else if (event.getSource() == btnShowBandInfo)
				showOutputMessage(controller.getBandInfo(comboBand.getSelectedItem().toString()).toString());
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