package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.GroupQuestionDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.security.CurrentUserUtils;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;
import cu.edu.mes.sigenu.training.web.utils.ApiRestMapper;
import cu.edu.mes.sigenu.training.web.utils.RestService;
import cu.edu.mes.vo.CareerVO;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Named
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Inject
    private RestService restService;

    @Override
    public List<QuestionnaireDto> getAllQuestionnaire() {
        List<QuestionnaireDto> listQuestionnaire = new ArrayList<>();

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<QuestionnaireDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/questionnaire/");
            String response = (String)restService.GET(template.toString(), params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            listQuestionnaire = apiRestMapper.mapList(response,QuestionnaireDto.class);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return listQuestionnaire;
    }

    @Override
    public ApiResponse save(QuestionnaireDto questionnaireDto) {
        ApiResponse apiResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/questionnaire");
            String response = (String) restService.POST(template.toString(), questionnaireDto, String.class,
                                                        CurrentUserUtils.getTokenBearer()).getBody();

            apiResponse = apiRestMapper.mapOne(response,ApiResponse.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  apiResponse;
    }

    @Override
    public ApiResponse update(QuestionnaireDto questionnaireDto) {
        ApiResponse apiResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/questionnaire");
            String response = (String) restService.PUT(template.toString(),params,questionnaireDto,String.class,
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
            UriTemplate template = new UriTemplate("/api/v1/training/questionnaire/{id}");
            String uri = template.expand(id).toString();

            String response = (String) restService.DELETE(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            apiResponse = apiRestMapper.mapOne(response,ApiResponse.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  apiResponse;
    }


}
