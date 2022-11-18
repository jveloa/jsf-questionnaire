package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.AnswerDto;
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
public class AnswerServiceImpl implements AnswerService{

    @Inject
    private RestService restService;

    @Override
    public List<AnswerDto> getAll() {
        List<AnswerDto> answers = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<AnswerDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/answer");
            String response = (String)restService.GET(template.toString(), params, String.class,
                                                      CurrentUserUtils.getTokenBearer()).getBody();

            answers = apiRestMapper.mapList(response,AnswerDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return answers;
    }

    @Override
    public AnswerDto getById(Integer id) {
        AnswerDto answer = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<AnswerDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/answer/{id}");
            String uri = template.expand(id).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            answer = apiRestMapper.mapOne(response,AnswerDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return answer;
    }

    @Override
    public ApiResponse save(AnswerDto answerDto) {
        ApiResponse apiResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/answer");
            String response = (String) restService.POST(template.toString(), answerDto, String.class,
                                                        CurrentUserUtils.getTokenBearer()).getBody();

            apiResponse = apiRestMapper.mapOne(response,ApiResponse.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  apiResponse;
    }

    @Override
    public ApiResponse update(AnswerDto answerDto) {
        ApiResponse apiResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/answer");
            String response = (String) restService.PUT(template.toString(),params,answerDto,String.class,
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
            UriTemplate template = new UriTemplate("/api/v1/training/answer/{id}");
            String uri = template.expand(id).toString();

            String response = (String) restService.DELETE(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            apiResponse = apiRestMapper.mapOne(response,ApiResponse.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  apiResponse;
    }
}
