package pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddSkillsRequestPayload {

    public String name;
    public String description;
}
