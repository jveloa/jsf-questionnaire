package cu.edu.mes.sigenu.training.web.config.spa;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.PrimeFaces;
import org.primefaces.component.dialog.Dialog;
import org.primefaces.event.CloseEvent;

import cu.edu.mes.sigenu.training.web.config.spa.interfaces.DyntabBeanInterface;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;



public class DynTab implements Serializable {
	 private String id = null;
     private String name;
     private boolean isActive;
     private String title;
     private String uniqueIdentifier = null;
     
     
     
     private Map parameters = new HashMap();
     private boolean parameterValuesResolved = false;



	
	private DyntabBeanInterface cdiBean = null;
	public void setCdiBean(DyntabBeanInterface cdiBean) {
		this.cdiBean = cdiBean;
		System.out.println("setCdiBean(), cdiBean = " + cdiBean);
	}
	public DyntabBeanInterface getCdiBean() {
		return this.cdiBean;
	}
	
	


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
		
		
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

     /**
      * Da li je tab  (p:tab u dyntab_test.xhtml template) vidljiv na ekranu
      * @return
      */
	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}



     
     public DynTab() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}


	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;

	}

    private String includePage;
	public String getIncludePage() {
		return includePage;
	}


	public void setIncludePage(String includePage) {
		this.includePage = includePage;
	}


	public DynTab(String id, String includePage) {
    	 this.id = id;
    	 this.includePage = includePage;
    	 isActive = false;
     }
	
	private boolean activated = false;
    public void setActivated(boolean activated)	  {
	    if (activated) {
	      if (!this.activated) {
	        System.out.println("Activating tab with id " + getId() + " (first time)." );
	      } else{
	    	System.out.println("Tab with id " + getId() + " has already been activated." );	    	  
	      }
	    }
	  }

	public boolean isActivated() {
	    return activated;
	}
	  
	  private boolean isCloseable = true;
	  public void setCloseable(boolean isCloseable) {

	    this.isCloseable = isCloseable;
	  }

	  public boolean isCloseable(){
	    return this.isCloseable;
	  }

	  /**
	   * Looks up the dyn tab bean for given tab name.
	   * This bean is named after the tab, suffixed with "DynTab" and placed in
	   * request scope
	   * @param tabName
	   */
	  public static DynTab getInstance(String tabName) {
		System.out.println("getInstance() tabName =   " + tabName );
	    String beanName = tabName + "DynTab";
	    String expr = "#{" + beanName + "}";
	    DynTab tab =  (DynTab) JsfUtils.getExpressionValue(expr);
	    if (tab != null)
	       System.err.println(" -pronadjen tab, uniqueId =   " + tab.getUniqueIdentifier() );
	    if (tab == null)	    {
	      // try viewScope, must be used when tab is initially displayed
	      expr = "#{viewScope." + beanName + "}";
	      tab =  (DynTab) JsfUtils.getExpressionValue(expr);
  	      if (tab != null)
			  System.err.println(" -pronadjen u viewScope, uniqueId =   " + tab.getUniqueIdentifier() );
	      
	    }
	    if (tab == null) {
	      //sLog.severe("Could not find DynTab bean " + beanName + "!");
	   	  System.err.println("Ne moze se naci tab  " + tabName );
	    }
	    tab.setName(tabName);
	    return tab;
	  }

	  public void setParameters(Map parameters) {
	    this.parameters = parameters;
        // setovati parametre u CdiBean, ako je postavljen:
	  }

	  public Map getParameters()
	  {
	    Map newParams = new HashMap();
	    if (!parameterValuesResolved)
	    {
	      parameterValuesResolved = true;
	      Iterator keys = parameters.keySet().iterator();
	      while (keys.hasNext())
	      {
	        Object key = keys.next();
	        Object value = parameters.get(key);
	        if (value instanceof String && ((String) value).startsWith("#{"))
	        {
	          value = JsfUtils.getExpressionValue((String) value);
	        }
	        newParams.put(key, value);
	      }
	      parameters = newParams;
	    }
	    return parameters;
	  }
     /**
      * Uslov za vidljivost dijaloga u dinamickom tabu:
      * 
      * VAZNO: visible i rendered metodi mogu i da primaju parametre
      * koriscenje na stranici:
      * 
      *   visible="#{dynTab.isComponentVisibleByClientId('neki_string')}" PRIMER:
      *   
      *  @param key : dolazi kao neki String, konkretno, na departments.xhtml, se salje clientId dialoga, u formi:
      *      visible="#{dynTab.isComponentVisibleByClientId(fn:replace(component.clientId,':','_'))}"
      *      
      *  i vrednost koja dolazi je:
      *          mainForm_mainTabView__sub_r1_pB1
      *          mainForm:mainTabView:_sub_r1:pB1
      *          
      *  metod vraca true ako u viewScope, pod  atributom koji dolazi kao param (key), postoji boolean value = true,
      *  inace vreaca false        
      * @return
      */
     public boolean isComponentVisibleByClientId(String key) {
    	// varijanta kada se sa stranice salje clientId od dugmeta, u formi: 
    	 //  visible="#{dynTab.isComponentVisibleByClientId(fn:replace(component.clientId,':','_'))}"
    	 
  		System.out.println("isDialogVisible(), key = " + key );
 		Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
 		Boolean result = (Boolean)viewMap.get(key);
 		result =  (result == null ? false : result.booleanValue());
 		System.out.println("isDialogVisible() vraca: " + result);
 		return result;
 		
    	 
     }
     
     /**
      * Koriscenje na stranici:
      * 
      * visible="#{dynTab.isComponentVisibleById(component.id)}"
      * 
      * 
      * 
      * @param key
      * @return
      */
     public boolean isComponentVisibleById(String key) {
    	 
  		System.out.println("isComponentVisibleById(), key = " + key );
 		Map<String, Object> visibleMap = getDynTabVisibleMap();
 		Boolean result = (Boolean)visibleMap.get(key);
 		result =  (result == null ? false : result.booleanValue());
 		System.out.println("isComponentVisibleById() vraca: " + result);
 		return result;
     }
     public  Map<String, Object> getDynTabVisibleMap(){
  		Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
  		Map<String, Object> visibleMap = (Map<String, Object>)viewMap.get(this.getUniqueIdentifier());
  		if (visibleMap == null) {
  			visibleMap = new HashMap<String, Object>();
  			viewMap.put(this.getUniqueIdentifier(), visibleMap);
  		}
  		return visibleMap;
     }
     
     public String getTabSubviewClientId() {
    	 return "mainForm:mainTabView:_sub_" + getId();
     }

     /**
      * Izbaciti visible property iz mape koju vraca getDynTabVisibleMap(), i koja se koristi u isComponentVisibleById()
      * to jest, u metodi koja regulise visible za dijalog, prema njegovom Id 
      * @param event
      */
     public void handleDlgClose(CloseEvent event) {
         System.out.println("handleDlgClose() call");
         UIComponent dlgComp = event.getComponent();
         if (dlgComp != null && dlgComp instanceof Dialog) {
        	 System.out.println("- pre remove() visible vraca: " + isComponentVisibleById(dlgComp.getId()));
        	 getDynTabVisibleMap().remove(dlgComp.getId());
        	 System.out.println("_POSLE remove() visible vraca:: " + isComponentVisibleById(dlgComp.getId()));
        	 
         }
     }  
     
     public void closeDialog(ActionEvent e) {
    	 if (e != null) {
    	     Dialog parentDlg = (Dialog) JsfUtils.findParentComponentOfClass(e.getComponent(), Dialog.class);
    	     if (parentDlg != null) {
    	    	 String jsCloseDlg = "PF('" + parentDlg.resolveWidgetVar() + "').hide()";
 		    	  PrimeFaces.current().executeScript(jsCloseDlg);
 		    	  // regulisati visible proeprty:
 		    	  getDynTabVisibleMap().remove(parentDlg.getId());
    	     }
    	 }
     }


}
