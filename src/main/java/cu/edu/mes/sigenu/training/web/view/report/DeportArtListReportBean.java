package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.DeportArtListDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentArtDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.ArtListReportService;
import cu.edu.mes.sigenu.training.web.service.report.DeportArtListReportService;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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
public class DeportArtListReportBean implements Serializable {

    @Inject
    private DeportArtListReportService deportArtListReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    private Integer questionnarieId;

    private Integer year;

    private List<DeportArtListDto> deportArtList;

    @PostConstruct
    public void init(){
        this.deportArtList = new ArrayList<>();
        this.questionnarieId = 0;
        this.year = 0;
    }

    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<DeportArtListDto> getAllDeportArtList() {
        return deportArtListReportService.getDeportArtListByStudent(this.year,this.questionnarieId);
    }

    public void updateList(){
        deportArtList = deportArtListReportService.getDeportArtListByStudent(this.year,this.questionnarieId);

        if(deportArtList.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            deportArtList = new ArrayList<>();
        }

    }



}
