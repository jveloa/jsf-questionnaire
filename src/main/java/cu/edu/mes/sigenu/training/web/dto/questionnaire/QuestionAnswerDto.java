package cu.edu.mes.sigenu.training.web.dto.questionnaire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerDto {
    private Integer id;
    private Integer questionId;
    private Integer answerId;
}
