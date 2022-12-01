package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsNotesDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.StudentsByConfigurableNotesReportService;
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
public class StudentsByConfigurableNotesReportBean implements Serializable {

    @Inject
    private StudentsByConfigurableNotesReportService studentsByConfigurableNotesReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    private Integer questionnarieId;

    private Integer year;

    private  Float  noteAve;

    private  Float  noteMat;

    private  Float  noteHistory;

    private  Float  noteSpanish;



    private List<StudentsNotesDto> studentsByConfigurableNotesList;


    @PostConstruct
    public void init(){
        studentsByConfigurableNotesList = new ArrayList<>();
        questionnarieId = 0;
        year = 0;
        noteAve = 0.0F;
        noteMat = 0.0F;
        noteHistory = 0.0F;
        noteSpanish = 0.0F;

    }

    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<StudentsNotesDto> getAllStudentsByConfigurableNotes() {
        return studentsByConfigurableNotesReportService.getStudentsByConfigurableNotes(this.year,this.noteAve,this.noteSpanish,
                this.noteMat,this.noteHistory,this.questionnarieId);
    }

    public void updateList(){
        studentsByConfigurableNotesList = studentsByConfigurableNotesReportService.getStudentsByConfigurableNotes(this.year,this.noteAve,this.noteSpanish,
                this.noteMat,this.noteHistory,this.questionnarieId);

        if(studentsByConfigurableNotesList.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            studentsByConfigurableNotesList = new ArrayList<>();
        }

    }

    public void validate(){
        if ((this.noteAve < 60 && !(this.noteAve == 0)) || (this.noteHistory < 60 && !(this.noteHistory == 0))
                || (this.noteMat < 60 && !(this.noteMat == 0)) || (this.noteSpanish < 60 && !(this.noteSpanish == 0)))

            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_note_minor");

        else if((this.noteAve > 100 && !(this.noteAve == 0)) || (this.noteHistory > 100 && !(this.noteHistory == 0))
                || (this.noteMat > 100 && !(this.noteMat == 0)) || (this.noteSpanish > 100 && !(this.noteSpanish == 0)))

            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_note_major");


    }



}
