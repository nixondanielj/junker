package nixon.daniel.junker.logic;

import java.util.HashMap;

public class JunkVM extends JunkModel {

	public JunkVM(String name) {
		super(name);
	}
	
	public String toXML() {
		StringBuffer result = new StringBuffer();
		result.append(tag(getType(), true));
		for(String key : getProperties().keySet()){
			result.append(tag(key, true));
			result.append(getProperties().get(key));
			result.append(tag(key, false));
			result.append("\n");
		}
		result.append(tag(getType(), false));
		return result.toString();
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
	
	private String tag(String item, boolean open){
		StringBuffer s = new StringBuffer();
		s.append("<");
		if(!open){
			s.append("/");
		}
		s.append(item);
		s.append(">");
		return s.toString();
	}
	private HashMap<String,String> properties;
	

}
