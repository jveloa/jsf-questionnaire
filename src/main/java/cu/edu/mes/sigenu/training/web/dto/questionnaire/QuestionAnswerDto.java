package cu.edu.mes.sigenu.training.web.dto.questionnaire;

import lombok.Data;

@Data
public class QuestionAnswerDto {
    private Integer id;
    private Integer questionId;
    private Integer answerId;
}
