package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.PercentsStudyHoursByAnswerDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentNotComputerDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionCareerService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.PercentsStudyHoursByAnswerReportService;
import cu.edu.mes.sigenu.training.web.service.report.StudentNotComputerReportService;
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
public class PercentsStudyHoursByAnswerReportBean implements Serializable {

    @Inject
    private PercentsStudyHoursByAnswerReportService percentsStudyHoursByAnswerReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    @Inject
    private QuestionCareerService questionCareerService;

    private List<NationalCareerVO> careerList;

    private Integer questionnarieId;

    private Integer year;

    private List<PercentsStudyHoursByAnswerDto> percentsStudyHoursByAnswerList;


    @PostConstruct
    public void init(){
        percentsStudyHoursByAnswerList = new ArrayList<>();
        questionnarieId = 0;
        year = 0;
    }


    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<PercentsStudyHoursByAnswerDto> getAllPercentsStudyHoursByAnswer() {
        return percentsStudyHoursByAnswerReportService.getPercentsStudyHoursByAnswer(this.year,this.questionnarieId);
    }

    public void updateList(){
        percentsStudyHoursByAnswerList = percentsStudyHoursByAnswerReportService.getPercentsStudyHoursByAnswer(this.year,this.questionnarieId);

        if(percentsStudyHoursByAnswerList.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            percentsStudyHoursByAnswerList = new ArrayList<>();
        }

    }

    public String getCarrerById(String IdCareer){
        if(careerList == null){
            careerList = questionCareerService.getCareersSigenu();
        }
        return careerList.stream().filter(career -> career.getIdNationalCareer().equals(IdCareer)).findFirst().get().getName();
    }



}
