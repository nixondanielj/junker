package nixon.daniel.junker.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nixon.daniel.junker.config.Settings;

public class JunkRepository extends Repository {

	public JunkRepository(String cString, String name) throws Exception{
		super(cString, Settings.getAnonDbName(), name);
	}
	
	public void create(Junk junk) throws SQLException{
		buildStructure(this.tableName, junk.getProperties().keySet());
		insert(junk);
	}

	public boolean verifyKeys(List<String> keys){
		boolean result = true;
		
		return result;
	}

	public Junk getJunkById(String id) throws SQLException {
		
		return junk;
	}
	
	private List<Junk> getJunks(String statement, Object... parameters) throws SQLException{
		ResultSet results = execute(statement, parameters);
		List<Junk> junks = new ArrayList<Junk>();
		while(results.next()){
			Junk junk = new Junk();
		}
	}
	
	private void insert(Junk junk) throws SQLException {
		String statement = "INSERT INTO " + this.tableName;
		statement += " (";
		String vals = "(";
		boolean first = true;
		for (String column : junk.getProperties().keySet()) {
			statement += (first ? "" : ",") + sanitize(column);
			vals += (first ? "'" : ",'") + sanitize(junk.getProperties().get(column)) + "'";
			first = false;
		}
		statement += ") VALUES " + vals + ")";
		executeNonQuery(statement);
	}
}
