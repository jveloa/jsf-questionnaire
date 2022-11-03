package cu.edu.mes.sigenu.training.web.dto.questionnaire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAnswerDto {
    private Integer id;
    private String studentSigenuId;
    private QuestionAnswerDto questionAnswerId;

}
