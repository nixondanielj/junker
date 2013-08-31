package nixon.daniel.junker.dal;

import java.util.HashMap;

import nixon.daniel.junker.config.Settings;

public class Junk {
	private HashMap<String, String> properties;
	
	public String getId(){
		return this.getProperties().get(Settings.getIdKeyword());
	}

	public HashMap<String, String> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	} 
}
