package org.sebi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8081")
@Path("/reverse")
public interface ReverseService {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{word}")
    public String reverse(@PathParam("word") String word);
}