package nixon.daniel.junker.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	public List<Junk> getAllJunks() throws SQLException{
		return getJunks("SELECT * FROM ?", this.tableName);
	}

	public Junk getJunkById(String id) throws SQLException {
		List<Junk> junks = getJunks("SELECT * FROM ? WHERE Id = ?", this.tableName, id);
		if(junks.size() > 0){
			return junks.get(0);
		}
		return null;
	}
	
	private List<Junk> getJunks(String statement, Object... parameters) throws SQLException{
		ResultSet results = execute(statement, parameters);
		List<Junk> junks = new ArrayList<Junk>();
		while(results.next()){
			HashMap<String,String> properties = new HashMap<String,String>();
			for(int i = 1; i <= results.getMetaData().getColumnCount(); i++){
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
