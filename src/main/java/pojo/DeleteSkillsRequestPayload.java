package pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DeleteSkillsRequestPayload {

    List<String> data;
}
