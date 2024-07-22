package pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Data{
    public String firstName;
    public String middleName;
    public String lastName;
    public String locationId;
    public String joinedDate;
    public boolean autoGenerateEmployeeId;
    //public String employeeId;
}
