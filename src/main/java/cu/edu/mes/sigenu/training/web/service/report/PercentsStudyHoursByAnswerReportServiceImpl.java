package cu.edu.mes.sigenu.training.web.service.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.PercentsStudyHoursByAnswerDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentNotComputerDto;
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
public class PercentsStudyHoursByAnswerReportServiceImpl implements PercentsStudyHoursByAnswerReportService {

    @Inject
    private RestService restService;


    @Override
    public List<PercentsStudyHoursByAnswerDto> getPercentsStudyHoursByAnswer(Integer year, Integer questionnarieId) {

        List<PercentsStudyHoursByAnswerDto> items = new ArrayList<PercentsStudyHoursByAnswerDto>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<PercentsStudyHoursByAnswerDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/reportCareerChief/percentsStudyHoursByAnswer/{year}/{questionnarieId}");
            String uri = template.expand(year, questionnarieId).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            items = apiRestMapper.mapList(response, PercentsStudyHoursByAnswerDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
