package com.ippon.server.service;


import com.ippon.server.Polynomial;
import io.grpc.stub.StreamObserver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DerivativeCalculatorServiceTest {

    @Autowired
    private DerivativeCalculatorService derivativeCalculatorService;

    @Test
    public void testCalculateDerivative_invalidPolynomial() {
        Integer[] coefficients = new Integer[] { 1, 2 };
        Integer[] exponents = new Integer[] { 1 };

        Polynomial polynomial = Polynomial.newBuilder().addAllCoefficients(Arrays.asList(coefficients)).addAllExponents(Arrays.asList(exponents)).build();

        List<Polynomial> result = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch failLatch = new CountDownLatch(1);
        StreamObserver<Polynomial> streamObserver = new StreamObserver<Polynomial>() {
            @Override
            public void onNext(Polynomial polynomial) {
                result.add(polynomial);
            }

            @Override
            public void onError(Throwable throwable) {
                failLatch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };
        try {
            derivativeCalculatorService.findDerivative(polynomial, streamObserver);
        } catch (Throwable throwable) {
            assertEquals(failLatch.getCount(), 0);
        }

        assertEquals(latch.getCount(), 1);
    }

    @Test
    public void testCalculateDerivative_successful() {
        Integer[] coefficients = new Integer[] { 1, 2 };
        Integer[] exponents = new Integer[] { 3, 2 };

        Polynomial polynomial = Polynomial.newBuilder().addAllCoefficients(Arrays.asList(coefficients)).addAllExponents(Arrays.asList(exponents)).build();

        List<Polynomial> result = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch failLatch = new CountDownLatch(1);
        StreamObserver<Polynomial> streamObserver = new StreamObserver<Polynomial>() {
            @Override
            public void onNext(Polynomial polynomial) {
                result.add(polynomial);
            }

            @Override
            public void onError(Throwable throwable) {
                failLatch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };
        derivativeCalculatorService.findDerivative(polynomial, streamObserver);

        Polynomial response = Polynomial.newBuilder().addAllExponents(Arrays.asList(2,1)).addAllCoefficients(Arrays.asList(3, 4)).build();

        assertEquals(latch.getCount(), 0);
        assertEquals(failLatch.getCount(), 1);
        assertEquals(result.get(0), response);
    }
}
