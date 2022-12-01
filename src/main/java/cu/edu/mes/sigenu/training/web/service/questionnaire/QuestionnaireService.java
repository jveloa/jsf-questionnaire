package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;
import org.omg.CORBA.INTERNAL;

import java.util.List;

public interface QuestionnaireService {
    List<QuestionnaireDto> getAllQuestionnaire();
    ApiResponse save(QuestionnaireDto questionnaireDto);
    ApiResponse update(QuestionnaireDto questionnaireDto);
    ApiResponse delete(Integer id);
}
