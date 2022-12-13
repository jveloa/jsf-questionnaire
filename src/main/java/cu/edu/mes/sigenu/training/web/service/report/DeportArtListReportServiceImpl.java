package cu.edu.mes.sigenu.training.web.service.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.DeportArtListDto;
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
public class DeportArtListReportServiceImpl implements DeportArtListReportService {

    @Inject
    private RestService restService;


    @Override
    public List<DeportArtListDto> getDeportArtListByStudent(Integer year, Integer questionnarieId) {
        List<DeportArtListDto> items = new ArrayList<DeportArtListDto>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<DeportArtListDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/reportFeu/deportArtListByStudent/{year}/{questionnarieId}");
            String uri = template.expand(year, questionnarieId).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            items = apiRestMapper.mapList(response, DeportArtListDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
