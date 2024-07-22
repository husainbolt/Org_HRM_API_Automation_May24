package services;

import io.restassured.response.Response;
import pojo.CreateEmployeeRequestPayload;

import java.util.Arrays;

public class EmployeeService extends CommonService {

    public Response createEmployeeResponse(CreateEmployeeRequestPayload payload){

        baseService();
        setAuthentication(getAuthToken());
        setBody(Arrays.asList(payload));
        return executeAPI("POST", "/api/wizard");
    }

    public String createEmployee(CreateEmployeeRequestPayload payload){

        Response response = createEmployeeResponse(payload);
        System.out.println(response.getStatusCode());
        return response.jsonPath().getString("data.empNumber");

    }
}
