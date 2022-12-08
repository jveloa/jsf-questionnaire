package cu.edu.mes.sigenu.training.web.dto.questionnaire.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentArtDto {
    private String name;
    private String studentSigenuId;
    private List<String> arts;
}
