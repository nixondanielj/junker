package nixon.daniel.junker.dal;

import nixon.daniel.junker.config.Settings;


public class RepoManager {
	
	public RepoManager(String name) {
		setName(name);
	}
	
	public JunkRepository getRepository() throws Exception {
		// lazy loaded to shorten connection active time
		if(repository == null){
			setRepository(new JunkRepository(Settings.getConnectionString(), getName()));
		}
		return this.repository;
	}

	public void setRepository(JunkRepository repository) {
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
	
	private JunkRepository repository;
	private String name;
}
