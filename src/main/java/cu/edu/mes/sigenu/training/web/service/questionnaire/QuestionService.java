package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionWithCareerDto;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getAll();
    List<QuestionWithCareerDto> getQuestionWithCareer();
    List<QuestionDto> getQuestionWithOutCareer();
    QuestionDto getById(Integer id);
    ApiResponse save(QuestionDto questionDto);
    ApiResponse update(QuestionDto questionDto);
    ApiResponse delete(Integer id);
}
