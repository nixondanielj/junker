package nixon.daniel.junker.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nixon.daniel.junker.config.Settings;

public class JunkFM extends JunkModel {

	public JunkFM(String name) {
		super(name);
	}

	@Override
	public HashMap<String, String> getProperties() {
		HashMap<String, String> properties = new HashMap<String, String>();
		for (String key : getRawParameters().keySet()) {
			if (!key.contains("[")) {
				if (getRawParameters().get(key).size() == 0) {
					properties.put(key, null);
				} else if (getRawParameters().get(key).size() == 1) {
					properties.put(key, getRawParameters().get(key).get(0));
				}
			}
		}
		return properties;
	}
	
	@Override
	public void setId(String id){
		List<String> temp = new ArrayList<String>(1);
		temp.add(id);
		getRawParameters().put(Settings.getIdKeyword(), temp);
	}

	public HashMap<String, List<String>> getRawParameters() {
		if(this.rawParameters == null){
			setRawParameters(new HashMap<String, List<String>>());
		}
		return this.rawParameters;
	}

	public void setRawParameters(HashMap<String, List<String>> rawParameters) {
		this.rawParameters = rawParameters;
	}

	private HashMap<String, List<String>> rawParameters;
}
