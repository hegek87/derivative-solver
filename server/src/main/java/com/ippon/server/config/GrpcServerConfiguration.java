package com.ippon.server.config;

import com.ippon.server.service.DerivativeCalculatorService;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcServerConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcServerConfiguration.class);

    private int port;
    private Server server;
    private DerivativeCalculatorService derivativeCalculatorService;

    public GrpcServerConfiguration(ApplicationProperties applicationProperties, DerivativeCalculatorService derivativeCalculatorService) {
        this.port = applicationProperties.getGrpc().getPort();
        this.derivativeCalculatorService = derivativeCalculatorService;
    }

    @Bean
    public Server grpcServer() throws IOException {
        LOGGER.info("Starting GRPC Server");
        this.server = ServerBuilder.forPort(this.port).addService((BindableService) derivativeCalculatorService).build();
        this.server.start();
        return this.server;
    }
}
