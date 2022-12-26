package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentArtDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentSportDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionCareerService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.ArtListReportService;
import cu.edu.mes.sigenu.training.web.service.report.StudentSportListReportService;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
import cu.edu.mes.vo.NationalCareerVO;
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
public class ArtListReportBean implements Serializable {

    @Inject
    private ArtListReportService artListReportService;

    @Inject
    private QuestionCareerService questionCareerService;

    @Inject
    private QuestionnaireService questionnaireService;

    private Integer questionnarieId;

    private Integer year;

    private List<StudentArtDto> studentArtList;

    private List<NationalCareerVO> careerList;

    @PostConstruct
    public void init(){
        this.studentArtList = new ArrayList<>();
        this.questionnarieId = 0;
        this.year = 0;
    }

    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<StudentArtDto> getAllStudentArt() {
        return artListReportService.getArtList(this.year,this.questionnarieId);
    }

    public void updateList(){
        studentArtList = artListReportService.getArtList(this.year,this.questionnarieId);

        if(studentArtList.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            studentArtList = new ArrayList<>();
        }

    }

    public String getCarrerById(String IdCareer){
        if(careerList == null){
            careerList = questionCareerService.getCareersSigenu();
        }
        return careerList.stream().filter(career -> career.getIdNationalCareer().equals(IdCareer)).findFirst().get().getName();
    }



}
