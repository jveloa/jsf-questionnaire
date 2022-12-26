package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.PercentsStudyHoursByAnswerDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsAnswerByAnswerByQuestionDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionCareerService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.PercentsStudyFormsByAnswerReportService;
import cu.edu.mes.sigenu.training.web.service.report.PercentsStudyHoursByAnswerReportService;
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
public class PercentsStudyFormsByAnswerReportBean implements Serializable {

    @Inject
    private PercentsStudyFormsByAnswerReportService percentsStudyFormsByAnswerReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    @Inject
    private QuestionCareerService questionCareerService;

    private List<NationalCareerVO> careerList;

    private Integer questionnarieId;

    private Integer year;

    private List<StudentsAnswerByAnswerByQuestionDto> percentsStudyFormsByAnswerList;


    @PostConstruct
    public void init(){
        percentsStudyFormsByAnswerList = new ArrayList<>();
        questionnarieId = 0;
        year = 0;
    }


    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<StudentsAnswerByAnswerByQuestionDto> getAllPercentsStudyFormsByAnswer() {
        return percentsStudyFormsByAnswerReportService.getPercentsStudyFormsByAnswer(this.year,this.questionnarieId);
    }

    public void updateList(){
        percentsStudyFormsByAnswerList = percentsStudyFormsByAnswerReportService.getPercentsStudyFormsByAnswer(this.year,this.questionnarieId);

        if(percentsStudyFormsByAnswerList.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            percentsStudyFormsByAnswerList = new ArrayList<>();
        }

    }

    public String getCarrerById(String IdCareer){
        if(careerList == null){
            careerList = questionCareerService.getCareersSigenu();
        }
        return careerList.stream().filter(career -> career.getIdNationalCareer().equals(IdCareer)).findFirst().get().getName();
    }



}
