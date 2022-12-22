package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireQuestionByGroupDto;
import cu.edu.mes.sigenu.training.web.security.CurrentUserUtils;
import cu.edu.mes.sigenu.training.web.utils.ApiRestMapper;
import cu.edu.mes.sigenu.training.web.utils.RestService;
import cu.edu.mes.vo.CareerVO;
import cu.edu.mes.vo.NationalCareerVO;
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
public class QuestionCarrerServiceImpl implements QuestionCareerService {

    @Inject
    private RestService restService;

    @Override
    public List<NationalCareerVO> getCareersSigenu() {
        List<NationalCareerVO> listCareerSigenu = new ArrayList<>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<NationalCareerVO> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/question-carrer/career");
            String response = (String)restService.GET(template.toString(), params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            listCareerSigenu = apiRestMapper.mapList(response,NationalCareerVO.class);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return listCareerSigenu;
    }
}
