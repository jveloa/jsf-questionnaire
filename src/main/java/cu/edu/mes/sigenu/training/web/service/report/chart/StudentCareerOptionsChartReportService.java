package cu.edu.mes.sigenu.training.web.service.report.chart;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.CareerOptionsDto;

import java.util.List;

public interface StudentCareerOptionsChartReportService {

    List<String> getAllYears (Integer questionnarieId);

    CareerOptionsDto studentCareerOptions(Integer year, Integer questionnarieId);

}
