package cu.edu.mes.sigenu.training.web.service.report;



import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsWithNotesDto;

import java.util.List;

public interface EntryDataByCourseReportService {

    List<StudentsWithNotesDto> getEntryDataByCourse (Integer year, Integer id);
}
