package nixon.daniel.junker.logic;

import java.util.UUID;

import nixon.daniel.junker.dal.RepoManager;

public abstract class JunkLogic {
	
	public JunkLogic(String name) throws Exception{
		setRepositoryManager(new RepoManager(name));
		setName(name);
	}
	
	private RepoManager repositoryManager;

	protected RepoManager getRepositoryManager() {
		return repositoryManager;
	}

	protected void setRepositoryManager(RepoManager unitOfWork) {
		this.repositoryManager = unitOfWork;
	}

	public void persist(JunkFM junk) throws Exception {
		if(junk.getId() != null && getRepositoryManager().getRepository().getJunkById(junk.getId()) != null){
			// if object already exists
			update(junk);
		} else {
			if(junk.getId() == null){
				junk.setId(UUID.randomUUID().toString());
			}
			create(junk);
		}
		getRepositoryManager().kill();
	}
	
	protected abstract void create(JunkFM junk) throws Exception;
	protected abstract void update(JunkFM junk) throws Exception;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
}
