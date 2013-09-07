package nixon.daniel.junker.api;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import nixon.daniel.junker.config.Settings;
import nixon.daniel.junker.logic.AnonLogic;
import nixon.daniel.junker.logic.JunkFM;
import nixon.daniel.junker.logic.JunkVM;
import nixon.daniel.utils.general.JSONUtils;
import nixon.daniel.utils.general.XMLUtils;
import nixon.daniel.utils.jersey.RestAPI;

import com.sun.jersey.api.NotFoundException;

@Path("{name}")
public class JunkFace {

	public JunkFace(@PathParam("name") String name) throws Exception {
		this.logic = new AnonLogic(name);
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getJunk(@PathParam("id") String id) throws SQLException,
			Exception {
		return retrieve(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getJunk() throws SQLException, Exception {
		return retrieve(null);
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String addJunk(MultivaluedMap<String, String> params)
			throws Exception {
		String id = saveJunk(params);
		System.out.println("posted junk to " + logic.getCollectionName());
		return String.format("{\"%s\":\"%s\"}", Settings.getIdKeyword(), id);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateJunk(MultivaluedMap<String, String> params)
			throws Exception {
		String id = saveJunk(params);
		System.out.println("put junk to " + logic.getCollectionName());
		return XMLUtils.wrap(Settings.getIdKeyword(), id);
	}

	@DELETE
	@Path("{id}")
	public void deleteJunk(@PathParam("id") String id) throws Exception {
		JunkFM junk = new JunkFM(logic.getCollectionName());
		junk.setId(id);
		if (!logic.delete(junk)) {
			throw new NotFoundException();
		}
	}

	@DELETE
	public void deleteJunk() throws Exception {
		if (!logic.delete(new JunkFM(logic.getCollectionName()))) {
			throw new NotFoundException();
		}
	}

	private String saveJunk(MultivaluedMap<String, String> params)
			throws Exception {
		JunkFM junk = new JunkFM(logic.getCollectionName());
		for (String key : params.keySet()) {
			junk.getRawParameters().put(key, params.get(key));
		}
		return logic.persist(junk);
	}

	private String retrieve(String id) throws Exception {
		String result = "";
		List<JunkVM> junks = logic.retrieve(id);
		if (junks == null) {
			throw new NotFoundException();
		}
		if (junks.size() > 1) {
			StringBuffer jsonResult = new StringBuffer();
			jsonResult.append("[");
			boolean first = true;
			for (JunkVM junk : junks) {
				if (!first) {
					jsonResult.append(",");
				}
				first = false;
				jsonResult.append(JSONUtils.toJSON(junk.getProperties()));
			}
			result = jsonResult.append("]").toString();
		} else if (junks.size() == 1) {
			result = JSONUtils.toJSON(junks.get(0).getProperties());
		}
		return result;
	}

	private RestAPI<JunkFM, JunkVM, String> logic;
}
