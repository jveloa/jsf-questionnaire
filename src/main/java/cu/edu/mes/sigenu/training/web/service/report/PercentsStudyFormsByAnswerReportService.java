package cu.edu.mes.sigenu.training.web.service.report;



import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsAnswerByAnswerByQuestionDto;

import java.util.List;

public interface PercentsStudyFormsByAnswerReportService {

    List<StudentsAnswerByAnswerByQuestionDto> getPercentsStudyFormsByAnswer (Integer year, Integer id);
}
