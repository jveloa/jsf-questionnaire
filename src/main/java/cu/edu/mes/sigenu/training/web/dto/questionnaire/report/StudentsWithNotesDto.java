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
public class StudentsWithNotesDto {
    private String name;
    private  Float  numberAve;
    private  Float  numberMax;
    private  Float  numberMin;
}
