package nixon.daniel.junker.logic;

import java.util.HashMap;
import java.util.List;

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
				} else if (getRawParameters().size() == 1) {
					properties.put(key, getRawParameters().get(key).get(0));
				}
			}
		}
		return properties;
	}

	public HashMap<String, List<String>> getRawParameters() {
		return rawParameters;
	}

	public void setRawParameters(HashMap<String, List<String>> rawParameters) {
		this.rawParameters = rawParameters;
	}

	private HashMap<String, List<String>> rawParameters;
}
