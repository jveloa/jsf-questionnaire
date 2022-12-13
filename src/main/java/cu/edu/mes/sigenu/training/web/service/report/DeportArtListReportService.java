package cu.edu.mes.sigenu.training.web.service.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.DeportArtListDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentArtDto;

import java.util.List;

public interface DeportArtListReportService {
    List<DeportArtListDto> getDeportArtListByStudent (Integer year, Integer questionnarieId);
}
