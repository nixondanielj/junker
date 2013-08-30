package nixon.daniel.junker.logic;

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
		// TODO Auto-generated method stub
	}

	public List<JunkVM> retrieve(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
