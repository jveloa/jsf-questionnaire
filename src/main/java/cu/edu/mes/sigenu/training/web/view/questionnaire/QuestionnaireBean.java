package cu.edu.mes.sigenu.training.web.view.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.AnswerDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionWithCareerDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionCareerService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireQuestionService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
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
public class QuestionnaireBean implements Serializable {

    @Inject
    private QuestionnaireService questionnaireService;

    @Inject
    private QuestionCareerService questionCareerService;

    @Inject
    private QuestionService questionService;

    @Inject
    private QuestionnaireQuestionService questionnaireQuestionService;


    private QuestionnaireDto selectedQuestionnaire;

    private List<QuestionnaireDto> questionnaireDtos;

    private List<NationalCareerVO> careerList;

    private NationalCareerVO selectedCareer;

    private List<QuestionWithCareerDto> questionDtos;

    private List<QuestionWithCareerDto> selectedQuestionDtos;


    public List<QuestionnaireDto> getQuestionnaireDtos() {
        return questionnaireService.getAllQuestionnaire();
    }

    public void openNew(){
        selectedQuestionnaire = new QuestionnaireDto();
        selectedQuestionnaire.setName("");
    }

    public void openForAdd(){
        List<QuestionWithCareerDto> questionWithCareerDtos = getQuestionWithOneCareer(selectedQuestionnaire.getCareerSigenuId());
        List<QuestionDto> questionByQuestionnaire = questionnaireQuestionService.getQuestions(selectedQuestionnaire.getId());
        selectedQuestionDtos = new ArrayList<>();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        questionDtos = questionWithCareerDtos
            .stream()
            .filter(questionWithCareerDto -> questionByQuestionnaire
                            .stream()
                            .allMatch(questionDto -> !questionDto.getId().equals(questionWithCareerDto.getId())))
            .collect(Collectors.toList());

    }
    public void openForDelete(){
        List<QuestionWithCareerDto> questionWithCareerDtos = getQuestionWithOneCareer(selectedQuestionnaire.getCareerSigenuId());
        List<QuestionDto> questionByQuestionnaire = questionnaireQuestionService.getQuestions(selectedQuestionnaire.getId());
        selectedQuestionDtos = new ArrayList<>();
        questionDtos = questionWithCareerDtos
            .stream()
            .filter(questionWithCareerDto -> questionByQuestionnaire
                .stream()
                .anyMatch(questionDto -> questionDto.getId().equals(questionWithCareerDto.getId())))
            .collect(Collectors.toList());
    }

    @PostConstruct
    public void init() {
        selectedQuestionnaire = new QuestionnaireDto();
        selectedQuestionnaire.setName("");
    }

    public void save(){
        if (!(selectedQuestionnaire.getName().length() > 2)){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_questionnarie_invalid");
            return;
        }

        if(this.selectedQuestionnaire.getId() == null){
            ApiResponse response = questionnaireService.save(selectedQuestionnaire);
            if (response.isSuccess())
                JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_added");
            else
                JsfUtils.addMessageFromBundle(null,FacesMessage.SEVERITY_WARN,"message_questionnarie_exist");
        }else{
            questionnaireService.update(selectedQuestionnaire);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_edited");
        }
        questionnaireDtos = questionnaireService.getAllQuestionnaire();
        PrimeFaces.current().executeScript("PF('questionnaire').hide()");
    }

    public void addQuestionnaireQuestion(){
        if(selectedQuestionDtos.size() == 0){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_questionnaire_question_add_invalid");
            return;
        }
        selectedQuestionDtos.forEach(
            questionWithCareerDto -> questionnaireQuestionService.save(selectedQuestionnaire.getId(),questionWithCareerDto.getId()));
        JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_associate");
        PrimeFaces.current().executeScript("PF('questionnaireAddQuestion').hide()");
    }

    public void deleteQuestionnaireQuestion(){
        if(selectedQuestionDtos.size() == 0){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_questionnaire_question_add_invalid");
            return;
        }
        selectedQuestionDtos.forEach(
            questionWithCareerDto -> questionnaireQuestionService.delete(selectedQuestionnaire.getId(),questionWithCareerDto.getId()));
        JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_delete_associate");
        PrimeFaces.current().executeScript("PF('questionnaireDeleteQuestion').hide()");
    }


    public void delete(){
        ApiResponse response = questionnaireService.delete(this.selectedQuestionnaire.getId());
        if(response.isSuccess()){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_deleted");
            questionnaireDtos = questionnaireService.getAllQuestionnaire();
        }else{
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_not_deleted");
        }
    }

    public String getCarrerById(String IdCareer){
        if(careerList == null){
            careerList = questionCareerService.getCareersSigenu();
        }
        return careerList.stream().filter(career -> career.getIdNationalCareer().equals(IdCareer)).findFirst().get().getName();
    }

    public List<QuestionWithCareerDto> getQuestionWithOneCareer(String career){
        return questionService.getQuestionWithCareer()
                              .stream().filter(questionnaireDto -> questionnaireDto.getQuestionCarrerId().equals(career))
                              .collect(Collectors.toList());
    }
}
