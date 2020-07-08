package org.sebi;

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