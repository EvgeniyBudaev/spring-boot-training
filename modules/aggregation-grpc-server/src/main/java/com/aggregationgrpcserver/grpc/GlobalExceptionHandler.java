package com.aggregationgrpcserver.grpc;

import io.grpc.Status;
import io.grpc.StatusException;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class GlobalExceptionHandler implements GrpcExceptionHandler {
    @Override
    public StatusException handleException(Throwable exception) {
        return new StatusException(Status.INTERNAL
                .withDescription("Internal server error")
                .withCause(exception));
    }
}
