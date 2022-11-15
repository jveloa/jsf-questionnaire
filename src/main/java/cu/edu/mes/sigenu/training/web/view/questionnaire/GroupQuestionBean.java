package cu.edu.mes.sigenu.training.web.view.questionnaire;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.GroupQuestionDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.GroupQuestionService;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class GroupQuestionBean {

    private static final long serialVersionUID = 1L;

    @Inject
    private GroupQuestionService groupQuestionService;

    List<GroupQuestionDto> groupQuestionDtos = new ArrayList<>();

    GroupQuestionDto seletedGroupQuestion = new GroupQuestionDto();


    public List<GroupQuestionDto> getGroupQuestionDtos() {
        return groupQuestionService.getAll();
    }

    public void setGroupQuestionDtos(List<GroupQuestionDto> groupQuestionDtos) {
        this.groupQuestionDtos = groupQuestionDtos;
    }

    public GroupQuestionDto getSeletedGroupQuestion() {
        return seletedGroupQuestion;
    }

    public void setSeletedGroupQuestion(GroupQuestionDto seletedGroupQuestion) {
        this.seletedGroupQuestion = seletedGroupQuestion;
    }

    public void openNew(){
        this.seletedGroupQuestion = new GroupQuestionDto();
    }

    public void openEdit(){

    }

    @PostConstruct
    public void init(){

    }

    public void save(){
        if(this.seletedGroupQuestion.getId() == null){
            groupQuestionService.save(seletedGroupQuestion);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_added");
        }else{
            groupQuestionService.update(seletedGroupQuestion);
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_edited");
        }
        groupQuestionDtos = groupQuestionService.getAll();
        PrimeFaces.current().executeScript("PF('groupQuestion').hide()");
        PrimeFaces.current().ajax().update("dt-Group");
    }

    public void delete(){

    }



}
