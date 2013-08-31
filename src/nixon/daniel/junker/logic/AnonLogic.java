package nixon.daniel.junker.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nixon.daniel.junker.dal.Junk;

public class AnonLogic extends JunkLogic {

	public AnonLogic(String name) throws Exception {
		super(name);
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
		if(model.getProperties().size() > 1){
			getRepositoryManager().getRepository().update(model);
		}
	}

	public List<JunkVM> retrieve(String id) throws SQLException, Exception {
		List<Junk> rawJunk = retrieveRawJunk(id);
		List<JunkVM> junkVMs = new ArrayList<JunkVM>();
		for(Junk junk : rawJunk){
			junkVMs.add(toJunkVM(junk));
		}
		return junkVMs;
	}

	private JunkVM toJunkVM(Junk junk) {
		JunkVM vm = new JunkVM(getName());
		vm.setProperties(junk.getProperties());
		return vm;
	}

	private List<Junk> retrieveRawJunk(String id) throws SQLException,
			Exception {
		List<Junk> rawJunk;
		if(id == null){
			rawJunk = getRepositoryManager().getRepository().getAllJunks();
		} else {
			rawJunk = getRepositoryManager().getRepository().getJunkById(id);
		}
		return rawJunk;
	}

}
