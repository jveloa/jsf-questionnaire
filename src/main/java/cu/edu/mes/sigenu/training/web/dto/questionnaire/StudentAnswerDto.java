package cu.edu.mes.sigenu.training.web.dto.questionnaire;

import lombok.Data;

@Data
public class StudentAnswerDto {
    private Integer id;
    private String studentSigenuId;
    private QuestionAnswerDto questionAnswerId;

}
