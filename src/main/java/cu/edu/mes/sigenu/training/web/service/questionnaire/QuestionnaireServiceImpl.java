package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.security.CurrentUserUtils;
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
}
