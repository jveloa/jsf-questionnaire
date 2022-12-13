package cu.edu.mes.sigenu.training.web.service.report;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.EntrySourceAuxDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentNotComputerDto;

import java.util.List;

public interface StudentsByEntrySourceReportService {
    List<String> getStudentsByEntrySource (Integer year, String entrySource,Integer questionnarieId);

    List<EntrySourceAuxDto> getAllEntrySource ();

}
