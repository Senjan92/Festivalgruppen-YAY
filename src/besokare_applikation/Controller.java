package besokare_applikation;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.sql.*;

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

	public ArrayList<String> getBandInfo() {
		try {
			return queryHandler.getBands();
		} catch (SQLException e) {
			gui.showErrorMessage(Constants.DATABASE_ERROR);
			return null;
		}
	}

	public String getBandPlayInfo() {
		try {
			StringBuilder stringBuilder = new StringBuilder();
			for (String bandInfo : queryHandler.getBandPlayInfo())
				stringBuilder.append(bandInfo + "\n");
			return stringBuilder.toString();
		} catch (SQLException e) {
			gui.showErrorMessage(Constants.DATABASE_ERROR);
			return "";
		}
	}

	public ArrayList<String> getBandInfo(String band) {
		try {
			return queryHandler.getBandInfo(band);
		} catch (SQLException e) {
			gui.showErrorMessage(Constants.DATABASE_ERROR);
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