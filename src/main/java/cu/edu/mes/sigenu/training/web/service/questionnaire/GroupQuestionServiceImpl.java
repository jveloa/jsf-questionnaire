package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.GroupQuestionDto;
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
public class GroupQuestionServiceImpl implements GroupQuestionService {

    @Inject
    private RestService restService;

    @Override
    public List<GroupQuestionDto> getAll() {
        List<GroupQuestionDto> groupQuestions = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<GroupQuestionDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/group-question");
            String response = (String)restService.GET(template.toString(), params, String.class,
                                                      CurrentUserUtils.getTokenBearer()).getBody();

            groupQuestions = apiRestMapper.mapList(response,GroupQuestionDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return groupQuestions;
    }

    @Override
    public GroupQuestionDto getById(Integer id) {
        GroupQuestionDto groupQuestion = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<GroupQuestionDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/group-question/{id}");
            String uri = template.expand(id).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            groupQuestion = apiRestMapper.mapOne(response,GroupQuestionDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return groupQuestion;
    }

    @Override
    public ApiResponse save(GroupQuestionDto groupQuestionDto) {
        ApiResponse apiResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/group-question");
            String response = (String) restService.POST(template.toString(), groupQuestionDto, String.class,
                                                        CurrentUserUtils.getTokenBearer()).getBody();

            apiResponse = apiRestMapper.mapOne(response,ApiResponse.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  apiResponse;
    }

    @Override
    public ApiResponse update(GroupQuestionDto groupQuestionDto) {
        ApiResponse apiResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/group-question");
            String response = (String) restService.PATCH(template.toString(),params,groupQuestionDto,String.class,
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
            UriTemplate template = new UriTemplate("/api/v1/training/group-question/{id}");
            String uri = template.expand(id).toString();

            String response = (String) restService.DELETE(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  apiResponse;
    }
}
