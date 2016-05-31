package app;

import java.util.*;
import java.sql.*;

public class QueryHandler {
	private PreparedStatement statement;
	private Connection connection;
	private ResultSet sqlResult;

	public QueryHandler(Connection connection) {
		this.connection = connection;
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
}