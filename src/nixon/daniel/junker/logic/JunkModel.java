package nixon.daniel.junker.logic;

import java.util.HashMap;

import nixon.daniel.junker.config.Settings;

public abstract class JunkModel {

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
	
	public abstract HashMap<String,String> getProperties();

	private String type;
	
}
