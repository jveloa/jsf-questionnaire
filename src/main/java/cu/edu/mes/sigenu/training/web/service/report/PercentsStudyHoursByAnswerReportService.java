package cu.edu.mes.sigenu.training.web.service.report;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.PercentsStudyHoursByAnswerDto;


import java.util.List;

public interface PercentsStudyHoursByAnswerReportService {

    List<PercentsStudyHoursByAnswerDto> getPercentsStudyHoursByAnswer (Integer year, Integer id);
}
