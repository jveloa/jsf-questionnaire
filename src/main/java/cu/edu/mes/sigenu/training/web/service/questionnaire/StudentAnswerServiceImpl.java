package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.StudentAnswerDto;
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
import java.util.List;

@Named
@RequestScoped
public class StudentAnswerServiceImpl implements StudentAnswerService {

    @Inject
    private RestService restService;


    @Override
    public boolean saveStudentAnswer(List<StudentAnswerDto> studentAnswerDtos,Integer questionnaireId) {
            ApiResponse apiResponse= new ApiResponse();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<ApiResponse> apiRestMapper = new ApiRestMapper<>();


            UriTemplate template = new UriTemplate("/api/v1/training/student-answer/questionnaire/{questionnaireId}/");
            String uri = template.expand(questionnaireId).toString();

            String response = (String) restService.POST(uri, studentAnswerDtos, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            apiResponse = apiRestMapper.mapOne(response,ApiResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiResponse.isSuccess() ;
    }
}
