package cu.edu.mes.sigenu.training.web.service.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.ResponsabilityReportDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentArtDto;
import cu.edu.mes.sigenu.training.web.security.CurrentUserUtils;
import cu.edu.mes.sigenu.training.web.utils.ApiRestMapper;
import cu.edu.mes.sigenu.training.web.utils.RestService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class StudentsResponsibilityReportServiceImpl implements StudentsResponsibilityReportService {

    @Inject
    private RestService restService;


    @Override
    public List<ResponsabilityReportDto> getStudentsResponsibilityList(Integer year, Integer questionnarieId) {
        List<ResponsabilityReportDto> items = new ArrayList<ResponsabilityReportDto>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ResponsabilityReportDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/reportFeu/responsabilityReport/{year}/{questionnarieId}");
            String uri = template.expand(year, questionnarieId).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            items = apiRestMapper.mapList(response, ResponsabilityReportDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
