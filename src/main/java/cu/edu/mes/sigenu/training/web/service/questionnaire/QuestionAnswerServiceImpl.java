package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionAnswerDto;
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
import java.util.List;

@Named
@RequestScoped
public class QuestionAnswerServiceImpl implements  QuestionAnswerService{

    @Inject
    private RestService restService;

    @Override
    public List<QuestionAnswerDto> getByQuestionId(Integer questionId) {
        List<QuestionAnswerDto> answers = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<QuestionAnswerDto> apiRestMapper = new ApiRestMapper<>();
            UriTemplate template = new UriTemplate("/api/v1/training/question-answer/question-id/{id}");
            String uri = template.expand(questionId).toString();

            String response = (String)restService.GET(uri, params, String.class,
                                                      CurrentUserUtils.getTokenBearer()).getBody();

            answers = apiRestMapper.mapList(response,QuestionAnswerDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return answers;
    }

    @Override
    public ApiResponse save(QuestionAnswerDto questionAnswerDto) {
        ApiResponse apiResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/question-answer");
            String response = (String) restService.POST(template.toString(), questionAnswerDto, String.class,
                                                        CurrentUserUtils.getTokenBearer()).getBody();

            apiResponse = apiRestMapper.mapOne(response,ApiResponse.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return apiResponse;
    }

    @Override
    public ApiResponse deleteById(Integer id) {
        ApiResponse apiResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();
            UriTemplate template = new UriTemplate("/api/v1/training/question-answer/{id}");
            String uri = template.expand(id).toString();

            String response = (String) restService.DELETE(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            apiResponse = apiRestMapper.mapOne(response,ApiResponse.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  apiResponse;
    }
}
