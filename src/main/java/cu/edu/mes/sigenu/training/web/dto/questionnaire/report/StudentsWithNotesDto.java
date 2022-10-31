package cu.edu.mes.sigenu.training.web.dto.questionnaire.report;

import lombok.Builder;
import lombok.Data;

import java.util.Map;


@Builder
@Data
public class StudentsWithNotesDto {
    private String name;
    private  Map<String,Float> notesList;
}
