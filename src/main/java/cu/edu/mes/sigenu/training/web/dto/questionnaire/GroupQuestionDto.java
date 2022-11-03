package cu.edu.mes.sigenu.training.web.dto.questionnaire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupQuestionDto {
    private Integer id;
    private String nameGroup;
    private String description;
}
