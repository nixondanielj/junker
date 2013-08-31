package nixon.daniel.utils.general;

import java.util.HashMap;

// Lazily rolled my own rather than going to find one...
public class XMLUtils {
	public static String tag(String item, boolean open){
		// TODO see if format is faster
		StringBuffer s = new StringBuffer();
		s.append("<");
		if(!open){
			s.append("/");
		}
		s.append(item);
		s.append(">");
		return s.toString();
	}
	
	public static String toXML(String root, HashMap<String,String> keyValues) {
		StringBuffer result = new StringBuffer();
		result.append(tag(root, true));
		for(String key : keyValues.keySet()){
			result.append(wrap(key, keyValues.get(key)));
			result.append("\n");
		}
		result.append(tag(root, false));
		return result.toString();
	}
	
	public static String wrap(String name, String value){
		return String.format("%s%s%s", tag(name, true), value, tag(name,false));
	}
}
