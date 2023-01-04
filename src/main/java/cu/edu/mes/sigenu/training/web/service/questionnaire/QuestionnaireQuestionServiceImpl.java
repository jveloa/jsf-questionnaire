package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireQuestionByGroupDto;
import cu.edu.mes.sigenu.training.web.security.CurrentUserUtils;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;
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

    @Override
    public List<QuestionDto> getQuestions(Integer questionnaireId) {
        List<QuestionDto> items = new ArrayList<>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<QuestionDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/questionnaire-question/questionnaire/{questionnaireId}/");
            String uri = template.expand(questionnaireId).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            items = apiRestMapper.mapList(response,QuestionDto.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public ApiResponse save(Integer questionnaireId, Integer questionId) {
        ApiResponse apiResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/questionnaire-question/{questionnaireId}/{questionId}");
            String uri = template.expand(questionnaireId,questionId).toString();

            String response = (String)restService.POST(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            apiResponse = apiRestMapper.mapOne(response,ApiResponse.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return apiResponse;
    }

    @Override
    public ApiResponse delete(Integer questionnaireId, Integer questionId) {
        ApiResponse apiResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/questionnaire-question/{questionnaireId}/{questionId}");
            String uri = template.expand(questionnaireId,questionId).toString();

            String response = (String)restService.DELETE(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            apiResponse = apiRestMapper.mapOne(response,ApiResponse.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return apiResponse;
    }


}
