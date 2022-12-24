package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireQuestionByGroupDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireStudentDto;
import cu.edu.mes.sigenu.training.web.security.CurrentUserUtils;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;
import cu.edu.mes.sigenu.training.web.utils.ApiRestMapper;
import cu.edu.mes.sigenu.training.web.utils.RestService;
import cu.edu.mes.subsystem.student.vo.StudentVO;
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
public class QuestionnaireStudentServiceImpl implements QuestionnaireStudentService{

    @Inject
    private RestService restService;

    @Override
    public List<QuestionnaireStudentDto> questionnaireByStudent(String sigenuId) {
        List<QuestionnaireStudentDto> items = new ArrayList<>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<QuestionnaireStudentDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/questionnaire-student/student/{sigenuId}/");
            String uri = template.expand(sigenuId).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            items = apiRestMapper.mapList(response,QuestionnaireStudentDto.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public StudentVO getCheckStudentByIdentification(String identification) {
        StudentVO studentVO = null;
        try{
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<StudentVO> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/questionnaire-student/student-sigenu/{identification}/");
            String uri = template.expand(identification).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            studentVO = apiRestMapper.mapOne(response,StudentVO.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return studentVO;
    }


}
