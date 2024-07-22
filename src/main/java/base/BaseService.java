package base;

import constants.PathConstants;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import utility.PropertyReader;

import java.io.File;
import java.util.Map;

public class BaseService {

    private static RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
    protected String env;
    protected void setAuthentication(String token){

        requestSpecBuilder.addHeader("Authentication","Bearer " + token);

    }

    protected void baseService(){
        env = System.getProperty("env")==null? "qa":System.getProperty("env").toLowerCase();
        PropertyReader propertyReader = new PropertyReader(PathConstants.APIDETAILSFILEPATH);
        String uri = propertyReader.getValue("orghrm.baseURI."+env);

        requestSpecBuilder.setBaseUri(uri)
        .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .log(LogDetail.ALL);
    }

    protected void setBody(Object obj){

        requestSpecBuilder.setBody(obj);
    }

    protected Response executeAPI(String httpMethod, String endpoint){

        RequestSpecification requestSpecification = RestAssured.given().spec(requestSpecBuilder.build());
        Response response = null;
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();

            switch(httpMethod.toUpperCase()){

            case "GET":
                        response = requestSpecification
                        .when()
                        .get(endpoint).then().spec(responseSpecBuilder.build()).extract().response();
                break;
            case "POST":
                        response = requestSpecification
                        .when()
                        .post(endpoint).then().spec(responseSpecBuilder.build()).extract().response();
                break;
            case "PUT":
                        response = requestSpecification
                        .when()
                        .put(endpoint).then().spec(responseSpecBuilder.build()).extract().response();
                break;
            case "PATCH":
                        response = requestSpecification
                        .when()
                        .patch(endpoint).then().spec(responseSpecBuilder.build()).extract().response();
                break;
            case "DELETE":
                        response = requestSpecification
                        .when()
                        .delete(endpoint).then().spec(responseSpecBuilder.build()).extract().response();
                break;
        }

        return response;

    }

    protected void resetService(){
        requestSpecBuilder = new RequestSpecBuilder();
        baseService();
    }

    protected void setMultiPartHeader(){
        requestSpecBuilder.addHeader("Content-Type", "multipart/form-data");
    }

    protected void setMultiPartBody(File file){
        requestSpecBuilder.addMultiPart(file);
    }

    protected void setJSONHeader(){
        requestSpecBuilder.addHeader("Content-Type", "application/json");
    }
    protected void setContentTypeURLEncodedHeader(){
        requestSpecBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    protected void setFormParam(String key, String value){

        requestSpecBuilder.addFormParam(key, value);
    }

    protected void setFormParam(Map<String, Object> formParamMap){

        requestSpecBuilder.addFormParams(formParamMap);
    }

    protected void setCookies(Map<String,String> cookies){
        requestSpecBuilder.addCookies(cookies);
    }

    protected void setQueryParams(Map<String, Object> queryParams){
        requestSpecBuilder.addQueryParams(queryParams);
    }

    protected void schemaValidation(Response response, File file){

        MatcherAssert.assertThat(response.asString(), JsonSchemaValidator.matchesJsonSchema(file));
    }

}
