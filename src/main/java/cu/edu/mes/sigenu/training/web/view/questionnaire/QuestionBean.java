package cu.edu.mes.sigenu.training.web.view.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.*;
import cu.edu.mes.sigenu.training.web.service.questionnaire.*;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
import cu.edu.mes.vo.NationalCareerVO;
import lombok.Data;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Inject
    private AnswerService answerService;

    @Inject
    private QuestionAnswerService questionAnswerService;


    private List<QuestionWithCareerDto> questionWithCareerDtos;

    private List<GroupQuestionDto> groupQuestionDtos;

    private List<NationalCareerVO> careerList;

    private QuestionWithCareerDto selectedQuestion;

    private List<AnswerDto> answerDtos;

    private List<AnswerDto> selectedAnswerDtos;

    private List<AnswerDto> answerDtoforActions;

    private List<QuestionAnswerDto> questionAnswerDtos;


    public void openNew() {
        this.selectedQuestion = new QuestionWithCareerDto();
        selectedQuestion.setQuestion("");
        selectedAnswerDtos = new ArrayList<>();
        answerDtos = answerService.getAll();
    }

    public void openForAdd(){
        selectedAnswerDtos = new ArrayList<>();
        answerDtos = answerService.getAll();
        questionAnswerDtos = questionAnswerService.getByQuestionId(selectedQuestion.getId());
        answerDtoforActions = answerDtos.stream().filter(answerDto ->
                                              questionAnswerDtos.stream().allMatch(
                                                  questionAnswerDto -> questionAnswerDto.getAnswerId()!=answerDto.getId()))
                                        .collect(Collectors.toList());
    }

    public void openForDelete(){
        selectedAnswerDtos = new ArrayList<>();
        answerDtos = answerService.getAll();
        questionAnswerDtos = questionAnswerService.getByQuestionId(selectedQuestion.getId());
        answerDtoforActions = answerDtos.stream().filter(answerDto ->
                                                             questionAnswerDtos.stream().anyMatch(
                                                                 questionAnswerDto -> questionAnswerDto.getAnswerId()==answerDto.getId()))
                                        .collect(Collectors.toList());
    }

    @PostConstruct
    public void init() {
        this.selectedQuestion = new QuestionWithCareerDto();
        selectedQuestion.setQuestion("");
    }

    public void save(){
        if (selectedQuestion.getQuestion().length() < 2){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_question_invalid");
            return;
        }
        if (selectedQuestion.getId()== null && selectedAnswerDtos.size() < 2){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_question_invalid_asnwer");
            return;
        }

        if(selectedQuestion.getId() == null){
            ApiResponse apiResponse = questionService.saveWithCareer(selectedQuestion);
            Integer questionId = questionService.getByName(selectedQuestion.getQuestion()).getId();
            selectedAnswerDtos.forEach(answerDto -> questionAnswerService.save(new QuestionAnswerDto(0,questionId,answerDto.getId(),null)));
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_added");
        }else{
            QuestionDto questionDto = new QuestionDto(selectedQuestion.getId(),selectedQuestion.getGroupQuestionId(),selectedQuestion.getQuestion(),false);
            ApiResponse apiResponse = questionService.update(questionDto);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_edited");
        }
        questionWithCareerDtos =  questionService.getQuestionWithCareer();
        PrimeFaces.current().executeScript("PF('question').hide()");
    }

    public void addQuestionAnswer(){
        if(selectedAnswerDtos.size() == 0){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_question_answer_add_invalid");
            return;
        }
        selectedAnswerDtos.forEach(answerDto -> questionAnswerService.save(new QuestionAnswerDto(0,selectedQuestion.getId(),answerDto.getId(),null)));
        JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_associate");
        PrimeFaces.current().executeScript("PF('questionAddAnswer').hide()");
    }

    public void deleteQuestionAnswer(){
        if(selectedAnswerDtos.size() == 0){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_question_answer_add_invalid");
            return;
        }
        if(answerDtoforActions.size() - selectedAnswerDtos.size() < 2){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_question_invalid_asnwer");
            return;
        }
        for (AnswerDto answerDto:selectedAnswerDtos) {
            for(QuestionAnswerDto questionAnswerDto: questionAnswerDtos){
                if(answerDto.getId() == questionAnswerDto.getAnswerId() && selectedQuestion.getId().equals(questionAnswerDto.getQuestionId())){
                    QuestionAnswerDto questionAnswerDto1 = questionAnswerDto;
                    questionAnswerService.deleteById(questionAnswerDto1.getId());
                }
            }
        }
        JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_delete_associate");
        PrimeFaces.current().executeScript("PF('questionDeleteAnswer').hide()");
    }

    public void delete(){
        ApiResponse response = questionService.delete(this.selectedQuestion.getId());
        if(response.isSuccess()){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_deleted");
            questionWithCareerDtos =  questionService.getQuestionWithCareer();
        }else{
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_not_deleted");
        }
    }

    public List<QuestionWithCareerDto> getQuestionWithCareerDtos() {
        return questionService.getQuestionWithCareer();
    }


    public String getGroupQuestionById(Integer Id){
        if(groupQuestionDtos == null){
            groupQuestionDtos = groupQuestionService.getAll();
        }
        return groupQuestionDtos.stream().filter(group -> group.getId() == Id ).findFirst().get().getNameGroup();
    }

    public String getCarrerById(String IdCareer){
        if (careerList == null){
            careerList = questionCareerService.getCareersSigenu();
        }
        return careerList.stream().filter(career -> career.getIdNationalCareer().equals(IdCareer)).findFirst().get().getName();
    }
}
