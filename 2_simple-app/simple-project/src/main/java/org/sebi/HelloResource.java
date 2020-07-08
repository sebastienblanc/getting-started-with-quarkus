package org.sebi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/hello")
public class HelloResource {

    @RestClient
    ReverseService reverseService;

    @ConfigProperty(name = "greeting")
    String greeting;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return greeting;
    }
    
    @GET
    @Path("/fr")
    @Produces(MediaType.TEXT_PLAIN)
    public String bonjour() {
        return "bonjour";
    }

    @GET
    @Path("/reverse/{word}")
    @Produces(MediaType.TEXT_PLAIN)
    @Fallback(fallbackMethod = "fallback")
    @Retry(
        maxRetries = 3,
        delay = 2000
    )
    public String reverse(@PathParam("word")String word) {
        return reverseService.reverse(word);
    }

    private String fallback(String word){
        return "can not reverse " + word + ", service is down";
    }

}