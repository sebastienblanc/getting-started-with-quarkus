package org.sebi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/reverse")
public class ReverseResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{word}")
    public String hello(@PathParam("word") String word) {
       StringBuilder builder = new StringBuilder(word);
       return builder.reverse().toString();
    }

}