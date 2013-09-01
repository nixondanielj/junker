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
import nixon.daniel.utils.general.XMLUtils;
import nixon.daniel.utils.jersey.RestAPI;

import com.sun.jersey.api.NotFoundException;

@Path("{name}")
public class JunkFace {
	
	public JunkFace(@PathParam("name") String name) throws Exception{
		this.logic = new AnonLogic(name);
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	public String getJunk(@PathParam("id") String id) throws SQLException, Exception{
		return retrieve(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String getJunk() throws SQLException, Exception{
		return retrieve(null);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String addJunk(MultivaluedMap<String,String> params) throws Exception{
		String id = saveJunk(params);
		System.out.println("posted junk to " + logic.getCollectionName());
		return XMLUtils.wrap(Settings.getIdKeyword(), id);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateJunk(MultivaluedMap<String,String> params) throws Exception{
		String id = saveJunk(params);
		System.out.println("put junk to " + logic.getCollectionName());
		return XMLUtils.wrap(Settings.getIdKeyword(), id);
	}
	
	@DELETE
	@Path("{id}")
	public String deleteJunk(@PathParam("id") String id) throws Exception{
		JunkFM junk = new JunkFM(logic.getCollectionName());
		junk.setId(id);
		logic.delete(junk);
		return XMLUtils.wrap("result", "success");
	}
	
	@DELETE
	public String deleteJunk() throws Exception{
		logic.delete(new JunkFM(logic.getCollectionName()));
		return XMLUtils.wrap("result", "success");
	}
	
	private String saveJunk(MultivaluedMap<String, String> params)
			throws Exception {
		JunkFM junk = new JunkFM(logic.getCollectionName());
		for(String key : params.keySet()){
			junk.getRawParameters().put(key,params.get(key));
		}
		return logic.persist(junk);
	}

	private String retrieve(String id) throws Exception {
		List<JunkVM> junks = logic.retrieve(id);
		if(junks == null){
			throw new NotFoundException();
		}
		StringBuffer xmlResult = new StringBuffer();
		xmlResult.append("<collection type=\"" + logic.getCollectionName() + "\">\n");
		for(JunkVM junk : junks){
			xmlResult.append(XMLUtils.toXML(junk.getType(), junk.getProperties()));
			xmlResult.append("\n");
		}
		xmlResult.append("</collection>");
		System.out.println("Successfully returned collection "+ logic.getCollectionName());
		return xmlResult.toString();
	}
	
	private RestAPI<JunkFM, JunkVM, String> logic;
}
