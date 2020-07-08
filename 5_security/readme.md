# Secure your Quarkus Application 

## Configure a Keycloak instance 

Download or use the Docker image 

For instance , with Docker : 

`docker run --name keycloak -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -p 8180:8080 quay.io/keycloak/keycloak:10.0.2`

Import the realm `quarkus-realm.json`

## Add the OIDC Extension

`mvn quarkus:add-extension -Dextension="quarkus-oidc"`

Add some configuration : 

```
quarkus.oidc.auth-server-url=http://localhost:8180/auth/realms/quarkus-demo
quarkus.oidc.client-id=task-app
quarkus.oidc.application-type=web-app
quarkus.oidc.logout.path=/logout
quarkus.oidc.logout.post-logout-path=/view

```

## Add A new Endpoint 

```

package org.sebi;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/view")
@RolesAllowed("user")
public class ViewResource {
    @Inject
    Template tasks;
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance tasksUI(){
        return tasks.data("tasks", Task.listAll());
    }
}

```

Open a browser and access to : http://localhost:8080/view

You should be redirected to a login screen, you can login with `sebi/sebi` 


