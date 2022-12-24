package cu.edu.mes.sigenu.training.web.view.questionnaire;
import com.sun.security.ntlm.Client;
import cu.edu.mes.sigenu.training.web.config.spa.DynTabManager;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.*;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireQuestionService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireStudentService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.StudentAnswerService;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
import cu.edu.mes.subsystem.student.vo.StudentVO;
import lombok.Data;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//SessionScope solo en este bean(para que mantenga los datos entre dos vistas), las demas deben ser ViewScope
@Named
@SessionScoped
@Data
public class MakeQuestionnaireBean implements Serializable {

    @Inject
    private QuestionnaireQuestionService questionnaireQuestionService;

    @Inject
    private QuestionnaireService questionnaireService;

    @Inject
    private QuestionnaireStudentService questionnaireStudentService;

    @Inject
    private StudentAnswerService studentAnswerService;

    private Integer questionnarieId = 0;

    private String questionnaireName = "Bienvenido";

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

    public void sendQuestionnaire(){
        List<StudentAnswerDto> studentAnswerDtos = new ArrayList<>();
        for (QuestionnaireQuestionByGroupDto item: this.questionnaireQuestions) {
            for (QuestionnaireQuestionDto questionDto : item.getQuestionnaireQuestionDto()){

                QuestionAnswerDto questionAnswerDto = QuestionAnswerDto.builder().questionId(questionDto.getQuestion().getId())
                                                                       .answerId(questionDto.getSelectedAnswer())
                                                                       .build();

                studentAnswerDtos.add(StudentAnswerDto.builder().identification(this.identification)
                                                      .questionAnswerId(questionAnswerDto)
                                                      .build());
            }
        }
        boolean isSuccessful = studentAnswerService.saveStudentAnswer(studentAnswerDtos,questionnarieId);

        if (isSuccessful) {
            JsfUtils.addMessageFromBundle("panelMake",FacesMessage.SEVERITY_INFO,"save_answer_ok");
            DynTabManager.getCurrentInstance().removeCurrentTab(true);
        } else {
            JsfUtils.addMessageFromBundle("panelMake", FacesMessage.SEVERITY_ERROR, "save_answer_fail");
        }
    }



    //redireccionar entre DynTab
    public String redirectToMakeQuestionnaire(){
        StudentVO studentVO = checkStudentSigenu(identification);
        if (studentVO.getIdStudent() == null){
            JsfUtils.addMessageFromBundle(null,FacesMessage.SEVERITY_WARN,"student_not_exist");
            return "";
        }

        Optional<QuestionnaireDto> questionnaire = getQuestionnaireByCarrer(studentVO.getCareer().getIdNationalCareer());
        if(!questionnaire.isPresent()){
            JsfUtils.addMessageFromBundle(null,FacesMessage.SEVERITY_WARN,"student_not_questionnaire_career");
            return "";
        } else
            questionnarieId = questionnaire.get().getId();

        if (checkStudentAlreadyTookQuestionnaire(studentVO.getIdStudent(),questionnarieId)){
            JsfUtils.addMessageFromBundle(null,FacesMessage.SEVERITY_WARN,"student_alredy_questionnaire");
            return "";
        }

        this.change = true;
        questionnaireName = questionnaire.get().getName();
        DynTabManager.getCurrentInstance().removeCurrentTab(true);
        return "uishell:makeQuestionnaireBean";
    }

    public StudentVO checkStudentSigenu(String identification){
        return questionnaireStudentService.getCheckStudentByIdentification(identification);
    }

    public boolean checkStudentAlreadyTookQuestionnaire(String sigenuId,Integer questionnarieId){
    	return questionnaireStudentService.questionnaireByStudent(sigenuId)
    				.stream().anyMatch(item -> item.getQuestionnarieId().getId().equals(questionnarieId));
    }

    public Optional<QuestionnaireDto> getQuestionnaireByCarrer(String nationalCareerId){
        Optional<QuestionnaireDto> questionnaire = getAllQuestionnaire().stream().filter(questionnaireDto ->
                                                                                      questionnaireDto.getCareerSigenuId().equals(nationalCareerId)).findFirst();
        return questionnaire;

    }
}
