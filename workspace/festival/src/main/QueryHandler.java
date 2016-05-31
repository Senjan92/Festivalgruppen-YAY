package main;

import java.util.*;
import java.sql.*;

public class QueryHandler {
	private PreparedStatement statement;
	private Connection connection;
	private ResultSet sqlResult;

	public QueryHandler(Connection connection) {
		this.connection = connection;
	}

	public void addBand(String name, int year, String origin, String genre) throws SQLException {
		statement = connection.prepareStatement("INSERT INTO Band VALUES ('" + name + "'," + year + ",'" + origin + "','" + genre + "');");
		statement.executeUpdate();
	}

	public void setContact(String band, String person) throws SQLException {
		statement = connection.prepareStatement("INSERT INTO Kontaktperson VALUES ('" + person + "','" + band + "');");
		statement.executeUpdate();
		statement = connection.prepareStatement("UPDATE Personal SET Ansvarsomrade='Kontaktperson' WHERE Personnummer='" + person + "';");
		statement.executeUpdate();
	}

	public ArrayList<String> getBands() throws SQLException {
		statement = connection.prepareStatement("SELECT Namn FROM Band;");
		sqlResult = statement.executeQuery();
		ArrayList<String> resultList = new ArrayList<>();
		while (sqlResult.next())
			resultList.add(sqlResult.getString(1));

		statement = connection.prepareStatement("SELECT bandNamn FROM Kontaktperson;");
		sqlResult = statement.executeQuery();

		return resultList;
	}

	public boolean bandHasContact(String band) throws SQLException {
		statement = connection.prepareStatement("SELECT bandNamn FROM Kontaktperson;");
		sqlResult = statement.executeQuery();
		ArrayList<String> resultList = new ArrayList<>();
		while (sqlResult.next())
			resultList.add(sqlResult.getString(1));
		if (resultList.contains(band))
			return true;
		else
			return false;
	}

	public ArrayList<String> getPersons() throws SQLException {
		statement = connection.prepareStatement("SELECT Personnummer,Namn FROM Personal WHERE Ansvarsomrade IS NULL;");
		sqlResult = statement.executeQuery();
		ArrayList<String> resultList = new ArrayList<>();
		while (sqlResult.next()) {
			String result = sqlResult.getString(1) + " - " + sqlResult.getString(2);
			resultList.add(result);
		}
		return resultList;
	}
}