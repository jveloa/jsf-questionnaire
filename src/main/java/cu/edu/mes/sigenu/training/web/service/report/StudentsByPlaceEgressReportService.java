package cu.edu.mes.sigenu.training.web.service.report;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.EntrySourceAuxDto;

import java.util.List;

public interface StudentsByPlaceEgressReportService {
    List<String> getStudentsByPlaceEgress (Integer year, String idPlaceEgress,Integer questionnarieId);

    List<EntrySourceAuxDto> getAllPlaceEgress ();

}
