package nixon.daniel.junker.config;

public class Settings {
	//returns the name of the junk property used as the identifier in the db
	public static String getIdKeyword(){
		return "id";
	}

	public static String getConnectionString() {
		return "jdbc:mysql://localhost/junk?user=juser&password=jpassword";
	}

	public static String getAnonDbName() {
		return "junk";
	}
}
