package nixon.daniel.junker.api;

import java.util.HashMap;
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

import nixon.daniel.junker.logic.AnonLogic;
import nixon.daniel.junker.logic.JunkFM;
import nixon.daniel.junker.logic.JunkVM;

@Path("/junk")
public class JunkFace {
	
	@GET
	@Path("{name}")
	@Produces(MediaType.APPLICATION_XML)
	public String getJunk(@PathParam("name") String name, @PathParam("id") String id){
		List<JunkVM> junks = new AnonLogic().retrieve(name);
	}
	
	@POST
	@Path("{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void addJunk(@PathParam("name") String name, MultivaluedMap<String,String> params) throws Exception{
		if(name == null || name.length() == 0){
			throw new Exception();
		}
		JunkFM junk = new JunkFM(name);
		for(String key : params.keySet()){
			junk.getRawProperties().put(key,params.get(key));
		}
		new AnonLogic(name).persist(junk);
	}
	
	@PUT
	public void updateJunk(){
		
	}
	
	@DELETE
	public void deleteJunk(){
		
	}
}
