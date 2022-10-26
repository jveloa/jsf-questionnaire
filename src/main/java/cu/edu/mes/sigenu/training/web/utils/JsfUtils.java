package cu.edu.mes.sigenu.training.web.utils;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.el.ValueExpression;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

//import cu.edu.mes.sigenu.utils.String;

//import cu.edu.mes.sigenu.utils.HttpSession;
import javax.servlet.http.HttpSession;
//import cu.edu.mes.sigenu.utils.Object;
//import cu.edu.mes.sigenu.utils.String;

import org.primefaces.PrimeFaces;
import org.primefaces.component.dialog.Dialog;

//import cu.edu.mes.sigenu.utils.Object;
//import cu.edu.mes.sigenu.utils.String;

public class JsfUtils {
	public static void addMessage(String componentId, Severity severity, String summary) {
		addMessage(componentId, severity, summary, null);
	}
	
	public static FacesContext getFacesContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context;
	}
	
	public static void addMessage(String componentId, Severity severity, String ...msgs) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(severity, msgs[0], msgs[1]);
		facesContext.addMessage(componentId, message);
	}
	
	public static void addMessageFromBundle(String componentId, Severity severity, String summaryKey) {
		addMessage(componentId, severity, getStringValueFromBundle(summaryKey), null);
		PrimeFaces.current().ajax().update(":mainForm:msgs");
	}
	
	public static void addMessageFromBundle(String componentId, Severity severity, String summaryKey, String param) {
		addMessage(componentId, severity, MessageFormat.format(getStringValueFromBundle(summaryKey), param), null);
	}

	public static void addMessageFromBundle(String componentId, Severity severity, String ...keys) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(severity, getStringValueFromBundle(keys[0]), getStringValueFromBundle(keys[1]));
		facesContext.addMessage(componentId, message);
	}
	
	public static String getStringValueFromBundle(String key) {
		return ResourceBundle.getBundle("i18n.messages", getCurrentLocale()).getString(key);
	}
	
	public static String getDataPanelAddress(String key) {
		return ResourceBundle.getBundle("datapanel.datapanel").getString(key);	
	}
	
	public static String getStringValueFromBundleWithParam(String msgKey, String paramValue ) {
		ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", getCurrentLocale());
		String  msgValue = bundle.getString(msgKey);
	    MessageFormat   messageFormat = new MessageFormat(msgValue);
	    Object[] args = {paramValue};
	    return messageFormat.format(args);
	}
	
	public static Object resolverBean (String beanName){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, beanName);
  }
	
	public static Locale getCurrentLocale(){
		return FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}
	
	public static Object getExpressionValue(String jsfExpression)
    {
      // when specifying EL expression in managed bean as "literal" value
      // so t can be evaluated later, the # is replaced with $, quite strange
      if (jsfExpression == null)
      {
        return jsfExpression;
      }
      if (jsfExpression.startsWith("${"))
      {
        jsfExpression = "#{" + jsfExpression.substring(2);
      }
      if (!jsfExpression.startsWith("#{"))
      {
        if (jsfExpression.equalsIgnoreCase("true"))
        {
          return Boolean.TRUE;
        }
        else if (jsfExpression.equalsIgnoreCase("false"))
        {
          return Boolean.FALSE;
        }
        // there can be literal text preceding the expression...
        else if (jsfExpression.indexOf("#{")<0)
        {
          return jsfExpression;
        }
      }
      ValueExpression ve =  getApplication().getExpressionFactory().createValueExpression(FacesContext.getCurrentInstance().getELContext(),jsfExpression,Object.class);
      return ve.getValue(FacesContext.getCurrentInstance().getELContext());
    }

    private String APPLICATION_FACTORY_KEY =
    	    "javax.faces.application.ApplicationFactory";

 
    public static Application getApplication()    {
      FacesContext context = FacesContext.getCurrentInstance();
      if (context != null)
      {
        return FacesContext.getCurrentInstance().getApplication();
      }
      else
      {
        ApplicationFactory afactory =
          (ApplicationFactory) FactoryFinder.getFactory("javax.faces.application.ApplicationFactory");
        return afactory.getApplication();
      }
    }
    
    public static UINamingContainer findParentNamingContainer(UIComponent component) {
        if (component == null){
            return null;
        }

        UIComponent parent = component.getParent();
        UINamingContainer nc = null;
        while (parent!=null)
        {
          if (parent instanceof UINamingContainer)
          {
            nc = (UINamingContainer) parent;
            break;
          }
          parent = parent.getParent();
        }
        
        return nc;
      }
    /**
     * Za komponentu component, prinalazi prvi parent NamingContainer ciji id pocinje sa idPrefix
     * @param component
     * @param idPrefix
     * @return
     */
    public static UINamingContainer findParentNamingContainerWithIdPrefix(UIComponent component, String idPrefix) {
    	System.out.println("findParentNamingContainerWithIdPrefix() begin");
        if (component == null){
            return null;
        }

        UIComponent parent = component.getParent();
        UINamingContainer nc = null;
        while (parent!=null) {
          if (parent instanceof UINamingContainer){
             nc = (UINamingContainer) parent;
             String ncId = parent.getId();
             System.err.println("-ncId = " + ncId);
             if (ncId.startsWith(idPrefix))
                break;
          }
          parent = parent.getParent();
        }
        System.out.println("findParentNamingContainerWithIdPrefix() vraca: " + nc);
        return nc;
    }
    
    public static UIComponent findParentWithIdPrefix(UIComponent component, String idPrefix) {
        if (component == null){
            return null;
        }

        UIComponent parent = component.getParent();
        while (parent != null) {
             String Id = parent.getId();
             if (Id.startsWith(idPrefix))
                break;
          parent = parent.getParent();
        }
        if (parent != null && !(parent instanceof org.primefaces.component.dialog.Dialog))
        	System.err.println("***findParentWithIdPrefix(): za idPrefix " + idPrefix + " pronadjen parent koji _nije p:dialog: " + parent.getClass().getName());
        return parent;
    	
    }
    
    public static UIComponent findParentForm(UIComponent component) {
        if (component == null){
            return null;
        }

        UIComponent parent = component.getParent();
        while (parent != null) {
          String Id = parent.getId();
          //System.err.println("parent id = " + Id + ", class = " + parent.getClass().getName());
          if (parent instanceof javax.faces.component.html.HtmlForm)
        	  break;
          parent = parent.getParent();
        }
        return parent;
    	
    }
    
    public static UIComponent findParentComponentOfClass(UIComponent component, Class clazz) {
    	
        if (component == null){
            return null;
        }
        UIComponent parent = component.getParent();
        while (parent != null) {
          if (clazz.isInstance(parent)) 
        	  break;
          parent = parent.getParent();
        }
        return parent;
    }

    
    public static UIComponent findComponent(UIComponent base, String id)  {

      // Is the "base" component itself the match we are looking for?
      if (id.equals(base.getId())) {
        return base;
      }
      // check for direct child
      UIComponent result = base.findComponent(id);
      if (result!=null)      {
        return result;
      }

      // Search through our facets and children
      UIComponent kid = null;
      Iterator kids = base.getFacetsAndChildren();
      while (kids.hasNext() && (result == null)) {
        kid = (UIComponent) kids.next();
        if (id.equals(kid.getId()))        {
          result = kid;
          break;
        }
        result = findComponent(kid, id);
        if (result != null) {
          break;
        }
      }
      return result;
    }

    /**
     * U prvom parent NamingContainer-u za prosledjni component, ciji id pocinje sa ncIdPrefix, pronalazi i vraca komponentu ciji je id jednak zadatom Id	
     * @param component
     * @param ncIdPrefix
     * @param Id
     * @return
     */
    public static UIComponent findComponentWithIdInNamigContainer(UIComponent component, String ncIdPrefix, String Id) {
    	System.out.println("findComponentWithIdInNamigContainer() begin, ncIdPrefix = " + ncIdPrefix + ", Id = " + Id);
    	// Za komponentu component, prinalazi prvi parent NamingContainer ciji id pocinje sa ncIdPrefix:
    	UINamingContainer nc = findParentNamingContainerWithIdPrefix(component, ncIdPrefix);
    	// ===
        if (nc == null){
            return null;
        }
        UIComponent result = findComponent(nc, Id);
        System.out.println("findComponentWithIdInNamigContainer() vraca: " + result);
        return result;
    }
    	
    public static ValueExpression createValueExpression(String exprStr, Class cls){
        FacesContext context = FacesContext.getCurrentInstance();  
        Application app = context.getApplication();  
        ValueExpression valueEx = app.getExpressionFactory().createValueExpression(context.getELContext(), exprStr, cls);  
        System.out.println("za exprStr = " + exprStr + ", createValueExpression() vraca : " + valueEx);
        return valueEx;        
    }

    /**
     * Poziva se u DynTabManager, na zatvaranju p:tab-a.
     * Zatvara dijalog,  i postavlja mu uslov za visible na false
     */
    public static void closePFDialog(Dialog dlg) {
		String jsCloseDlg = "PF('" + dlg.resolveWidgetVar() + "').hide()";
		
		PrimeFaces.current().executeScript(jsCloseDlg);
		System.out.println("closePFDialog(), za dialog " + dlg.getClientId() + " pozvan " + jsCloseDlg);
		removePFDialogVisibleCondition(dlg.getClientId());
    }
    
    
    
    /**
     * Svaki crud Dialog, kao visible uslov ima sledeci expression:
     *   "#{viewScope." + dlg.getClientId().replace(":", "_")  + "}"
     * i to mu se postavlja u solveDlgVisible()
     * Ovaj metod omogucava da taj uslov bude ispunjen, tj, da dialog postane visible.
     * Poziva se u crudListener()
     * @param dlg
     */
    public static void openPFDialog(Dialog dlg) {
        String dlgClientId = dlg.getClientId();
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        String dlgClId_rep = dlgClientId.replace(":", "_");
        viewMap.put(dlgClId_rep, true);
        System.out.println("-OTVOREN DIJALOG " + dlgClientId + ", viewAttr: " + dlgClId_rep);
        PrimeFaces.current().ajax().update(dlgClientId);
    	
    }

    /**
     * Iz viewScope uklanja atribut za visible od p:dialog koga je tamo postavio DrugiBean.crudListener()
     * Naime, svaki crud dialog ima visible expression postavljen u 
     *         f:event listener="#{drugiBean.solveDlgVisible}" type="postAddToView"
     *  preciznije, u adjustVisibleForDialog(), u sledecoj formi:       
     *    "#{viewScope." + dlg.getClientId().replace(":", "_")  + "}"
     *  Onda, da bi dialog postao vidljiv, crudListener() postavi u viewScope takav atribut na true.
     *  Sada, da bi dialog postao nevidljiv, treba omoguciti da gornji expression evaluira u false,
     *  i to se resava ovde, tako sto se istomni atribut izbacuje iz viewScope  
     * @param dlgClientId
     */
    
    public static  void removePFDialogVisibleCondition(String dlgClientId) {
    	Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
    	String dlgClId_rep = dlgClientId.replace(":", "_");
    	viewMap.remove(dlgClId_rep);
    	System.out.println(" -iz viewScope izbacen atribut: " + dlgClientId.replace(":", "_"));
    	System.out.println("_ZATVOREN DIJALOG " + dlgClientId + ", IZBACEN viewAttr: " + dlgClId_rep);
    }
    
    
    public static UIComponent findComponentByClientId(String clientId) {
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        UIComponent result = view.findComponent(clientId);
        return result;
    }

    public static HttpSession getSession() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		return session;
	}
    
    public static void setSessionParameter(String key, Object value) {
		getSession().setAttribute(key, value);
	}
    
    public static Object getSessionParameter(String key) {
		return getSession().getAttribute(key);
	}
    
    public static void removeSessionParameter(String key) {
		getSession().removeAttribute(key);
	}
  
    public static String getSherlogSessionIDValue(){
		return (String)getSessionParameter("SHERLOG-SESSION-ID");
	}

}
