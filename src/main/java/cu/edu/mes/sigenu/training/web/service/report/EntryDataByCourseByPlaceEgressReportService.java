package cu.edu.mes.sigenu.training.web.service.report;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.EntrySourceAuxDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsWithNotesDto;

import java.util.List;

public interface EntryDataByCourseByPlaceEgressReportService {

    List<StudentsWithNotesDto> getEntryDataByCourseByPlaceEgress(Integer year, String idPlaceEgress, Integer questionnarieId);

    List<EntrySourceAuxDto> getAllPlaceEgress ();

}
