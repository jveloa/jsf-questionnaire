package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireQuestionByGroupDto;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;

import java.util.List;

public interface QuestionnaireQuestionService {

    List<QuestionnaireQuestionByGroupDto> getQuestionsByQuestionnaire(Integer questionnaireId);
    List<QuestionDto> getQuestions(Integer questionnaireId);
    ApiResponse save(Integer questionnaireId,Integer questionId);
    ApiResponse delete(Integer questionnaireId, Integer questionId);
}
