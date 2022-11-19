package cu.edu.mes.sigenu.training.web.view.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import cu.edu.mes.sigenu.training.web.config.spa.DynTabManager;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.*;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentNotComputerDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireQuestionService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireStudentService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.StudentAnswerService;
import cu.edu.mes.sigenu.training.web.service.report.StudentNotComputerReportService;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
import lombok.Data;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Named
@ViewScoped
@Data
public class StudentNotComputerReportBean implements Serializable {

    @Inject
    private StudentNotComputerReportService studentNotComputerReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    private Integer questionnarieId;

    private Integer year;

    private List<StudentNotComputerDto> studentNotComputerList = new ArrayList<>();



    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<StudentNotComputerDto> getAllStudentNotComputer() {
        return studentNotComputerReportService.getStudentsNotComputer(this.year,this.questionnarieId);
    }

    public void updateList(){
        studentNotComputerList = studentNotComputerReportService.getStudentsNotComputer(this.year,this.questionnarieId);

        if(studentNotComputerList.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            studentNotComputerList = new ArrayList<>();
        }

    }



}
