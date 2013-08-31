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

@Path("/junk")
public class JunkFace {
	
	@GET
	@Path("{name}/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public String getJunk(@PathParam("name") String name, @PathParam("id") String id) throws SQLException, Exception{
		return retrieve(name, id);
	}
	
	@GET
	@Path("{name}")
	@Produces(MediaType.APPLICATION_XML)
	public String getJunk(@PathParam("name") String name) throws SQLException, Exception{
		return retrieve(name, null);
	}
	
	@POST
	@Path("{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public String addJunk(@PathParam("name") String name, MultivaluedMap<String,String> params) throws Exception{
		String id = saveJunk(name, params);
		System.out.println("posted junk to " + name);
		return XMLUtils.wrap(Settings.getIdKeyword(), id);
	}
	
	@PUT
	@Path("{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateJunk(@PathParam("name") String name, MultivaluedMap<String,String> params) throws Exception{
		String id = saveJunk(name, params);
		System.out.println("put junk to " + name);
		return XMLUtils.wrap(Settings.getIdKeyword(), id);
	}
	
	@DELETE
	public void deleteJunk(){
		
	}
	
	private String saveJunk(String name, MultivaluedMap<String, String> params)
			throws Exception {
		if(name == null || name.length() == 0){
			throw new Exception();
		}
		JunkFM junk = new JunkFM(name);
		for(String key : params.keySet()){
			junk.getRawParameters().put(key,params.get(key));
		}
		return new AnonLogic(name).persist(junk);
	}

	private String retrieve(String name, String id) throws SQLException,
			Exception {
		List<JunkVM> junks = new AnonLogic(name).retrieve(id);
		StringBuffer xmlResult = new StringBuffer();
		xmlResult.append("<collection type=\"" + name + "\">\n");
		for(JunkVM junk : junks){
			xmlResult.append(XMLUtils.toXML(junk.getType(), junk.getProperties()));
			xmlResult.append("\n");
		}
		xmlResult.append("</collection>");
		System.out.println("Successfully returned collection "+ name);
		return xmlResult.toString();
	}
	
	
}
