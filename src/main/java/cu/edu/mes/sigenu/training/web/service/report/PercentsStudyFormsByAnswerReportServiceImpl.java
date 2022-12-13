package cu.edu.mes.sigenu.training.web.service.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.PercentsStudyHoursByAnswerDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsAnswerByAnswerByQuestionDto;
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
public class PercentsStudyFormsByAnswerReportServiceImpl implements PercentsStudyFormsByAnswerReportService {

    @Inject
    private RestService restService;


    @Override
    public List<StudentsAnswerByAnswerByQuestionDto> getPercentsStudyFormsByAnswer(Integer year, Integer questionnarieId) {

        List<StudentsAnswerByAnswerByQuestionDto> items = new ArrayList<StudentsAnswerByAnswerByQuestionDto>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<StudentsAnswerByAnswerByQuestionDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/reportCareerChief/percentsStudyFomrsByAnswer/{year}/{questionnarieId}");
            String uri = template.expand(year, questionnarieId).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            items = apiRestMapper.mapList(response, StudentsAnswerByAnswerByQuestionDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
