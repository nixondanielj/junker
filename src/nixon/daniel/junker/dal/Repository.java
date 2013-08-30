package nixon.daniel.junker.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class Repository {

	public Repository(String cString, String dbName, String tableName)
			throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(cString);
		this.tableName = sanitize(tableName);
		this.dbName = sanitize(dbName);
	}

	protected void buildStructure(String table, Set<String> set)
			throws SQLException {
		if (!tableExists(table)) {
			createTable(table);
		}
		for (String column : set) {
			if (!hasColumn(table, column)) {
				addColumn(column);
			}
		}
	}

	protected ResultSet execute(String statement, Object... objects)
			throws SQLException {
		PreparedStatement query = connection.prepareStatement(statement);
		for (int i = 0; i < objects.length; i++) {
			query.setString(i + 1, objects[i].toString());
		}
		setResults(query.executeQuery());
		return this.getResults();
	}

	protected void executeNonQuery(String statement, Object... objects)
			throws SQLException {
		PreparedStatement query = connection.prepareStatement(statement);
		for (int i = 0; i < objects.length; i++) {
			query.setString(i + 1, objects[i].toString());
		}
		query.executeUpdate();
		query.close();
	}

	protected String sanitize(String term) {
		return term.replace("'", "");
	}

	protected void createTable(String name) throws SQLException {
		executeNonQuery("CREATE TABLE " + name.replace("'", "") + "( Id varchar(5000) not null )");
	}

	protected boolean hasColumn(String tableName, String columnName)
			throws SQLException {
		return execute(
				"SELECT * FROM information_schema.columns WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? AND COLUMN_NAME = ?",
				this.dbName, tableName, columnName).next();
	}

	protected void addColumn(String columnName) throws SQLException {
		executeNonQuery("ALTER TABLE " + this.tableName + " ADD " + columnName.replace("'", "")
				+ " varchar(5000) default NULL");
	}

	protected boolean tableExists(String tableName) throws SQLException {
		return execute("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.tables WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?",
				this.dbName, tableName).next();
	}

	public void kill() {
		killResults();
		try{connection.close();}catch(Exception e){}
	}

	private void killResults() {
		try {
			if (getResults() != null) {
				getResults().close();
			}
		} catch (Exception e) {
		}
	}

	private void setResults(ResultSet results) throws SQLException {
		if (!results.equals(getResults())) {
			killResults();
			this.results = results;
		}
	}

	private ResultSet getResults() {
		return this.results;
	}

	protected Connection connection;
	private ResultSet results;
	protected String dbName;
	protected String tableName;
}
