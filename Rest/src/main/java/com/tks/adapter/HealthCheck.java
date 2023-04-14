package com.tks.adapter;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@Liveness
public class HealthCheck implements org.eclipse.microprofile.health.HealthCheck {
    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.builder()
                .name("readiness")
                .up()
                .build();
    }
}
