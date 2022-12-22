package cu.edu.mes.sigenu.training.web.view.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.AnswerDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.GroupQuestionDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionWithCareerDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.GroupQuestionService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionCareerService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionService;
import cu.edu.mes.vo.CareerVO;
import cu.edu.mes.vo.NationalCareerVO;
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

    private List<NationalCareerVO> careerList;

    private QuestionWithCareerDto selectedQuestion;



    public void openNew() {
        this.selectedQuestion = new QuestionWithCareerDto();
        selectedQuestion.setQuestion("");
        careerList = questionCareerService.getCareersSigenu();
        groupQuestionDtos = groupQuestionService.getAll();

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
        return careerList.stream().filter(career -> career.getIdNationalCareer().equals(IdCareer)).findFirst().get().getName();
    }


}
