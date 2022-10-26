package cu.edu.mes.sigenu.training.web.config.spa;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;

import org.primefaces.PrimeFaces;

import cu.edu.mes.sigenu.training.web.config.spa.interfaces.DyntabBeanInterface;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
public class BaseDyntabCdiBean implements Serializable, DyntabBeanInterface {
	@PostConstruct
	public void init() {
		// TODO Auto-generated method stub
        active = false;
	}

	@Override
	public Map getParameters() {
		return (getDynTab() != null ? getDynTab().getParameters() : null);
	}
	

	private boolean mainContentRendered;
	public boolean isMainContentRendered() {
		return mainContentRendered;
	}

	
	public void setMainContentRendered(boolean value) {
		mainContentRendered = value;
	}

	private boolean errPageRendered;
	public boolean isErrPageRendered() {
		return errPageRendered;
	}
	public void setErrPageRendered(boolean value) {
		errPageRendered = value;
	}

	private String errMsg;
	public void setErrMsg(String msg) {
		errMsg = msg;
	}
	public String getErrMsg() {
		return errMsg;
	}
	
	
    public void observeDynTabEvent(@Observes DynTabCDIEvent dynTabEvent) {
    	System.out.println("observeDynTabEvent(), dynTabEvent = " + dynTabEvent);
    	
    	if (!getActive() || dynTabEvent == null)
    		 return;
    	
    	String eventType = dynTabEvent.getEventType();
    	if ( !this.equals(dynTabEvent.getTab().getCdiBean())) {
    		
     	   if ("dynTabAdded".equalsIgnoreCase(eventType)) {
     		   onDynTabAdded(dynTabEvent.getTab());
     	   } else if("dynTabRemoved".equalsIgnoreCase(eventType)) {
     		   onDynTabRemoved(dynTabEvent.getTab());
     	   } else if("dynTabSelected".equalsIgnoreCase(eventType)) {
    	       onDynTabSelected(dynTabEvent.getTab());
     	   }    
    	} else { 
    		if("dynTabSelected".equalsIgnoreCase(eventType)) {
    			onThisTabSelected();
    		}
    	}
	}// of observer metod
    
    protected void onDynTabAdded(DynTab addedTab ) {
    	    	
    }
    
    protected void onDynTabRemoved(DynTab removedTab ) {
    	    	
    }
    

    protected void onDynTabSelected(DynTab selectedTab ) {
    	    	
    }
    
    protected void onThisTabSelected() {
    	
    }
    
    public void observeApplicationEvent(@Observes ApplicationCDIEvent appEvent) {
    	System.out.println("observeApplicationEvent() call, appEvent = " + appEvent);
    	if (!getActive() || appEvent == null)
   		 return;
    	// ne reagovati na event koga je sam bean poslao:
    	String senderId = appEvent.getSenderAppModuleId();
    	String uniqueId = getUniqueIdentifier();
    	System.out.println(" -uniqueId = " + uniqueId + " klasa: " + getClass().getSimpleName());
    	if (uniqueId.equalsIgnoreCase(senderId))
    		return;
    		
    	String targetId = appEvent.getTargetAppModuleId();
    	System.out.println(" -targetId: " + targetId + ", eventType: " + appEvent.getEventType());
    	if (targetId != null &&
    		targetId.equalsIgnoreCase(uniqueId) &&
    		"JobFlowReturn".equalsIgnoreCase(appEvent.getEventType())) {
                onJobFlowReturn(senderId, appEvent.getPayload());
                return;
    	}
    	// ako je target null, poruka se salje svima. Reagovati samo ako se poruka salje svima, ili samo ovom app modulu:
        if (targetId == null || targetId.equalsIgnoreCase(uniqueId)) { // "JobFlowReturn"
    	   onApplicationMessage(senderId, appEvent.getPayload());
        } 
    }
    
    protected void onApplicationMessage(String senderId, Object payload) {
    	
    }
    
    protected void onJobFlowReturn(String senderId, Object jobFlowReturnValue) {
    	
    }

    
    public void sendMessageToAppModule(String targetAppModuleId, Object payload) {
        BeanManager bm = CDI.current().getBeanManager();
        bm.fireEvent(new ApplicationCDIEvent(this.getUniqueIdentifier(), targetAppModuleId,  payload));
    }
    
    public void closeAndReturnValueToCaller(Object returnValue) {
		String callerID = getCallerID();
		System.out.println("callerID = " + callerID);
		if (callerID != null) { // the callerID is a special param, signaling that this JobFlow is called from another JobFlow 
           BeanManager bm = CDI.current().getBeanManager();
           bm.fireEvent(new ApplicationCDIEvent("JobFlowReturn", this.getUniqueIdentifier(), callerID,  returnValue));
		}
		// close Job Flow:
		DynTabManager dtm = DynTabManager.getCurrentInstance();
		dtm.removeCurrentTab(null);
    }
    
    public String getCallerID() {
    	String result = null;
		Map params = getParameters();
		if (params != null && params.get("callerID") != null)  // the callerID is a special param, signaling that this JobFlow is called from another JobFlow 
	        result = (String)params.get("callerID");
        return result;
    }

    
    public void sendMessageToAllAppModules(Object payload) {
    	sendMessageToAppModule(null, payload);
    }

    
	public String getUniqueIdentifier() {
		return (getDynTab() != null ? getDynTab().getUniqueIdentifier() : null);
	}



	// action="{cdiBean.callViewActivity('/WEB-INF/include/employees/employees_step2.xhtml')}"
    public void callViewActivity(String viewActivity) {
    	DynTabManager manager = null;
    	try {
        	 
	    	// uzeti tekuci view sa koga se odlazi:
	    	 manager = (DynTabManager) JsfUtils.getExpressionValue("#{dynTabManager}");
	    	 String oldViewActivity = manager.getSelectedTab().getIncludePage();
	    	 // pozvati Java metodu koja treba da se izvrsi pre nevigacije na viewActivity:
	    	 callMethodActivity(oldViewActivity, viewActivity);
	    	 // ===
	           setMainContentRendered(true);
	           setErrPageRendered(false);

	    	 // ===
	    	 // za tab. postaviti novi sadrzaj za UserInterface:
	    	 manager.getSelectedTab().setIncludePage(viewActivity);
	    	
	    	 String tabClientId =  "mainForm:mainTabView:_sub_" + manager.getSelectedTabId() + ":fragmentPanel";
	    	 System.out.println("tabClientId = " + tabClientId);
	    	// UIComponent tab = JsfUtils.findComponentByClientId(tabClientId);
	  		 //PrimeFaces.current().ajax().update("mainForm:mainTabView");
	  		 PrimeFaces.current().ajax().update(tabClientId);
    	}catch(Exception ex) {
	           setMainContentRendered(false);
	           setErrPageRendered(true);
	           setErrMsg(ex.getMessage());
	           if (manager != null) {
	              PrimeFaces.current().ajax().update("mainForm:mainTabView:_sub_" + manager.getSelectedTabId() + ":fragmentPanel");
	           } 

    		/*
    		 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                    ex.getMessage(),
                                                    null);
    		 PrimeFaces.current().dialog().showMessageDynamic(message);
    		 */
    	}
    }
    
	/**
	 * Metod koji se poriva prilikom otvaranja tab-a
	 */
	protected void accessPointMethod(Map parameters) {
		
	}

	@Override 
	public final void callAccessPointMethod() {
		 try {
	           accessPointMethod(getParameters());
	           setMainContentRendered(true);
	           setErrPageRendered(false);
	        }catch(Exception exc) {
	           setMainContentRendered(false);
	           setErrPageRendered(true);
	           setErrMsg(exc.getMessage());
	     }   
	}
    
    @Override  
    public  void callMethodActivity(String oldViewActivity, String newViewActivity) {
    }
    
    private boolean active = false;
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean getActive() {
		return this.active;
	}
	
	/**
	 * Metod koji se poriva prilikom zatvaranja tab-a
	 */
	protected void exitPointMethod(Map parameters) {
		
	}


	@Override
	public void callExitPointMethod() {
		 System.out.println("callExitPointMethod() call" );
		try {
			exitPointMethod(getParameters());
		}catch(Exception exc) {
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                    exc.getMessage(),
                                                    null);
		}
	}


	@Override
	public String getDynTabId() {
		return (getDynTab() != null ? getDynTab().getId() : null);
	}

	private DynTab dt = null;
	@Override
	public void setDynTab(DynTab dt) {
		// TODO Auto-generated method stub
		this.dt = dt;
	}

	@Override
	public DynTab getDynTab() {
		// TODO Auto-generated method stub
		return this.dt;
	}

    
} 