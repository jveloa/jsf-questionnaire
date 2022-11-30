package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.PercentsStudyHoursByAnswerDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsNotesDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.PercentsStudyHoursByAnswerReportService;
import cu.edu.mes.sigenu.training.web.service.report.StudentsWitchNotesReportService;
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
public class StudentsWitchNotesReportBean implements Serializable {

    @Inject
    private StudentsWitchNotesReportService studentsWitchNotesReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    private Integer questionnarieId;

    private Integer year;

    private List<StudentsNotesDto> studentsNotesList;


    @PostConstruct
    public void init(){
        studentsNotesList = new ArrayList<>();
        questionnarieId = 0;
        year = 0;
    }


    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<StudentsNotesDto> getAllStudentsWitchNotes() {
        return studentsWitchNotesReportService.getStudentsWitchNotes(this.year,this.questionnarieId);
    }

    public void updateList(){
        studentsNotesList = studentsWitchNotesReportService.getStudentsWitchNotes(this.year,this.questionnarieId);

        if(studentsNotesList.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            studentsNotesList = new ArrayList<>();
        }

    }



}
