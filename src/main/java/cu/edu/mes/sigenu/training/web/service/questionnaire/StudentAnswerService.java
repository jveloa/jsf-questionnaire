package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.StudentAnswerDto;

import java.util.List;

public interface StudentAnswerService {
    boolean saveStudentAnswer(List<StudentAnswerDto> studentAnswerDtos,Integer questionnaireId);
}
