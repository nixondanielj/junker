package nixon.daniel.junker.logic;

import java.util.HashMap;

import nixon.daniel.junker.config.Settings;

public class JunkModel {

	public JunkModel(String name){
		setType(name);
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getId() {
		return getProperties().get(Settings.getIdKeyword());
	}
	public void setId(String id) {
		getProperties().put(Settings.getIdKeyword(), id);
	}
	
	public HashMap<String,String> getProperties() {
		return properties;
	}
	public void setProperties(HashMap<String,String> properties) {
		this.properties = properties;
	}

	private String type;
	private HashMap<String,String> properties;
}
