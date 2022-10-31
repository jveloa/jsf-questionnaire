package cu.edu.mes.sigenu.training.web.dto.questionnaire.report;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentNotComputerDto {
    private  String name;
    private String studentSigenuId;
}
