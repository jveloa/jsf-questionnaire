package cu.edu.mes.sigenu.training.web.view.questionnaire;
import cu.edu.mes.sigenu.training.web.config.spa.DynTabManager;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireQuestionByGroupDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireQuestionService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import lombok.Data;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//SessionScope solo en este bean(para que mantenga los datos entre dos vistas), las demas deben ser ViewScope
@Named
@SessionScoped
@Data
public class MakeQuestionnaireBean implements Serializable {

    @Inject
    private QuestionnaireQuestionService questionnaireQuestionService;

    @Inject
    private QuestionnaireService questionnaireService;

    private Integer questionnarieId = 0;

    private String identification = "";

    private boolean change = false;

    private List<QuestionnaireQuestionByGroupDto> questionnaireQuestions = new ArrayList<>();



    public List<QuestionnaireQuestionByGroupDto> getQuestionsByGroup() {
       if(this.change) {
           questionnaireQuestions = questionnaireQuestionService.getQuestionsByQuestionnaire(this.questionnarieId);
           this.change = false;
       }
        return questionnaireQuestions;
    }

    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    //redireccionar entre DynTab
    public String redirectToMakeQuestionnaire(){
        this.change = true;
        DynTabManager.getCurrentInstance().removeCurrentTab(true);
        return "uishell:makeQuestionnaireBean";
    }
}
