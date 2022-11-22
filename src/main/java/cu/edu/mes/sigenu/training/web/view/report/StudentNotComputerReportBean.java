package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.*;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentNotComputerDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.StudentNotComputerReportService;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
import lombok.Data;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Named
@RequestScoped
@Data
public class StudentNotComputerReportBean implements Serializable {

    @Inject
    private StudentNotComputerReportService studentNotComputerReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    private Integer questionnarieId;

    private Integer year;

    private List<StudentNotComputerDto> studentNotComputerList;

    @PostConstruct
    public void init(){
        studentNotComputerList = new ArrayList<>();
        questionnarieId = 0;
        year = 0;
    }

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
