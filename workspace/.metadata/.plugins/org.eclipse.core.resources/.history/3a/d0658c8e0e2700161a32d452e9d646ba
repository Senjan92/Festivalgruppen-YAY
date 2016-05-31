package app;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Controller {
	private QueryHandler queryHandler;
	private Connection connection;
	private boolean isConnected;

	public Controller() {
		isConnected = connectToDatabase();
		if (isConnected);
	}

	public void exit() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getBands() {
		try {
			return queryHandler.getBands();
		} catch (SQLException e) {
			return null;
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