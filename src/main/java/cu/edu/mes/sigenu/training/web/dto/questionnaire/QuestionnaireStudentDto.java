package cu.edu.mes.sigenu.training.web.dto.questionnaire;

import lombok.Data;

import java.util.Date;

@Data
public class QuestionnaireStudentDto {

    private String studentSigenuId;
    private Date doneDate;
    private Integer id;
    private QuestionnaireDto questionnarieId;
}
