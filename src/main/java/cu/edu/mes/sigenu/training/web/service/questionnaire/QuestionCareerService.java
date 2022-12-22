package cu.edu.mes.sigenu.training.web.service.questionnaire;

import cu.edu.mes.vo.CareerVO;
import cu.edu.mes.vo.NationalCareerVO;

import java.util.List;

public interface QuestionCareerService {
    List<NationalCareerVO> getCareersSigenu();
}
