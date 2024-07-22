package payloadgenerator;

import pojo.CreateEmployeeRequestPayload;
import pojo.Data;
import pojo.Params;
import utility.DataGenerator;

public class CreateEmployeePayload {

    public static CreateEmployeeRequestPayload generateEmployeePayload(){

        Data data = Data.builder().firstName(DataGenerator.generateFirstName()).middleName(DataGenerator.generateMiddleName())
                .lastName(DataGenerator.generateLastName()).locationId("13").joinedDate(DataGenerator.generateJoinedDate())
                .autoGenerateEmployeeId(true).build();
        Params params = Params.builder().build();
        CreateEmployeeRequestPayload createEmployeeRequestPayload = CreateEmployeeRequestPayload.builder().endpoint("employees").method("POST").data(data).params(params).build();
        return createEmployeeRequestPayload;
    }
}
