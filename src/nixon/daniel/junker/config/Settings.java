package nixon.daniel.junker.config;

import java.util.logging.Logger;

public class Settings {
	//returns the name of the property used as the identifier in the db
	public static String getIdKeyword(){
		return "id";
	}

	public static String getConnectionString() {
		return "jdbc:mysql://localhost/junk?user=juser&password=jpassword";
	}

	public static String getAnonDbName() {
		return "junk";
	}
	
	public static Logger getLogger(Object obj){
		return Logger.getLogger(obj.getClass().getName());
	}
}
