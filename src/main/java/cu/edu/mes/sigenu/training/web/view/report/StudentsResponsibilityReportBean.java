package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.ResponsabilityReportDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentArtDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionCareerService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.ArtListReportService;
import cu.edu.mes.sigenu.training.web.service.report.StudentsResponsibilityReportService;
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
public class StudentsResponsibilityReportBean implements Serializable {

    @Inject
    private StudentsResponsibilityReportService studentsResponsibilityReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    @Inject
    private QuestionCareerService questionCareerService;

    private List<NationalCareerVO> careerList;

    private Integer questionnarieId;

    private Integer year;

    private List<ResponsabilityReportDto> studentResponsibilityList;

    @PostConstruct
    public void init(){
        this.studentResponsibilityList = new ArrayList<>();
        this.questionnarieId = 0;
        this.year = 0;
    }

    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<ResponsabilityReportDto> getAllStudentResponsibility() {
        return studentsResponsibilityReportService.getStudentsResponsibilityList(this.year,this.questionnarieId);
    }

    public void updateList(){
        studentResponsibilityList = studentsResponsibilityReportService.getStudentsResponsibilityList(this.year,this.questionnarieId);

        if(studentResponsibilityList.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            studentResponsibilityList = new ArrayList<>();
        }

    }

    public String getCarrerById(String IdCareer){
        if(careerList == null){
            careerList = questionCareerService.getCareersSigenu();
        }
        return careerList.stream().filter(career -> career.getIdNationalCareer().equals(IdCareer)).findFirst().get().getName();
    }



}
