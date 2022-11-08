package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireQuestionByGroupDto;
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
public class QuestionnaireQuestionServiceImpl implements QuestionnaireQuestionService {

    @Inject
    private RestService restService;

    @Override
    public List<QuestionnaireQuestionByGroupDto> getQuestionsByQuestionnaire(Integer questionnaireId) {
        List<QuestionnaireQuestionByGroupDto> items = new ArrayList<>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<QuestionnaireQuestionByGroupDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/questionnaire-question/{questionnaireId}/");
            String uri = template.expand(questionnaireId).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            items = apiRestMapper.mapList(response,QuestionnaireQuestionByGroupDto.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return items;
    }



}
