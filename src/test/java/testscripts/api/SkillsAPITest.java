package testscripts.api;

import services.SkillsService;
import constants.StatusCode;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.AddSkillsRequestPayload;
import pojo.DeleteSkillsRequestPayload;
import utility.DataGenerator;
import utility.ExcelOperations;

import java.io.IOException;
import java.util.Arrays;

public class SkillsAPITest {

    SkillsService skillsService = new SkillsService();

    @Test
    public void createSkillTest(){
        AddSkillsRequestPayload addSkillsRequestPayload = AddSkillsRequestPayload.builder().name(DataGenerator.getSkillName()).description(DataGenerator.getSkillDescription()).build();
        Response response = skillsService.createSkill(addSkillsRequestPayload);
        //System.out.println(response.asPrettyString());
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), StatusCode.CREATED);
        String skillid = response.jsonPath().getString("data.id");
        System.out.print(skillid);
        Assert.assertTrue(skillsService.isSkillPresent(skillid));
    }

    @Test
    public void getAllSkillsTest(){

        Response response = skillsService.getSkills();
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), StatusCode.OK);
    }

    @Test
    public void deleteSkillTest(){

        String skillid = skillsService.createSkill();
        System.out.println(skillid);
        DeleteSkillsRequestPayload deleteSkillsRequestPayload = DeleteSkillsRequestPayload.builder().data(Arrays.asList(skillid)).build();
        Response response = skillsService.deleteSkill(deleteSkillsRequestPayload);
        Assert.assertEquals(response.getStatusCode(), StatusCode.NOCONTENT);
        Assert.assertFalse(skillsService.isSkillPresent(skillid));
    }

    @Test
    public void updateSkillTest(){

        String skillid = skillsService.createSkill();
        System.out.println(skillid);
        AddSkillsRequestPayload addSkillsRequestPayload = AddSkillsRequestPayload.builder().name(DataGenerator.getSkillName()).description(DataGenerator.getSkillDescription()).build();
        Response response = skillsService.updateSkills(addSkillsRequestPayload, skillid);
        Assert.assertEquals(response.getStatusCode(), StatusCode.OK);
        Assert.assertEquals(response.jsonPath().getString("messages.success"), "Successfully Saved.");

    }

    @Test(dataProvider = "skillDataExcel")
    public void createSkillDataProviderTest(String skillName, String skillDescription, double expectedStatusCode){
        AddSkillsRequestPayload addSkillsRequestPayload = AddSkillsRequestPayload.builder().name(skillName).description(skillDescription).build();
        Response response = skillsService.createSkill(addSkillsRequestPayload);
        //System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
        if(expectedStatusCode==StatusCode.CREATED) {
            String skillid = response.jsonPath().getString("data.id");
            System.out.print(skillid);
            Assert.assertTrue(skillsService.isSkillPresent(skillid));
        }
        else if(expectedStatusCode==StatusCode.BADREQUEST){
            String errorMessage = response.jsonPath().getString("messages.error");
            Assert.assertEquals(errorMessage,"Duplicate skill ids not allowed");
        }
    }

    @DataProvider(name = "skillData")
    public Object[][] getSkillData(){

        Object[][] data = new Object[4][3];
        data[0][0] = "new skill added 1";
        data[0][1] = "new description added 1";
        data[0][2] = 201;

        data[1][0] = "new skill added 2";
        data[1][1] = "new description added 2";
        data[1][2] = 201;

        data[2][0] = "new skill added 3";
        data[2][1] = "new description added 3";
        data[2][2] = 201;

        data[3][0] = "";
        data[3][1] = "new description added 4";
        data[3][2] = 400;

        return data;
    }

    @DataProvider(name="skillDataExcel")
    public Object[][] getSkillDataExcel() throws IOException {
        return ExcelOperations.excelReadOperation("src/test/resources/testdata/skillDataexcel.xlsx", "Sheet1");
    }

}
