package cu.edu.mes.sigenu.training.web.view.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.GroupQuestionDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.GroupQuestionService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionService;
import lombok.Data;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Data
public class QuestionWithOutCareerBean implements Serializable {
    @Inject
    private QuestionService questionService;

    @Inject
    private GroupQuestionService groupQuestionService;

    private List<GroupQuestionDto> groupQuestionDtos;

    private List<QuestionDto> questionDtos;

    public List<GroupQuestionDto> getGroupQuestionDtos() {
        return groupQuestionService.getAll();
    }

    public List<QuestionDto> getQuestionDtos() {
        return questionService.getQuestionWithOutCareer();
    }

    public String getGroupQuestionById(Integer Id){
        if(groupQuestionDtos == null){
            groupQuestionDtos = groupQuestionService.getAll();
        }
        return groupQuestionDtos.stream().filter(group -> group.getId() == Id ).findFirst().get().getNameGroup();
    }

}
