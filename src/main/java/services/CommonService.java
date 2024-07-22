package services;

import base.BaseService;
import constants.PathConstants;
import io.restassured.response.Response;
import utility.PropertyReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonService extends BaseService {

    String csrfToken;
    private Map<String, String> cookies;
    protected PropertyReader propertyReader;
    public CommonService(){
        propertyReader = new PropertyReader(PathConstants.APIDETAILSFILEPATH);
    }


    public void getcsrfToken(){

        //baseService();
        resetService();
        Response response = executeAPI("GET", propertyReader.getValue("getcsrfToken"));
        //System.out.println(response.getStatusCode());
        String responseString = response.asString();
        String regex = "[(a-z0-9)]{32}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(responseString);
        while(matcher.find()){
            csrfToken = matcher.group();
        }
        System.out.println(csrfToken);
        cookies = response.getCookies();
    }

    public void validateCredentials(){

        setContentTypeURLEncodedHeader();
        Map<String, Object> formParamMap = new HashMap<>();
        formParamMap.put("login[_csrf_token]",csrfToken);
        formParamMap.put("hdnUserTimeZoneOffset",5.5);
        formParamMap.put("txtUsername",propertyReader.getValue("orghrm.username."+env));
        formParamMap.put("txtPassword",propertyReader.getValue("orghrm.password."+env));
        setFormParam(formParamMap);
        setCookies(cookies);
        Response response = executeAPI("POST", propertyReader.getValue("validateCredentials"));
        System.out.println(response.getStatusCode());
        cookies = response.getCookies();
    }

    public String getAccessToken(){
        resetService();
        setCookies(cookies);
        Response response = executeAPI("GET", propertyReader.getValue("getToken"));
        System.out.println(response.getStatusCode());
        System.out.println(response.asPrettyString());
        System.out.println(response.jsonPath().getString("token.access_token"));
        //cookies = response.getCookies();
        //setCookies(cookies);
        return response.jsonPath().getString("token.access_token");
    }

    public String getAuthToken(){
        getcsrfToken();
        validateCredentials();
        return getAccessToken();
    }

    public static void main(String args[]){
        CommonService commonService = new CommonService();
        System.out.println(commonService.getAuthToken());
    }
}
