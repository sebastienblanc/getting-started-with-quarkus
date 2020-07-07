# A Complete Quarkus Application , step by step

## Create a new Quarkus project 

`mvn io.quarkus:quarkus-maven-plugin:1.5.2.Final:create`

## Start the project 

`mvn quarkus:dev` 

## Add a new endpoint 

```
    @GET
    @Path("/fr")
    @Produces(MediaType.TEXT_PLAIN)
    public String bonjour() {
        return "hello";
    }

```

Try out your new endpoint : `curl localhost:8080/hello/fr`

## Add Configuration 

Add in your Resource class: 

```

 @ConfigProperty(name = "greeting")
 String greeting;

```

Add in your `application.properties` : 

```

greeting=Hola

```

Try to change the value of the property and try again. 


## Add Persistence

Let's start by adding the needed extensions : 

`mvn quarkus:add-extension -Dextension="quarkus-resteasy-jsonb, quarkus-jdbc-h2, quarkus-hibernate-orm-panache"`

### Add configuration

```
quarkus.datasource.url=jdbc:h2:mem:default
quarkus.datasource.driver=org.h2.Driver
quarkus.hibernate-orm.database.generation=drop-and-create

```


### Create an Entity 

```

package org.sebi;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Task extends PanacheEntity {

    public String name;

    public boolean done;
    
}

```

### Create a new Endpoint 

```

package org.sebi;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/tasks")
public class TaskResource {
    
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
}

```

