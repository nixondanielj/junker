package nixon.daniel.utils.jersey;

import java.util.List;

public interface RestAPI<IN,OUT,ID> {
	List<OUT> retrieve(ID id) throws Exception;
	ID persist(IN fm) throws Exception;
	void delete(IN fm) throws Exception;
	String getCollectionName();
}
