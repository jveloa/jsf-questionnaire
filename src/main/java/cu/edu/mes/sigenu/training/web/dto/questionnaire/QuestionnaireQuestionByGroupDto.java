package cu.edu.mes.sigenu.training.web.dto.questionnaire;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireQuestionByGroupDto {
    private GroupQuestionDto groupQuestionDto;
    private List<QuestionnaireQuestionDto> questionnaireQuestionDto;
}
