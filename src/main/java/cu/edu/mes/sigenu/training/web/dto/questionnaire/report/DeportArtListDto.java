package cu.edu.mes.sigenu.training.web.dto.questionnaire.report;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DeportArtListDto {
    private List<String> sports;
    private List<String> arts;

}
