package pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateEmployeeRequestPayload {
    public String endpoint;
    public String method;
    public Data data;
    public Params params;
}