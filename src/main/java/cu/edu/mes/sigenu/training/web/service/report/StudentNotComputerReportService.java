package cu.edu.mes.sigenu.training.web.service.report;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentNotComputerDto;

import java.util.List;

public interface StudentNotComputerReportService {
    List<StudentNotComputerDto> getStudentsNotComputer (Integer year, Integer questionnarieId);
}
