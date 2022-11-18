package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.AnswerDto;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;

import java.util.List;

public interface AnswerService {
    List<AnswerDto> getAll();
    AnswerDto getById(Integer id);
    ApiResponse save(AnswerDto answerDto);
    ApiResponse update(AnswerDto answerDto);
    ApiResponse delete(Integer id);
}
