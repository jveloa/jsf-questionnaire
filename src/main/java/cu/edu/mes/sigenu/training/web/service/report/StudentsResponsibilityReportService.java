package cu.edu.mes.sigenu.training.web.service.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.ResponsabilityReportDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentArtDto;

import java.util.List;

public interface StudentsResponsibilityReportService {
    List<ResponsabilityReportDto> getStudentsResponsibilityList (Integer year, Integer questionnarieId);
}
