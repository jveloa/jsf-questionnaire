package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionWithCareerDto;
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
public class QuestionServiceImpl implements QuestionService {

    @Inject
    private RestService restService;

    @Override
    public List<QuestionDto> getAll() {
        List<QuestionDto> questions = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<QuestionDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/question");
            String response = (String)restService.GET(template.toString(), params, String.class,
                                                      CurrentUserUtils.getTokenBearer()).getBody();

            questions = apiRestMapper.mapList(response,QuestionDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public List<QuestionWithCareerDto> getQuestionWithCareer() {
        List<QuestionWithCareerDto> questions = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<QuestionWithCareerDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/question/questionWithCareer");
            String response = (String)restService.GET(template.toString(), params, String.class,
                                                      CurrentUserUtils.getTokenBearer()).getBody();

            questions = apiRestMapper.mapList(response,QuestionWithCareerDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public List<QuestionDto> getQuestionWithOutCareer() {
        List<QuestionDto> questions = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<QuestionDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/question/questionWithoutCareer");
            String response = (String)restService.GET(template.toString(), params, String.class,
                                                      CurrentUserUtils.getTokenBearer()).getBody();

            questions = apiRestMapper.mapList(response,QuestionDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public QuestionDto getById(Integer id) {
        QuestionDto question = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<QuestionDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/question/{id}");
            String uri = template.expand(id).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            question = apiRestMapper.mapOne(response,QuestionDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return question;
    }

    @Override
    public ApiResponse save(QuestionDto questionDto) {
        ApiResponse apiResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/question");
            String response = (String) restService.POST(template.toString(), questionDto, String.class,
                                                        CurrentUserUtils.getTokenBearer()).getBody();

            apiResponse = apiRestMapper.mapOne(response,ApiResponse.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return apiResponse;
    }

    @Override
    public ApiResponse update(QuestionDto questionDto) {
        ApiResponse apiResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/question");
            String response = (String) restService.PUT(template.toString(),params,questionDto,String.class,
                                                       CurrentUserUtils.getTokenBearer()).getBody();

            apiResponse = apiRestMapper.mapOne(response,ApiResponse.class);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  apiResponse;
    }

    @Override
    public ApiResponse delete(Integer id) {
        ApiResponse apiResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();
            UriTemplate template = new UriTemplate("/api/v1/training/question/{id}");
            String uri = template.expand(id).toString();

            String response = (String) restService.DELETE(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            apiResponse = apiRestMapper.mapOne(response,ApiResponse.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  apiResponse;
    }
}
