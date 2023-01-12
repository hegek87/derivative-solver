package com.ippon.client.service;

import com.ippon.client.DerivativeCalculatorGrpc;
import com.ippon.client.Polynomial;
import com.ippon.client.config.ApplicationProperties;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class DerivativeClient {

    private ManagedChannel managedChannel;

    private DerivativeCalculatorGrpc.DerivativeCalculatorBlockingStub client;

    public DerivativeClient(ApplicationProperties applicationProperties) {
        this.managedChannel = ManagedChannelBuilder
                .forAddress(applicationProperties.getGrpc().getHost(), applicationProperties.getGrpc().getPort())
                .usePlaintext()
                .build();
        this.client = DerivativeCalculatorGrpc.newBlockingStub(this.managedChannel);
    }

    public Polynomial findDerivative(Polynomial polynomial) {
        return this.client.findDerivative(polynomial);
    }
}
