package nixon.daniel.utils.jersey;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CrossDomainFilter implements ContainerResponseFilter{
	@Override
	public ContainerResponse filter(ContainerRequest req, ContainerResponse resp){
		// TODO implement a way to inject necessary origins here
		resp.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
        resp.getHttpHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        resp.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
        // TODO implement a way to inject necessary methods here
        // must accept OPTIONS - used to check CORS policy
        resp.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // keep the setting for a day, should be inexpensive to refresh
        resp.getHttpHeaders().add("Access-Control-Max-Age", "86400");
        
		return resp;
	}
}
