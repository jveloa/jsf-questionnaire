package cu.edu.mes.sigenu.training.web.service.report.chart;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.PercentsStudyHoursByAnswerDto;

import java.util.List;

public interface PercentsStudyHoursByAnswerChartReportService {

    List<String> getAllYears (Integer questionnarieId);
}
