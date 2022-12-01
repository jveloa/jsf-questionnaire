package cu.edu.mes.sigenu.training.web.service.report;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsNotesDto;

import java.util.List;

public interface StudentsByConfigurableNotesReportService {

    List<StudentsNotesDto> getStudentsByConfigurableNotes (Integer year, float academicIndex
            , float noteSpanish, float noteMat, float noteHistory,Integer id);
}
