package cu.edu.mes.sigenu.training.web.view.report;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.EntrySourceAuxDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionCareerService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.StudentsByEntrySourceReportService;
import cu.edu.mes.sigenu.training.web.service.report.StudentsByPlaceEgressReportService;
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
public class StudentsByPlaceEgressReportBean implements Serializable {

    @Inject
    private StudentsByPlaceEgressReportService studentsByPlaceEgressReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    @Inject
    private QuestionCareerService questionCareerService;

    private List<NationalCareerVO> careerList;

    private Integer questionnarieId;

    private Integer year;

    private List<String> studentsByPlaceEgress;

    private String idPlaceEgress;

    @PostConstruct
    public void init(){
        this.studentsByPlaceEgress = new ArrayList<String>();
        this.questionnarieId = 0;
        this.year = 0;
        this.idPlaceEgress = "";
    }

    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public List<EntrySourceAuxDto> getAllPlaceEgress() {
        return studentsByPlaceEgressReportService.getAllPlaceEgress();
    }

    public List<String> getAllStudentsByPlaceEgress() {

        List<String> aux = new ArrayList<>();
        if (this.idPlaceEgress.equals(""))
            return aux;

        return studentsByPlaceEgressReportService.getStudentsByPlaceEgress(this.year,this.idPlaceEgress,this.questionnarieId);
    }

    public void updateList(){
        studentsByPlaceEgress = studentsByPlaceEgressReportService.getStudentsByPlaceEgress(this.year,this.idPlaceEgress,this.questionnarieId);

        if(studentsByPlaceEgress.isEmpty()){
            JsfUtils.addMessageFromBundle("panel", FacesMessage.SEVERITY_WARN, "search_not_found");
            studentsByPlaceEgress = new ArrayList<String>();
        }

    }

    public String getCarrerById(String IdCareer){
        if(careerList == null){
            careerList = questionCareerService.getCareersSigenu();
        }
        return careerList.stream().filter(career -> career.getIdNationalCareer().equals(IdCareer)).findFirst().get().getName();
    }



}
