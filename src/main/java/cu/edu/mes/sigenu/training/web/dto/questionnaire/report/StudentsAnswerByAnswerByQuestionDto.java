package cu.edu.mes.sigenu.training.web.dto.questionnaire.report;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentsAnswerByAnswerByQuestionDto {

    private String nameQuestion;
    private Float percentMuch;
    private Float percentLittle;
    private Float percentNot;
    private Float percentNever;


}
