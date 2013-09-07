package nixon.daniel.utils.general;

import java.util.HashMap;

public class JSONUtils {
	public static String toJSON(HashMap<String,String> keyVals){
		StringBuffer result = new StringBuffer();
		result.append("{");
		boolean first = true;
		for(String key : keyVals.keySet()){
			if(!first) {
				result.append(",");
			}
			first = false;
			result.append(String.format("\"%s\":\"%s\"", key, keyVals.get(key)));
		}
		result.append("}");
		return result.toString();
	}
}
