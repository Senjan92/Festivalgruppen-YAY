package main;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Controller {
	private QueryHandler queryHandler;
	private Connection connection;
	private boolean isConnected;
	private GUI gui;

	public Controller() {
		isConnected = connectToDatabase();
		if (isConnected)
			gui = new GUI(this);
		else
			JOptionPane.showMessageDialog(null, Constants.COULD_NOT_CONNECT);
	}

	public void exit() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getBandsWithoutContacts() {
		try {
			return queryHandler.getBandsWithoutContacts();
		} catch (SQLException e) {
			gui.showErrorMessage(Constants.DATABASE_ERROR);
			return null;
		}
	}

	public ArrayList<String> getPersons() {
		try {
			return queryHandler.getPersons();
		} catch (SQLException e) {
			gui.showErrorMessage(Constants.DATABASE_ERROR);
			return null;
		}
	}

	public boolean bandIsBooked(String band, String time) {
		try {
			return queryHandler.bandIsBooked(band, time);
		} catch (SQLException e) {
			gui.showErrorMessage(Constants.DATABASE_ERROR);
			return false;
		}
	}

	public boolean timeSlotTaken(String scene, String date, String time) {
		try {
			return queryHandler.timeSlotTaken(scene, date, time);
		} catch (SQLException e) {
			gui.showErrorMessage(Constants.DATABASE_ERROR);
		}
		return false;
	}

	public ArrayList<String> getScenes() {
		try {
			return queryHandler.getScenes();
		} catch (SQLException e) {
			gui.showErrorMessage(Constants.DATABASE_ERROR);
			return null;
		}
	}

	public ArrayList<String> getBands() {
		try {
			return queryHandler.getBands();
		} catch (SQLException e) {
			gui.showErrorMessage(Constants.DATABASE_ERROR);
		}
		return null;
	}

	public boolean bookPlay(String scen, String band, String datum, String tid) {
		try {
			queryHandler.bookPlay(scen, band, datum, tid);
			return true;
		} catch (SQLException e) {
			gui.showErrorMessage(Constants.DATABASE_ERROR);
		}
		return false;
	}

	public boolean setContact(String band, String person) {
		try {
			queryHandler.setContact(band, person);
			return true;
		} catch (SQLException e) {
			gui.showErrorMessage(Constants.DATABASE_ERROR);
		} catch (Exception e) {
			gui.showErrorMessage(Constants.TOO_MANY_FOR_CONTACT);
		}
		return false;
	}

	public boolean bookBand(String name, int year, String origin, String genre) {
		try {
			queryHandler.addBand(name, year, origin, genre);
			return true;
		} catch (SQLException e) {
			gui.showErrorMessage(Constants.DATABASE_ERROR);
			return false;
		}
	}

	private boolean connectToDatabase() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection("jdbc:mysql://195.178.232.16/af7253?user=af7253&password=Hekfixy1430");
			queryHandler = new QueryHandler(connection);
			return true;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		new Controller();
	}
}