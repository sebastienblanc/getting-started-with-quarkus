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