package cu.edu.mes.sigenu.training.web.view.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionCareerService;
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
import java.util.List;

@Named
@ViewScoped
@Data
public class QuestionnaireBean implements Serializable {

    @Inject
    private QuestionnaireService questionnaireService;

    @Inject
    private QuestionCareerService questionCareerService;


    private QuestionnaireDto selectedQuestionnaire;

    private List<QuestionnaireDto> questionnaireDtos;

    private List<NationalCareerVO> careerList;

    private NationalCareerVO selectedCareer;


    public List<QuestionnaireDto> getQuestionnaireDtos() {
        return questionnaireService.getAllQuestionnaire();
    }

    public void openNew(){
        selectedQuestionnaire = new QuestionnaireDto();
        selectedQuestionnaire.setName("");
    }

    @PostConstruct
    public void init() {
        openNew();
    }

    public void save(){
        if (!(selectedQuestionnaire.getName().length() > 2)){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_group_invalid");
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


}
