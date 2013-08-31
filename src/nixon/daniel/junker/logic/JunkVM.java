package nixon.daniel.junker.logic;

import java.util.HashMap;

public class JunkVM extends JunkModel {

	public JunkVM(String name) {
		super(name);
	}
	
	public HashMap<String,String> getProperties() {
		if(properties == null){
			setProperties(new HashMap<String,String>());
		}
		return properties;
	}
	public void setProperties(HashMap<String,String> properties) {
		this.properties = properties;
	}
	
	private HashMap<String,String> properties;
	

}
