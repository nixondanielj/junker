package nixon.daniel.junker.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nixon.daniel.junker.config.Settings;

/*
 * hypothesis: better to avoid overhead of creating/dropping sprocs with tables
 * need to investigate performance
*/

public class JunkRepository extends Repository {

	public JunkRepository(String cString, String name) throws Exception {
		super(cString, Settings.getAnonDbName(), name);
	}

	public void create(Junk junk) throws SQLException {
		buildTableStructure(this.tableName, junk.getProperties().keySet());
		insert(junk);
	}

	public List<Junk> getAllJunks() throws SQLException {
		return getJunks(String.format("SELECT * FROM %s", this.tableName));
	}

	public List<Junk> getJunkById(String id) throws SQLException {
		return getJunks(String.format("SELECT * FROM %s WHERE %s = ?",
				this.tableName, Settings.getIdKeyword()), id);
	}

	private List<Junk> getJunks(String statement, Object... parameters)
			throws SQLException {
		ResultSet results = execute(statement, parameters);
		List<Junk> junks = new ArrayList<Junk>();
		while (results.next()) {
			HashMap<String, String> properties = new HashMap<String, String>();
			for (int i = 1; i <= results.getMetaData().getColumnCount(); i++) {
				String key = results.getMetaData().getColumnName(i);
				String value = results.getString(i);
				properties.put(key, value);
			}
			Junk junk = new Junk();
			junk.setProperties(properties);
			junks.add(junk);
		}
		return junks;
	}

	public void update(Junk model) throws SQLException {
		buildColumns(this.tableName, model.getProperties().keySet());
		String statement = "UPDATE %s SET %s WHERE %s = ?";
		String set = "";
		for(String column : model.getProperties().keySet()){
			if(set.length()!=0){
				set += ",";
			}
			set += String.format("%s = '%s'", sanitize(column), sanitize(model.getProperties().get(column)));
		}
		executeNonQuery(String.format(statement, this.tableName, set, Settings.getIdKeyword()), model.getId());
	}

	private void insert(Junk junk) throws SQLException {
		String statement = "INSERT INTO %s (%s) VALUES (%s)";
		String columns = "";
		String vals = "";
		for (String column : junk.getProperties().keySet()) {
			if(columns.length() != 0){
				columns += ",";
				vals+=",";
			}
			columns += sanitize(column);
			vals += "'" + sanitize(junk.getProperties().get(column)) + "'";
		}
		executeNonQuery(String.format(statement, this.tableName, columns, vals));
	}

}
