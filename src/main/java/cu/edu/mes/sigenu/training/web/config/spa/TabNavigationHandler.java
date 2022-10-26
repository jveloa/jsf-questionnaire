package cu.edu.mes.sigenu.training.web.config.spa;


import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;

import org.primefaces.application.DialogNavigationHandler;

public class TabNavigationHandler  extends DialogNavigationHandler {
   

   public TabNavigationHandler(ConfigurableNavigationHandler base) {
		super(base);
		// TODO Auto-generated constructor stub
	}

/**
    * Overridden method. Launch tab when tabName specified after colon,
    * @param facesContext
    * @param action
    * @param outcome
    */
   public void handleNavigation(FacesContext facesContext, String action, String outcome)   {
	 System.out.println("handleNavigation() begin, action = " + action + ", outcome = " + outcome);
     if (outcome != null && outcome.indexOf(":")>-1)  {
	       int pos = outcome.indexOf(":");
	       String shellAction = outcome.substring(0, pos);
	       String tabName = outcome.substring(outcome.indexOf(":") + 1);
	       // launch dyn tab
	       DynTabManager.getCurrentInstance().launchTab(tabName);
	       
	       // navigate to uishell page if needed (usually not needed because only 
	       // page is UIShell page) 
	       super.handleNavigation(facesContext, action, shellAction);
     }
     else{
           super.handleNavigation(facesContext, action, outcome);
     }
   }
 }

