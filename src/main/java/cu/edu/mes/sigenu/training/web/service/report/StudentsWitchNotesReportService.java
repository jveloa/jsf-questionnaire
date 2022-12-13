package cu.edu.mes.sigenu.training.web.service.report;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsNotesDto;

import java.util.List;

public interface StudentsWitchNotesReportService {

    List<StudentsNotesDto> getStudentsWitchNotes (Integer year, Integer questionnarieId);
}
