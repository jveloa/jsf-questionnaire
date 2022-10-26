package cu.edu.mes.sigenu.training.web.config.spa;

public class ApplicationCDIEvent {
  private String senderAppModuleId;	
  private String targetAppModuleId;
  private Object payload;
  
  public ApplicationCDIEvent(String senderAppModuleId, String targetAppModuleId, Object payload) {
	  this.senderAppModuleId = senderAppModuleId;
	  this.targetAppModuleId = targetAppModuleId;
	  this.payload = payload;
  }
  
  private String eventType = null;
  public ApplicationCDIEvent(String eventType, String senderAppModuleId, String targetAppModuleId, Object payload) {
	  this(senderAppModuleId, targetAppModuleId, payload);
	  this.eventType = eventType;  
  }


  public String getSenderAppModuleId() {
	  return this.senderAppModuleId;
  }

  
  public String getTargetAppModuleId() {
	  return this.targetAppModuleId;
  }
  public Object getPayload() {
	  return this.payload;
  }
  
  public String getEventType() {
	  return this.eventType;
  }
  
}
