package nixon.daniel.junker.logic;

import java.util.ArrayList;
import java.util.List;

import nixon.daniel.junker.dal.Junk;

public class AnonLogic extends JunkLogic {

	public AnonLogic(String name) throws Exception {
		super(name);
	}

	@Override
	protected List<JunkVM> getCollection() throws Exception {
		List<JunkVM> vms = null;
		List<Junk> rawJunk = getRepositoryManager().getRepository()
				.getAllJunks();
		if (rawJunk != null) {
			vms = new ArrayList<JunkVM>();
			for (Junk junk : rawJunk) {
				vms.add(toJunkVM(junk));
			}
		}
		return vms;
	}

	@Override
	protected List<JunkVM> getItem(String id) throws Exception {
		List<JunkVM> vms = null;
		Junk junk = getRepositoryManager().getRepository().getJunkById(id);
		if(junk != null){
			vms = new ArrayList<JunkVM>();
			vms.add(toJunkVM(getRepositoryManager().getRepository().getJunkById(id)));
		}
		return vms;
	}

	@Override
	protected void create(JunkFM junk) throws Exception {
		Junk model = new Junk();
		model.setProperties(junk.getProperties());
		if (model.getProperties().size() > 1) {
			getRepositoryManager().getRepository().create(model);
		}
	}

	@Override
	protected void update(JunkFM junk) throws Exception {
		Junk model = new Junk();
		model.setProperties(junk.getProperties());
		if (model.getProperties().size() > 1) {
			getRepositoryManager().getRepository().update(model);
		}
	}

	@Override
	protected void deleteCollection() throws Exception {
		getRepositoryManager().getRepository().deleteAll();
	}

	@Override
	protected void deleteItem(String id) throws Exception {
		getRepositoryManager().getRepository().delete(id);
	}

	private JunkVM toJunkVM(Junk junk) {
		JunkVM vm = new JunkVM(getCollectionName());
		vm.setProperties(junk.getProperties());
		return vm;
	}
}
