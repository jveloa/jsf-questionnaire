package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireQuestionByGroupDto;

import java.util.List;

public interface QuestionnaireQuestionService {

    List<QuestionnaireQuestionByGroupDto> getQuestionsByQuestionnaire(Integer questionnaireId);
}
