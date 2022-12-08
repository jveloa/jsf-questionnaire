package cu.edu.mes.sigenu.training.web.service.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentArtDto;

import java.util.List;

public interface ArtListReportService {
    List<StudentArtDto> getArtList (Integer year, Integer questionnarieId);
}
