package org.baeldung.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.baeldung.config.ResourceServerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceServerApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class OAuth2SwaggerIntegrationTest {

    private static final String URL_PREFIX = "http://localhost:8082/spring-security-oauth-resource";
    private String tokenValue = null;

    @Before
    public void obtainAccessToken() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "password");
        params.put("client_id", "fooClientIdPassword");
        params.put("username", "john");
        params.put("password", "123");
        final Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic("fooClientIdPassword", "secret")
                .and()
                .with()
                .params(params)
                .when()
                .post("http://localhost:8081/spring-security-oauth-server/oauth/token");

        tokenValue = response.jsonPath()
                .getString("access_token");
    }

    @Test
    public void whenVerifySwaggerDocIsWorking_thenOK() {
        Response response = RestAssured.get(URL_PREFIX + "/v2/api-docs");
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());

        response = RestAssured.given()
                .header("Authorization", "Bearer " + tokenValue)
                .get(URL_PREFIX + "/v2/api-docs");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenVerifySwaggerUIIsWorking_thenOK() {
        Response response = RestAssured.get(URL_PREFIX + "/swagger-ui.html");
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());

        response = RestAssured.given()
                .header("Authorization", "Bearer " + tokenValue)
                .get(URL_PREFIX + "/swagger-ui.html");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

}
