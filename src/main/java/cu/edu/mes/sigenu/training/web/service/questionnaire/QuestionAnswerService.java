package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionAnswerDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionDto;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;

import java.util.List;


public interface QuestionAnswerService {
    List<QuestionAnswerDto> getByQuestionId(Integer questionId);
    ApiResponse save(QuestionAnswerDto questionAnswerDto);
    ApiResponse deleteById(Integer id);
}
