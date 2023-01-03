package cu.edu.mes.sigenu.training.web.dto.questionnaire.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CareerOptionsDto {
    private Integer quantityOptionOne;
    private Integer quantityOptionTwo;
    private Integer quantityOptionThree;
    private Integer quantityOptionPlusThree;

}
