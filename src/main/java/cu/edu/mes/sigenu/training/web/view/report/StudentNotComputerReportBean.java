package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.*;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentNotComputerDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionCareerService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.StudentNotComputerReportService;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
import cu.edu.mes.vo.NationalCareerVO;
import lombok.Data;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
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
public class StudentNotComputerReportBean implements Serializable {

    @Inject
    private StudentNotComputerReportService studentNotComputerReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    @Inject
    private QuestionCareerService questionCareerService;

    private List<NationalCareerVO> careerList;

    private Integer questionnarieId;

    private Integer year;

    private List<StudentNotComputerDto> studentNotComputerList;

    @PostConstruct
    public void init(){
        this.studentNotComputerList = new ArrayList<>();
        this.questionnarieId = 0;
        this.year = 0;
    }

    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<StudentNotComputerDto> getAllStudentNotComputer() {
        return studentNotComputerReportService.getStudentsNotComputer(this.year,this.questionnarieId);
    }

    public void updateList(){
        studentNotComputerList = studentNotComputerReportService.getStudentsNotComputer(this.year,this.questionnarieId);

        if(studentNotComputerList.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            studentNotComputerList = new ArrayList<>();
        }

    }

    public String getCarrerById(String IdCareer){
        if(careerList == null){
            careerList = questionCareerService.getCareersSigenu();
        }
        return careerList.stream().filter(career -> career.getIdNationalCareer().equals(IdCareer)).findFirst().get().getName();
    }



}
