package cu.edu.mes.sigenu.training.web.dto.questionnaire.report;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentNotComputerDto {
    private  String name;
    private String studentSigenuId;
}
