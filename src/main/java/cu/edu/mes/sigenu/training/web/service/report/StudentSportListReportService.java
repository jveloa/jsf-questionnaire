package cu.edu.mes.sigenu.training.web.service.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentSportDto;

import java.util.List;

public interface StudentSportListReportService {
    List<StudentSportDto> getStudentSportList (Integer year, Integer questionnarieId);
}
