package cu.edu.mes.sigenu.training.web.dto.questionnaire.report;


import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class StudentsAnswerByAnswerByQuestionDto {

    private String nameQuestion;
    private Map<String,Float> answerList;


}
