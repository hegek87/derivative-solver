package com.ippon.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("derivative-server")
public class ApplicationProperties {

    private Grpc grpc = new Grpc();

    public Grpc getGrpc() {
        return grpc;
    }

    public static class Grpc {
        private String host;
        private int port;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
