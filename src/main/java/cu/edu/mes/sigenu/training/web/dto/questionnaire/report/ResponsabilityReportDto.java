package cu.edu.mes.sigenu.training.web.dto.questionnaire.report;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ResponsabilityReportDto {
    private String name;
    private String studentSigenuId;
    private String questionInterest;
    private String answerInterest;
    private String questionExp;
    private String answerExp;
    private String questionOrg;
    private String answerOrg;
}
