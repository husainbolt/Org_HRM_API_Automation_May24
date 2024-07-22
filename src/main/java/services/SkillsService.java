package services;

import io.restassured.response.Response;
import pojo.AddSkillsRequestPayload;
import pojo.DeleteSkillsRequestPayload;
import utility.DataGenerator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SkillsService extends CommonService {

    public Response createSkill(AddSkillsRequestPayload payload){

        setAuthentication(getAuthToken());
        setBody(payload);
        return executeAPI("POST", propertyReader.getValue("orghrm.addskills"));
    }

    public String createSkill(){

        AddSkillsRequestPayload addSkillsRequestPayload = AddSkillsRequestPayload.builder().name(DataGenerator.getSkillName()).description(DataGenerator.getSkillDescription()).build();
        Response response = createSkill(addSkillsRequestPayload);
        //System.out.println(response.asPrettyString());
        //System.out.println(response.getStatusCode());
        return response.jsonPath().getString("data.id");

    }

    public Response getSkills(){

        resetService();
        setAuthentication(getAuthToken());

        Map<String, Object> queryParamMap = new LinkedHashMap<>();
        queryParamMap.put("limit", 50);
        queryParamMap.put("offset", 0);
        queryParamMap.put("sortingFeild","name");
        queryParamMap.put("sortingOrder", "ASC");
        setQueryParams(queryParamMap);

        return executeAPI("GET", propertyReader.getValue("orghrm.getskills"));
    }

    public boolean isSkillPresent(String skillid){

        Response getSkillsResponse = getSkills();
        List<String> skillsIDs = getSkillsResponse.jsonPath().getList("data.id");
        return skillsIDs.contains(skillid);
    }

    public Response deleteSkill(DeleteSkillsRequestPayload deleteSkillsRequestPayload){

        resetService();
        setAuthentication(getAuthToken());
        setBody(deleteSkillsRequestPayload);
        return executeAPI("DELETE", propertyReader.getValue("orghrm.deleteskills"));
    }

    public Response updateSkills(AddSkillsRequestPayload payload, String skillid){

        resetService();
        setBody(payload);
        return executeAPI("PATCH", propertyReader.getValue("orghrm.updateskills")+"/"+skillid);
    }
}
