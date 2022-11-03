package cu.edu.mes.sigenu.training.web.dto.questionnaire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireStudentDto {

    private String studentSigenuId;
    private String identification;
    private Date doneDate;
    private Integer id;
    private QuestionnaireDto questionnarieId;
}
