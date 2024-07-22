package testscripts.api;

import constants.PathConstants;
import constants.StatusCode;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloadgenerator.CreateEmployeePayload;
import pojo.CreateEmployeeRequestPayload;
import pojo.CreateProjectPayload;
import pojo.Customer;
import services.EmployeeService;
import services.ProjectService;
import utility.DataGenerator;

import java.io.File;
import java.util.Arrays;

public class ProjectAPITest {

    EmployeeService employeeService = new EmployeeService();
    ProjectService projectService = new ProjectService();

    @Test
    public void createProjectTest(){

        CreateEmployeeRequestPayload createEmployeeRequestPayload = CreateEmployeePayload.generateEmployeePayload();
        String empNumber = employeeService.createEmployee(createEmployeeRequestPayload);
        System.out.println(empNumber);
        Customer customerPayload = Customer.builder().name(DataGenerator.generateFullName()).description(DataGenerator.getSkillDescription())
                .costCenter("1").build();

        CreateProjectPayload createProjectPayload = CreateProjectPayload.builder().name(DataGenerator.generateFullName()).description(DataGenerator.getSkillDescription())
                .addCustomer("1").customer(customerPayload).projectAdmin(Arrays.asList(empNumber))
                .costCenter("3").visibility(0).status(1).build();

        Response response = projectService.createProject(createProjectPayload);
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), StatusCode.CREATED);

        projectService.validateCreateProjectSchema(response, new File(PathConstants.CREATPROJECTSCHEMAPATH));

    }
}
