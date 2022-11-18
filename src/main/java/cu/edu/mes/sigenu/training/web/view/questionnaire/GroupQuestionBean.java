package cu.edu.mes.sigenu.training.web.view.questionnaire;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.GroupQuestionDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.GroupQuestionService;
import cu.edu.mes.sigenu.training.web.utils.ApiResponse;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
import org.primefaces.PrimeFaces;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class GroupQuestionBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private GroupQuestionService groupQuestionService;

	private List<GroupQuestionDto> groupQuestionDtos;

	private GroupQuestionDto seletedGroupQuestion;

	public void openNew(){
		this.seletedGroupQuestion = new GroupQuestionDto();
		seletedGroupQuestion.setDescription("");
		seletedGroupQuestion.setNameGroup("");
	}

	@PostConstruct
	public void init(){
        openNew();
	}

	public void save(){
	    if (!(seletedGroupQuestion.getNameGroup().length() > 2)){
            JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_group_invalid");
            return;
        }

		if(this.seletedGroupQuestion.getId() == null){
			groupQuestionService.save(seletedGroupQuestion);
			JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_added");
		}else{
			groupQuestionService.update(seletedGroupQuestion);
			JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_edited");
		}
		groupQuestionDtos = groupQuestionService.getAll();
		PrimeFaces.current().executeScript("PF('groupQuestion').hide()");
	}

	public void delete(){
         ApiResponse response = groupQuestionService.delete(this.seletedGroupQuestion.getId());
         if(response.isSuccess()){
             JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_INFO, "message_deleted");
             groupQuestionDtos = groupQuestionService.getAll();
         }else{
             JsfUtils.addMessageFromBundle(null, FacesMessage.SEVERITY_WARN, "message_not_deleted");
         }

	}

	public GroupQuestionService getGroupQuestionService() {
		return groupQuestionService;
	}

	public void setGroupQuestionService(GroupQuestionService groupQuestionService) {
		this.groupQuestionService = groupQuestionService;
	}

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

}
