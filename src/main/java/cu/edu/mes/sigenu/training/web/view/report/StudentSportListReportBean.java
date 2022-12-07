package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentNotComputerDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentSportDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.StudentNotComputerReportService;
import cu.edu.mes.sigenu.training.web.service.report.StudentSportListReportService;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Named
@ViewScoped
@Data
public class StudentSportListReportBean implements Serializable {

    @Inject
    private StudentSportListReportService studentSportListReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    private Integer questionnarieId;

    private Integer year;

    private List<StudentSportDto> studentSportList;

    @PostConstruct
    public void init(){
        this.studentSportList = new ArrayList<>();
        this.questionnarieId = 0;
        this.year = 0;
    }

    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<StudentSportDto> getAllStudentSport() {
        return studentSportListReportService.getStudentSportList(this.year,this.questionnarieId);
    }

    public void updateList(){
        studentSportList = studentSportListReportService.getStudentSportList(this.year,this.questionnarieId);

        if(studentSportList.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            studentSportList = new ArrayList<>();
        }

    }



}
