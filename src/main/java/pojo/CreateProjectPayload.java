package pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Builder
public class CreateProjectPayload{
    public String name;
    public String description;
    public String addCustomer;
    public Customer customer;
    public List<String> projectAdmin;
    public String costCenter;
    public int visibility;
    public int status;
}
