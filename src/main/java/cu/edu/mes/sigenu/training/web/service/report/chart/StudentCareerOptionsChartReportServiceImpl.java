package cu.edu.mes.sigenu.training.web.service.report.chart;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.CareerOptionsDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsNotesDto;
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
public class StudentCareerOptionsChartReportServiceImpl implements StudentCareerOptionsChartReportService {

    @Inject
    private RestService restService;


    @Override
    public List<String> getAllYears(Integer questionnarieId) {

        List<String> items = new ArrayList<String>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<String> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/reportCareerChief/allYear/{questionnarieId}");

            String uri = template.expand(questionnarieId).toString();

            String response = (String) restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            items = apiRestMapper.mapList(response, String.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;

    }

    @Override
    public CareerOptionsDto studentCareerOptions(Integer year, Integer questionnarieId) {

        CareerOptionsDto items = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<CareerOptionsDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/reportCareerChief/studentCareerOptions/{year}/{questionnarieId}");
            String uri = template.expand(year, questionnarieId).toString();

            String response = (String) restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            items = apiRestMapper.mapOne(response, CareerOptionsDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

}
