package nixon.daniel.junker.logic;

import java.util.UUID;

import nixon.daniel.junker.dal.RepoManager;

public abstract class JunkLogic {
	
	public JunkLogic(String name) throws Exception{
		setRepositoryManager(new RepoManager(name));
	}
	
	private RepoManager repositoryManager;

	protected RepoManager getRepositoryManager() {
		return repositoryManager;
	}

	protected void setRepositoryManager(RepoManager unitOfWork) {
		this.repositoryManager = unitOfWork;
	}

	public void persist(JunkFM junk) {
		if(junk.getId() != null && getRepositoryManager().getRepository().getJunkById(junk.getId()) != null){
			// if already have object
			update(junk);
		} else {
			if(junk.getId() == null){
				junk.setId(UUID.randomUUID().toString());
			}
			create(junk);
		}
		getRepositoryManager().kill();
	}
	
	protected abstract void create(JunkFM junk);
	protected abstract void update(JunkFM junk);
}
