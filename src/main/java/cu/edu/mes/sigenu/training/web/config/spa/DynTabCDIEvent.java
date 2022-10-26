package cu.edu.mes.sigenu.training.web.config.spa;

public class DynTabCDIEvent {
	// dynTabAdded, dynTabRemoved, dynTabSelected
	 private String eventType;
	 public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	
	public DynTab getTab() {
		return tab;
	}
	public void setTab(DynTab tab) {
		this.tab = tab;
	}
	
	private DynTab tab;
     public DynTabCDIEvent(String eventType, DynTab tab) {
    	 this.eventType =  eventType;
    	 this.tab = tab;
     }
     
}
