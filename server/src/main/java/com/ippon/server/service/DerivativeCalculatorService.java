package com.ippon.server.service;

import com.ippon.server.DerivativeCalculatorGrpc;
import com.ippon.server.Polynomial;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DerivativeCalculatorService extends DerivativeCalculatorGrpc.DerivativeCalculatorImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(DerivativeCalculatorService.class);

    @Override
    public void findDerivative(Polynomial polynomial, StreamObserver<Polynomial> responseObserver) {
        if(polynomial.getCoefficientsCount() != polynomial.getExponentsCount()) {
            RuntimeException err = new RuntimeException("Invalid polynomial definition - It should have the same amount of exponents and coefficients");
            responseObserver.onError(err);
            throw err;
        }

        List<Integer> coefficients = polynomial.getCoefficientsList();
        List<Integer> exponents = polynomial.getExponentsList();

        List<Integer> updatedCoefficient = new ArrayList<>();
        List<Integer> updatedExponents = exponents.stream().map(exponent -> exponent - 1).collect(Collectors.toList());

        for(int i = 0; i < coefficients.size(); ++i) {
            updatedCoefficient.add(i, coefficients.get(i) * exponents.get(i));
        }

        responseObserver.onNext(Polynomial.newBuilder().addAllCoefficients(updatedCoefficient).addAllExponents(updatedExponents).build());
        responseObserver.onCompleted();
    }
}
