package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.GroupQuestionDto;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;

import java.util.List;

public interface GroupQuestionService {
    List<GroupQuestionDto> getAll();
    GroupQuestionDto getById(Integer id);
    ApiResponse save(GroupQuestionDto groupQuestionDto);
    ApiResponse update(GroupQuestionDto groupQuestionDto);
    ApiResponse delete(Integer id);
}
