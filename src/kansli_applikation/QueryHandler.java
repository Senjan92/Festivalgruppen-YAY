package kansli_applikation;

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

	public void setContact(String band, String person) throws SQLException, Exception {
		if (tooManyToHandle(person, band))
			throw new Exception();
		statement = connection.prepareStatement("BEGIN;");
		statement.executeUpdate();
		statement = connection.prepareStatement("INSERT INTO Kontaktperson VALUES ('" + person + "','" + band + "');");
		statement.executeUpdate();
		statement = connection.prepareStatement("UPDATE Personal SET Ansvarsomrade='Kontaktperson' WHERE Personnummer='" + person + "';");
		statement.executeUpdate();
		statement = connection.prepareStatement("COMMIT;");
		statement.executeUpdate();
	}

	public void bookPlay(String scen, String band, String datum, String tid) throws SQLException {
		statement = connection.prepareStatement("INSERT INTO festivalschema(Scen,Band,Datum,Tid) VALUES('" + scen + "','" + band + "','" + datum + "','" + tid + "')");
		statement.executeUpdate();
	}

	public ArrayList<String> getBands() throws SQLException {
		statement = connection.prepareStatement("SELECT Namn FROM Band;");
		sqlResult = statement.executeQuery();
		ArrayList<String> bandList = new ArrayList<>();
		while (sqlResult.next())
			bandList.add(sqlResult.getString(1));
		return bandList;
	}

	public boolean bandIsBooked(String band, String date, String time) throws SQLException {
		statement = connection.prepareStatement("SELECT COUNT(schemaID) FROM festivalschema WHERE band='" + band + "' AND datum='" + date + "' AND tid='" + time + "'");
		sqlResult = statement.executeQuery();
		sqlResult.next();
		if (sqlResult.getInt(1) > 0)
			return true;
		return false;
	}

	public boolean timeSlotTaken(String scene, String date, String time) throws SQLException {
		statement = connection.prepareStatement("SELECT COUNT(schemaID) FROM festivalschema WHERE scen='" + scene + "' AND datum='" + date + "' AND tid='" + time + "';");
		sqlResult = statement.executeQuery();
		sqlResult.next();
		if (sqlResult.getInt(1) > 0)
			return true;
		return false;
	}

	public ArrayList<String> getScenes() throws SQLException {
		statement = connection.prepareStatement("SELECT Namn FROM Scen;");
		sqlResult = statement.executeQuery();
		ArrayList<String> sceneList = new ArrayList<>();
		while (sqlResult.next())
			sceneList.add(sqlResult.getString(1));
		return sceneList;
	}

	public ArrayList<String> getBandsWithoutContacts() throws SQLException {
		statement = connection.prepareStatement("SELECT Namn FROM Band;");
		sqlResult = statement.executeQuery();
		ArrayList<String> resultList = new ArrayList<>();
		while (sqlResult.next())
			resultList.add(sqlResult.getString(1));
		ArrayList<String> contactList = new ArrayList<>();
		statement = connection.prepareStatement("SELECT bandNamn FROM Kontaktperson;");
		sqlResult = statement.executeQuery();
		while (sqlResult.next())
			contactList.add(sqlResult.getString(1));
		ArrayList<String> finalResult = new ArrayList<>();
		for (String band : resultList)
			if (!contactList.contains(band))
				finalResult.add(band);
		return finalResult;
	}

	private boolean tooManyToHandle(String person, String band) throws SQLException {
		statement = connection.prepareStatement(
				"SELECT COUNT(bandmedlem.id)FROM(bandmedlem JOIN band ON bandmedlem.band=band.namn JOIN kontaktperson ON band.namn=kontaktperson.bandNamn JOIN personal ON kontaktperson.personalNr=personal.personnummer) WHERE personal.personnummer='"
						+ person + "';");
		sqlResult = statement.executeQuery();
		sqlResult.next();
		int currentAmount = sqlResult.getInt(1);
		statement = connection.prepareStatement("SELECT COUNT(id) FROM Bandmedlem WHERE Band='" + band + "';");
		sqlResult = statement.executeQuery();
		sqlResult.next();
		int newBandAmount = sqlResult.getInt(1);
		if ((currentAmount + newBandAmount) > 10)
			return true;
		return false;
	}

	public ArrayList<String> getPersons() throws SQLException {
		statement = connection.prepareStatement("SELECT Personnummer,Namn FROM Personal;");
		sqlResult = statement.executeQuery();
		ArrayList<String> resultList = new ArrayList<>();
		while (sqlResult.next()) {
			String result = sqlResult.getString(1) + " - " + sqlResult.getString(2);
			resultList.add(result);
		}
		return resultList;
	}
}