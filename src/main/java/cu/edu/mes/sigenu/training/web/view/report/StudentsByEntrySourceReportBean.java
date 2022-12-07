package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.EntrySourceAuxDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentNotComputerDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.StudentNotComputerReportService;
import cu.edu.mes.sigenu.training.web.service.report.StudentsByEntrySourceReportService;
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
public class StudentsByEntrySourceReportBean implements Serializable {

    @Inject
    private StudentsByEntrySourceReportService studentsByEntrySourceReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    private Integer questionnarieId;

    private Integer year;

    private List<String> studentsByEntrySource;

    private String idEntrySource;

    @PostConstruct
    public void init(){
        this.studentsByEntrySource = new ArrayList<String>();
        this.questionnarieId = 0;
        this.year = 0;
        this.idEntrySource = "";
    }

    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<EntrySourceAuxDto> getAllEntrySource() {
        return studentsByEntrySourceReportService.getAllEntrySource();
    }

    public List<String> getAllStudentsByEntrySource() {

        List<String> aux = new ArrayList<>();
        if (this.idEntrySource.equals(""))
            return aux;

        return studentsByEntrySourceReportService.getStudentsByEntrySource(this.year,this.idEntrySource,this.questionnarieId);
    }

    public void updateList(){
        studentsByEntrySource = studentsByEntrySourceReportService.getStudentsByEntrySource(this.year,this.idEntrySource,this.questionnarieId);

        if(studentsByEntrySource.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            studentsByEntrySource = new ArrayList<String>();
        }

    }



}
