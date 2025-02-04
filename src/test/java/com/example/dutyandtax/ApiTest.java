package com.example.dutyandtax;

import com.example.dutyandtax.apiData.Specifications;
import com.example.dutyandtax.apiData.TestRequestAndResponseGenerator;
import com.example.dutyandtax.apiData.requestData.RequestRoot;
import com.example.dutyandtax.apiData.responseData.ResponseCode400;
import com.example.dutyandtax.apiData.responseData.ResponseCode401;
import com.example.dutyandtax.apiData.responseData.ResponseRoot;
import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.common.mapper.TypeRef;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTest {
    ArrayList<RequestRoot> requestRoot;
    SoftAssertions softly = new SoftAssertions();
    private final static String URL = "https://api.integration.eurora.com";
    private final static String PATH = "customs-calculator/v1/shopping-cart";

    @BeforeSuite
    void setUp() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", "Chrome")
                        .put("Browser.Version", "87.0.4280.88")
                        .put("URL", "https://eliasnogueira.com")
                        .build());
    }

    /**
     * Checks for unauthorized access.
     *
     * @result Backend should provide unauthorized response.
     */
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Something")
    @DisplayName("Should return unauthorized 401")
    void shouldReturnUnauthorized401() {
        step("Test step");
        requestRoot = new TestRequestAndResponseGenerator().getRequestRoot();
        Specifications.installSpec(Specifications.requestWithoutAuthSpec(URL), Specifications.responseSpec(401));
        ResponseCode401 responseCode = given()
                .body(requestRoot)
                .when()
                .post(PATH)
                .then().extract().as(ResponseCode401.class);
        assertThat(responseCode.getResponseMessage()).isEqualTo("Unauthorized");
    }

    @Nested
    @DisplayName("Various 400 scenarios")
    class Various400Scenarios {
        /**
         * Test for not supported currency code
         *
         * @result Backend should reject unsupported currency code
         * with information that this currency not found.
         */
        @Test
        @DisplayName("Should try not supported currency code")
        void shouldTryNotSupportedCurrencyCode() {
            requestRoot = new TestRequestAndResponseGenerator().getRequestRoot();
            requestRoot.getFirst().setOrderCurrency("MIK");
            Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(400));
            ResponseCode400 responseCode = given()
                    .body(requestRoot)
                    .when()
                    .post(PATH)
                    .then().extract().as(ResponseCode400.class);

            softly.assertThat(responseCode.type).isEqualTo("CONSTRAINT_VIOLATION");
            softly.assertThat(responseCode.getRows().getFirst().reason).isEqualTo("Currency");
            softly.assertThat(responseCode.getRows().getFirst().message).isEqualTo("currency not found");
            softly.assertAll();
        }

        /**
         * Test for possibility to provide backend currency code in lowercase.
         *
         * @result Backend should respond with 400 and provide information
         * that currency should be in Uppercase.
         */
        @Test
        @DisplayName("Should try supported currency code in lowercase")
        void shouldTrySupportedCurrencyCodeInLowercase() {
            requestRoot = new TestRequestAndResponseGenerator().getRequestRoot();
            requestRoot.getFirst().setOrderCurrency("usd");
            Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(400));
            ResponseCode400 responseCode = given()
                    .body(requestRoot)
                    .when()
                    .post(PATH)
                    .then().extract().as(ResponseCode400.class);

            softly.assertThat(responseCode.type).isEqualTo("CONSTRAINT_VIOLATION");
            softly.assertThat(responseCode.getRows().getFirst().reason).isEqualTo("Uppercase");
            softly.assertThat(responseCode.getRows().getFirst().message).isEqualTo("string must be uppercase");
            softly.assertAll();
        }

        /**
         * Test to check if backend will not consume transportation price with negative value.
         *
         * @result Backend should respond with 400 and provide information
         * that transportation price field much contain positive double.
         */
        @Test
        @DisplayName("Should try negative value for currency")
        void shouldTryNegativeValueForCurrency() {
            requestRoot = new TestRequestAndResponseGenerator().getRequestRoot();
            requestRoot.getFirst().setTransportationPrice(-45);
            Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(400));
            ResponseCode400 responseCode = given()
                    .body(requestRoot)
                    .when()
                    .post(PATH)
                    .then().extract().as(ResponseCode400.class);
            softly.assertThat(responseCode.getRows().getFirst().field).isEqualTo("transportationPrice");
            softly.assertThat(responseCode.getRows().getFirst().message).isEqualTo("must be greater than or equal to 0.0");
            softly.assertAll();
        }

        /**
         * Test if backend will check length constrains for goods description
         *
         * @result Backend should respond with 400 and provide information
         * that goods descriptions size should be between 3 and 512 bytes
         */
        @ParameterizedTest(name = "#{index} - Run test with goods description with following strings= \"{0}\"")
        @DisplayName("Should try too short or to long description for goods")
        @ValueSource(strings = {"I", "Op", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis nec tincidunt " +
                "mauris. Phasellus nec lorem at odio rutrum mattis. Vestibulum porttitor varius tortor sed vestibulum. " +
                "Suspendisse at tellus at nisi finibus eleifend. Maecenas ac pretium massa. Maecenas varius, nibh et scelerisque " +
                "pretium, orci diam sagittis elit, ut malesuada ligula nulla sed dolor. Praesent faucibus ipsum non urna facilisis " +
                "elementum. Ut sodales varius nisi, eu malesuada lectus. Pellentesque sit amet malesuada dui, vitae sodales sed."})
        void shouldTryTooShortDescriptionForGoods(String testString) {
            requestRoot = new TestRequestAndResponseGenerator().getRequestRoot();
            requestRoot.getFirst().getGoods().getFirst().setDescription(testString);
            Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(400));
            ResponseCode400 responseCode = given()
                    .body(requestRoot)
                    .when()
                    .post(PATH)
                    .then().extract().as(ResponseCode400.class);
            softly.assertThat(responseCode.type).isEqualTo("CONSTRAINT_VIOLATION");
            softly.assertThat(responseCode.rows.getFirst().reason).isEqualTo("Size");
            softly.assertThat(responseCode.rows.getFirst().message).isEqualTo("size must be between 3 and 512");
            softly.assertAll();
        }
        /**
         * Test if backend will check minimal quantity value
         *
         * @result Backend should respond with 400 and provide information
         * that quantity number couldn't be <=0
         */
        @ParameterizedTest(name = "#{index} - Run test with quantity={0}")
        @DisplayName("Should try to pass quantity with zero or negative values")
        @ValueSource(ints = {-23, 0})
        void shouldTryToPassQuantityWithZeroOrNegativeValues(int value) {
            requestRoot = new TestRequestAndResponseGenerator().getRequestRoot();
            requestRoot.getFirst().getGoods().getFirst().setQuantity(value);
            Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(400));
            ResponseCode400 responseCode = given()
                    .body(requestRoot)
                    .when()
                    .post(PATH)
                    .then().extract().as(ResponseCode400.class);
            softly.assertThat(responseCode.type).isEqualTo("CONSTRAINT_VIOLATION");
            softly.assertThat(responseCode.rows.getFirst().reason).isEqualTo("Min");
            softly.assertThat(responseCode.rows.getFirst().message).isEqualTo("must be greater than or equal to 1");
            softly.assertAll();
        }
        /**
         * Test, if the backend will check for valid additional value share string, should be in the variants list
         *
         * @result Backend should respond with 400 and provide information
         * that provided value string are not recognised within available variants
         */
        @Test
        @DisplayName("Should try to pass wrong value to additionalValueShare")
        void shouldTryToPassWrongValueToAdditionalValueShare() {
            String addValueShare = "SOMETHING";
            requestRoot = new TestRequestAndResponseGenerator().getRequestRoot();
            requestRoot.getFirst().setAdditionalValueShare(addValueShare);
            Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(400));
            ResponseCode400 responseCode = given()
                    .body(requestRoot)
                    .when()
                    .post(PATH)
                    .then().extract().as(ResponseCode400.class);
            softly.assertThat(responseCode.type).isEqualTo("ARGUMENT_NOT_VALID");
            softly.assertThat(responseCode.rows.getFirst().reason).isEqualTo("InvalidValue");
            softly.assertThat(responseCode.rows.getFirst().message).isEqualTo("Value not recognized (" + addValueShare + "), please refer to specification for available values.");
            softly.assertAll();
        }
    }

    @Nested
    @DisplayName("Various 200 scenarios")
    class Various200Scenarios {
        /**
         * Test if backend will accept transportation price in allowed range.
         *
         * @result Backend should respond with 200 for all provided doubles
         */
        @ParameterizedTest(name = "#{index} - Run test with transportation price={0}")
        @DisplayName("Should try different values for currencies")
        @ValueSource(doubles = {0.1, 1000, 1234567890, Integer.MAX_VALUE})
        void shouldTryDifferentValuesForCurrencies(double value) {
            requestRoot = new TestRequestAndResponseGenerator().getRequestRoot();
            requestRoot.getFirst().setTransportationPrice(value);
            Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(200));
            ArrayList<ResponseRoot> responseRoot = given()
                    .body(requestRoot)
                    .when()
                    .post(PATH)
                    .then()
                    .extract().as(new TypeRef<>() {
                    });
            softly.assertThat(responseRoot.getFirst().getTotalDuties()).isGreaterThan(0.0);
        }

        /**
         * Test will check that externalId string in request is identical to the externalId in response.
         *
         * @result Backend should respond with 200 and externalId value will
         * be identical in the response for the shipments and in the good details
         */
        @Test
        @DisplayName("Should return 200 with matching externalId")
        void shouldReturn200withMatchingExternalId() {
            requestRoot = new TestRequestAndResponseGenerator().getRequestRoot();
            Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(200));
            ArrayList<ResponseRoot> responseRoot = given()
                    .body(requestRoot)
                    .when()
                    .post(PATH)
                    .then()
                    .extract().as(new TypeRef<>() {
                    });
            softly.assertThat(responseRoot.getFirst().getExternalId()).isEqualTo(requestRoot.getFirst().getExternalId());
            softly.assertThat(responseRoot.getFirst().getGoods().getFirst().getExternalId()).isEqualTo(requestRoot.getFirst().getGoods().getFirst().getExternalId());
            softly.assertAll();
        }

        /**
         * Test will check that additional value share can consume strings in range.
         *
         * @result Backend should respond with 200
         */
        @ParameterizedTest(name = "#{index} - Run test with additional value share variants={0}")
        @DisplayName("Should try different additional value share")
        @ValueSource(strings = {"MANUAL", "VALUE", "WEIGHT"})
        void shouldTryDifferentAdditionalValueShare(String value) {
            requestRoot = new TestRequestAndResponseGenerator().getRequestRoot();
            requestRoot.getFirst().setAdditionalValueShare(value);
            Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(200));
            ArrayList<ResponseRoot> responseRoot = given()
                    .body(requestRoot)
                    .when()
                    .post(PATH)
                    .then()
                    .extract().as(new TypeRef<>() {
                    });
            softly.assertThat(responseRoot.getFirst().getExternalId()).isEqualTo(requestRoot.getFirst().getExternalId());
            softly.assertThat(responseRoot.getFirst().getGoods().getFirst().getExternalId()).isEqualTo(requestRoot.getFirst().getGoods().getFirst().getExternalId());
            softly.assertAll();
        }
        /**
         * Simple test that checks work without POJO response class .
         *
         * @result Backend should respond with 200
         */
        @Test
        @DisplayName("Should return 200 with no POJO response class (Plain Old Java Object)")
        void shouldReturn200WithNoPOJO() {
            requestRoot = new TestRequestAndResponseGenerator().getRequestRoot();
            Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(200));
            given()
                    .body(requestRoot)
                    .when()
                    .post(PATH)
                    .then().log().all()
                    .body("externalId", containsInAnyOrder("123e4567-e89b-12d3-a456-426655440000"));
        }
    }
}
