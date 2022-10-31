package cu.edu.mes.sigenu.training.web.dto.questionnaire;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionnaireQuestionDto {
    private cu.edu.mes.sigenu.training.core.dto.QuestionDto question;
    private List<AnswerDto> answerList;
}
