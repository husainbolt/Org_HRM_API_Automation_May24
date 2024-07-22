package pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Customer{
    public String name;
    public String description;
    public String costCenter;
}
