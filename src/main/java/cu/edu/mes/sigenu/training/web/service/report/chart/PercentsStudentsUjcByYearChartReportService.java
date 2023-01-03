package cu.edu.mes.sigenu.training.web.service.report.chart;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.CareerOptionsDto;

import java.util.List;

public interface PercentsStudentsUjcByYearChartReportService {

    List<String> getAllYears (Integer questionnarieId);

    Double percentsStudentsUjcByYear(Integer year, Integer questionnarieId);

}
