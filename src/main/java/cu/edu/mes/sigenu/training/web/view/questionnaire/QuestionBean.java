package cu.edu.mes.sigenu.training.web.view.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.AnswerDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.GroupQuestionDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionWithCareerDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.GroupQuestionService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionCareerService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionService;
import cu.edu.mes.vo.CareerVO;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Data
public class QuestionBean implements Serializable {

    @Inject
    private QuestionService questionService;

    @Inject
    private QuestionCareerService questionCareerService;

    @Inject
    private GroupQuestionService groupQuestionService;


    private List<QuestionWithCareerDto> questionWithCareerDtos;

    private List<GroupQuestionDto> groupQuestionDtos;

    private List<CareerVO> careerList;

    private QuestionWithCareerDto selectedQuestion;

    private GroupQuestionDto selectedGroupQuestion;

    private CareerVO selectedCareer;


    public void openNew() {
        this.selectedQuestion = new QuestionWithCareerDto();
        selectedQuestion.setQuestion("");
        groupQuestionDtos = groupQuestionService.getAll();
        careerList = questionCareerService.getCareersSigenu();

    }

    @PostConstruct
    public void init() {
        openNew();
    }

    public void save(){

    }

    public void delete(){

    }

    public List<QuestionWithCareerDto> getQuestionWithCareerDtos() {
        return questionService.getQuestionWithCareer();
    }

    public String getGroupQuestionById(Integer Id){
        return groupQuestionDtos.stream().filter(group -> group.getId() == Id ).findFirst().get().getNameGroup();
    }

    public String getCarrerById(String IdCareer){
        return careerList.stream().filter(career -> career.getNationalCareerVO().getIdNationalCareer().equals(IdCareer)).findFirst().get().getNameNationalCareer();
    }


}
