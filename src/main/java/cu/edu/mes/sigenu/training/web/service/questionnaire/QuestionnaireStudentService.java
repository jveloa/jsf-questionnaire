package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireStudentDto;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;
import cu.edu.mes.subsystem.student.vo.StudentVO;

import java.util.List;

public interface QuestionnaireStudentService {

    List<QuestionnaireStudentDto> questionnaireByStudent(String sigenuId);

    StudentVO getCheckStudentByIdentification(String identification);
}
