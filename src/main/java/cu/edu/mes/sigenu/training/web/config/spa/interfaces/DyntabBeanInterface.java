package cu.edu.mes.sigenu.training.web.config.spa.interfaces;

import java.util.Map;

import javax.enterprise.event.Observes;

import cu.edu.mes.sigenu.training.web.config.spa.ApplicationCDIEvent;
import cu.edu.mes.sigenu.training.web.config.spa.DynTab;
import cu.edu.mes.sigenu.training.web.config.spa.DynTabCDIEvent;

public interface DyntabBeanInterface {
    public void init();
	
	
	public void callAccessPointMethod();
	public void callMethodActivity(String oldViewActivity, String newViewActivity);
	public void callViewActivity(String viewActivity);
	public void observeDynTabEvent(@Observes DynTabCDIEvent dynTabEvent);
	public void setActive(boolean active);
	public boolean getActive();
	public void callExitPointMethod();
	public void observeApplicationEvent(@Observes ApplicationCDIEvent appEvent);

	
	public void setDynTab(DynTab dt);
	public DynTab getDynTab();
    // osobine koje bean vraca iz DynTab instance koju sadrzi:
	public Map getParameters();
	public String getUniqueIdentifier();
	public String getDynTabId();

	

} 