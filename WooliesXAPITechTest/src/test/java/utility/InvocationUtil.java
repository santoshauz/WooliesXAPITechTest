package utility;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Invocation class for framing the endpoint
 * POST call includes city, country and appid
 */
public class InvocationUtil {
    private Map<String, String> mapWebServiceResponse = new HashMap<>();
    public Map<String, String> invoke(String endPoint, String city, String country, String appId) {
        Response response;
        response = RestAssured.given().config(RestAssured.config.sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .pathParam("city", city)
                .pathParam("country", country)
                .pathParam("appId", appId)
                .when().post(endPoint)
                .then()
                .extract().response();
        String strResponse = response.getBody().asString();
        mapWebServiceResponse.put("response", strResponse);
        mapWebServiceResponse.put("contentType", (response.contentType()));
        mapWebServiceResponse.put("statusCode", Integer.toString(response.getStatusCode()));
        return mapWebServiceResponse;
    }
}
