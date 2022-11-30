package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsNotesDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsWithNotesDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.EntryDataByCourseReportService;
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
public class EntryDataByCourseReportBean implements Serializable {

    @Inject
    private EntryDataByCourseReportService entryDataByCourseReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    private Integer questionnarieId;

    private Integer year;

    private List<StudentsWithNotesDto> studentsWithNotesList;


    @PostConstruct
    public void init(){
        studentsWithNotesList = new ArrayList<>();
        questionnarieId = 0;
        year = 0;
    }


    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<StudentsWithNotesDto> getAllEntryDataByCourse() {
        return entryDataByCourseReportService.getEntryDataByCourse(this.year,this.questionnarieId);
    }

    public void updateList(){
        studentsWithNotesList = entryDataByCourseReportService.getEntryDataByCourse(this.year,this.questionnarieId);

        if(studentsWithNotesList.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            studentsWithNotesList = new ArrayList<>();
        }

    }



}
