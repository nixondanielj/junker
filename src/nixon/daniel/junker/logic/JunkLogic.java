package nixon.daniel.junker.logic;

import java.util.List;
import java.util.UUID;

import nixon.daniel.junker.dal.RepoManager;
import nixon.daniel.utils.jersey.RestAPI;

public abstract class JunkLogic implements RestAPI<JunkFM, JunkVM, String> {

	public JunkLogic(String name) throws Exception {
		setName(name);
	}

	public List<JunkVM> retrieve(String id) throws Exception {
		List<JunkVM> junk = null;
		if (id != null) {
			junk = getItem(id);
		} else {
			junk = getCollection();
		}
		getRepositoryManager().kill();
		return junk;
	}

	public String persist(JunkFM junk) throws Exception {
		if (junk.getId() != null
				&& getRepositoryManager().getRepository().getJunkById(
						junk.getId()) != null) {
			// if object already exists
			update(junk);
		} else {
			if (junk.getId() == null) {
				junk.setId(UUID.randomUUID().toString());
			}
			create(junk);
		}
		getRepositoryManager().kill();
		return junk.getId();
	}

	public boolean delete(JunkFM junk) throws Exception {
		boolean found = false;
		if (getRepositoryManager().getRepository().alreadyExists()) {
			if (junk.getId() == null) {
				deleteCollection();
				found = true;
			} else if(getRepositoryManager().getRepository().getJunkById(
						junk.getId()) != null){
				deleteItem(junk.getId());
				found = true;
			}
		}
		getRepositoryManager().kill();
		return found;
	}

	protected abstract void create(JunkFM junk) throws Exception;

	protected abstract void update(JunkFM junk) throws Exception;

	protected abstract void deleteCollection() throws Exception;

	protected abstract void deleteItem(String id) throws Exception;

	protected abstract List<JunkVM> getCollection() throws Exception;

	protected abstract List<JunkVM> getItem(String id) throws Exception;

	public String getCollectionName() {
		return name;
	}

	public void setName(String name) {
		setRepositoryManager(new RepoManager(name));
		this.name = name;
	}

	protected RepoManager getRepositoryManager() {
		return repositoryManager;
	}

	protected void setRepositoryManager(RepoManager unitOfWork) {
		this.repositoryManager = unitOfWork;
	}

	private String name;
	private RepoManager repositoryManager;

}
