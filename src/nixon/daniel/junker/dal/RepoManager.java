package nixon.daniel.junker.dal;

import nixon.daniel.junker.config.Settings;


public class RepoManager {
	
	public RepoManager(String name) {
		setName(name);
	}
	
	public Repository getRepository() throws Exception {
		// lazy loaded to shorten connection active time
		if(repository == null){
			setRepository(new Repository(Settings.getConnectionString(), Settings.getAnonDbName(), getName()));
		}
		return this.repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public void kill(){
		this.repository.kill();
	}
	
	private String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}
	
	private Repository repository;
	private String name;
}
