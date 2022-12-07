package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.EntrySourceAuxDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsWithNotesDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.EntryDataByCourseByPlaceEgressReportService;
import cu.edu.mes.sigenu.training.web.service.report.StudentsByPlaceEgressReportService;
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
public class EntryDataByCourseByPlaceEgressReportBean implements Serializable {

    @Inject
    private EntryDataByCourseByPlaceEgressReportService entryDataByCourseByPlaceEgressReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    private Integer questionnarieId;

    private Integer year;

    private List<StudentsWithNotesDto> entryDataByCourseByPlaceEgress;

    private String idPlaceEgress;

    @PostConstruct
    public void init(){
        this.entryDataByCourseByPlaceEgress = new ArrayList<StudentsWithNotesDto>();
        this.questionnarieId = 0;
        this.year = 0;
        this.idPlaceEgress = "";
    }

    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<EntrySourceAuxDto> getAllPlaceEgress() {
        return entryDataByCourseByPlaceEgressReportService.getAllPlaceEgress();
    }

    public List<StudentsWithNotesDto> getAllEntryDataByCourseByPlaceEgress() {

        List<StudentsWithNotesDto> aux = new ArrayList<StudentsWithNotesDto>();
        if (this.idPlaceEgress.equals(""))
            return aux;

        return entryDataByCourseByPlaceEgressReportService.getEntryDataByCourseByPlaceEgress(this.year,this.idPlaceEgress,this.questionnarieId);
    }

    public void updateList(){
        entryDataByCourseByPlaceEgress = entryDataByCourseByPlaceEgressReportService.getEntryDataByCourseByPlaceEgress(this.year,this.idPlaceEgress,this.questionnarieId);

        if(entryDataByCourseByPlaceEgress.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            entryDataByCourseByPlaceEgress = new ArrayList<StudentsWithNotesDto>();
        }

    }



}
