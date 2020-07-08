# Adding Cloud Native Features

## Adding a REST Client

Add the rest-client extension `mvn quarkus:add-extension -Dextension="rest-client"`

Create an interface : 

```

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

```

Now we inject the rest client in our `HelloResource` class : 

```

@RestClient
ReverseService reverseService;

```

And finally add a new method : 

```

    @GET
    @Path("/reverse/{word}")
    @Produces(MediaType.TEXT_PLAIN)
    public String reverse(@PathParam("word")String word) {
        return reverseService.reverse(word);
    }

```

Now you need to start the Reverse Service. You can create it from scratch or use the one avaialble in the root of this repository (`reverve-service`)

Make a call to the new endpoint : `curl localhost:8080/hello/reverse/sebi`

## Adding Fault Tolerance

Add the fault-tolerance extension `mvn quarkus:add-extension -Dextension="smallrye-fault-tolerance"`

Update the `HelloResource` class : 

```
    
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

```


## Adding Liveness and Readiness Probes

Add the Heatlh extension `mvn quarkus:add-extension -Dextension="smallrye-health"`

You can now invoke the `/health` endpoint : 
`curl localhost:8080/health`

### Adding a liveness probe

```
package org.sebi;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
public class LivenessProbe implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.up("I'm alive");
    }

}
```

invoke the `/health` endpoint again 

## Adding metrics

Add the Metrics extension `mvn quarkus:add-extension -Dextension="smallrye-metrics"`

Add a TimeResource : 

```

import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;


@Path("/time")
public class TimeResource {

    @Counted(name = "time.now") 
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Instant now() {
        return Instant.now();
    }

    @Gauge(name = "offsetFromUTC", unit = "hours") 
    public int offsetFromUTC() {
        return TimeZone.getDefault().getOffset(Calendar.ZONE_OFFSET)/(3600*1000);
    }

}

```

Access the endpoint several time and now check the metrics : 
`curl -H "Accept: application/json" localhost:8080/metrics/application`

