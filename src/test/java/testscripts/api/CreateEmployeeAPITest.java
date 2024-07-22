package testscripts.api;

import constants.StatusCode;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloadgenerator.CreateEmployeePayload;
import pojo.CreateEmployeeRequestPayload;
import pojo.Data;
import pojo.Params;
import services.EmployeeService;
import utility.DataGenerator;

public class CreateEmployeeAPITest {

    EmployeeService employeeService = new EmployeeService();

    @Test
    void createEmployeeTest(){

        Data data = Data.builder().firstName(DataGenerator.generateFirstName()).middleName(DataGenerator.generateMiddleName())
                .lastName(DataGenerator.generateLastName()).locationId("13").joinedDate(DataGenerator.generateJoinedDate())
                .autoGenerateEmployeeId(true).build();
        Params params = Params.builder().build();
        CreateEmployeeRequestPayload createEmployeeRequestPayload = CreateEmployeeRequestPayload.builder().endpoint("employees").method("POST").data(data).params(params).build();

        Response response = employeeService.createEmployeeResponse(createEmployeeRequestPayload);
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), StatusCode.OK);
    }

    @Test
    void createEmployeeTest1(){

        CreateEmployeeRequestPayload createEmployeeRequestPayload = CreateEmployeePayload.generateEmployeePayload();
        String empNumber = employeeService.createEmployee(createEmployeeRequestPayload);
        System.out.println(empNumber);
    }
}
