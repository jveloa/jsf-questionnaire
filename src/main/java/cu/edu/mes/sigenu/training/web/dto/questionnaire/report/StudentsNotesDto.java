package cu.edu.mes.sigenu.training.web.dto.questionnaire.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentsNotesDto {
    private String name;
    private  Float  noteAve;
    private  Float  noteMat;
    private  Float  noteHistory;
    private  Float  noteSpanish;
}
