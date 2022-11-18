package cu.edu.mes.sigenu.training.web.view.questionnaire;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.AnswerDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.AnswerService;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
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
public class AnswerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private AnswerService answerService;

    private List<AnswerDto> answerDtos;

    private AnswerDto selectedAnswer;

    public void openNew() {
        this.selectedAnswer = new AnswerDto();
        selectedAnswer.setAnswer("");
    }

    @PostConstruct
    public void init() {
        openNew();
    }

    public void save(){
        if (!(selectedAnswer.getAnswer().length() > 2)){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_answer_invalid");
            return;
        }

        if(this.selectedAnswer.getId() == null){
            answerService.save(selectedAnswer);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_added");
        }else{
            answerService.update(selectedAnswer);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_edited");
        }
        answerDtos = answerService.getAll();
        PrimeFaces.current().executeScript("PF('answer').hide()");
    }

    public void delete(){
        ApiResponse response = answerService.delete(this.selectedAnswer.getId());
        if(response.isSuccess()){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_deleted");
            answerDtos = answerService.getAll();
        }else{
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_not_deleted");
        }

    }

    public List<AnswerDto> getAnswerDtos() {
        return answerService.getAll()  ;
    }

    public void setAnswerDtos(List<AnswerDto> answerDtos) {
        this.answerDtos = answerDtos;
    }

    public AnswerDto getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(AnswerDto selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
