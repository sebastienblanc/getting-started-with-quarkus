package org.sebi;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/tasks")
public class TaskResource {
    
    @RestClient
    ReverseService reverseService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> tasks(){
        return Task.listAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createTask(Task task){
        task.persist();
        return Response.status(Status.CREATED).entity(task).build();
    }

    @GET
    @Path("/reverse")
    @Produces(MediaType.TEXT_PLAIN)
    public List<String> reverse(){
        return Task.<Task>listAll().stream()
            .map(task -> reverseService.reverse(task.name))
            .collect(Collectors.toList());

    }
}