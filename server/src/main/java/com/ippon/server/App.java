package com.ippon.server;

import com.ippon.server.config.ApplicationProperties;
import com.ippon.server.config.GrpcServerConfiguration;
import com.ippon.server.service.DerivativeCalculatorService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {

        LOGGER.info(
            "\n----------------------------------------------------------\n\t"
                    + "Application '{}' is running! Access URLs:\n\t"
                    + "Profile(s): \t{}\n\t"
                    + "\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            env.getActiveProfiles());
    }
}
