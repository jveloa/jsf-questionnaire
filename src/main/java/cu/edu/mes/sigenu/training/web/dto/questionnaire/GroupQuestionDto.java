package cu.edu.mes.sigenu.training.web.dto.questionnaire;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupQuestionDto {
    private Integer id;
    private String nameGroup;
    private String description;
    private Integer organizationOrder;
}
