package services;

import io.restassured.response.Response;
import pojo.CreateProjectPayload;

import java.io.File;

public class ProjectService extends CommonService {

    public Response createProject(CreateProjectPayload payload){

        resetService();
        setAuthentication(getAuthToken());
        setBody(payload);
        return executeAPI("POST", "/api/projects");
    }

    public void validateCreateProjectSchema(Response response, File file){
        schemaValidation(response, file);
    }
}
