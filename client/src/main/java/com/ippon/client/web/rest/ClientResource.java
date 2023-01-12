package com.ippon.client.web.rest;

import com.ippon.client.Polynomial;
import com.ippon.client.domain.PolynomialModel;
import com.ippon.client.service.DerivativeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/example")
public class ClientResource {

    public static final Logger LOGGER = LoggerFactory.getLogger(ClientResource.class);

    private DerivativeClient derivativeClient;

    public ClientResource(DerivativeClient derivativeClient) {
        this.derivativeClient = derivativeClient;
    }

    @PostMapping("/calculate-derivative")
    public ResponseEntity<PolynomialModel> findDerivative(@RequestBody PolynomialModel polynomialModel) {
        String polynomialExpression = polynomialModel.getPolynomial();
        String[] splitPolynomial = polynomialExpression.split("\\+");

        Integer[] coefficients = new Integer[splitPolynomial.length];
        Integer[] exponents = new Integer[splitPolynomial.length];

        for(int i = 0; i < splitPolynomial.length; i++) {
            String[] element = splitPolynomial[i].split("x\\^");
            LOGGER.info("Coefficient {}", element[0]);
            LOGGER.info("Exponent {}", element[1]);
            coefficients[i] = Integer.parseInt(element[0].trim());
            exponents[i] = Integer.parseInt(element[1].trim());
        }

        LOGGER.info("COEFFICIENTS {}", coefficients);
        LOGGER.info("EXPONENTS {}", exponents);

        Polynomial polynomial = Polynomial.newBuilder().addAllExponents(Arrays.asList(exponents)).addAllCoefficients(Arrays.asList(coefficients)).build();
        Polynomial derivative = derivativeClient.findDerivative(polynomial);

        List<Integer> derivativeCoefficients = derivative.getCoefficientsList();
        List<Integer> derivativeExponents = derivative.getExponentsList();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < splitPolynomial.length; ++i) {
            sb.append(derivativeCoefficients.get(i) + "x^" + derivativeExponents.get(i) + (((i+1) < splitPolynomial.length) ? " + " : ""));
        }

        PolynomialModel response = new PolynomialModel();
        response.setPolynomial(sb.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public void test() {
        LOGGER.info("Hello, World!");
    }
}
