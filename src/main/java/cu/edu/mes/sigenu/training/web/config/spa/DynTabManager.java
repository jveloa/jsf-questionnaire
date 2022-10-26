package cu.edu.mes.sigenu.training.web.config.spa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.PrimeFaces;
import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import cu.edu.mes.sigenu.training.web.utils.JsfUtils;



// @Named
// @javax.faces.view.ViewScoped
public class DynTabManager implements Serializable {
    
	
	private DynTabTracker tabTracker;
	
	public void setTabTracker(DynTabTracker tabTracker) {
		this.tabTracker = tabTracker;
		//System.out.println("setTabTracker(), value =  " + tabTracker);
	}
	
	public DynTabTracker getTabTracker() {
		return tabTracker;
	}
	
	public List<DynTab> getTabMenuModel(){
	     List<DynTab> tabList = tabTracker.getActiveTabList();
	     for (DynTab dt : tabList) {
	    	 //System.out.println("--dt id =  " + dt.getId());
	     }
	     //System.out.println("getTabMenuModel() vraca: " + tabList);
         return tabList; 
	}
	
	   public Map<String, DynTab> getTabMap()
	   {
	     return Collections.unmodifiableMap(tabTracker.getTabMap());
	   }

	
	 public String getSelectedTabId(){
		 
	    String result =  tabTracker.getSelectedTabId();
	   // System.out.println("getSelectedTabId() vraca: " + result);
	    return result;
	 }

	public DynTabManager() {
		super();
		// TODO Auto-generated constructor stub
		//System.out.println("DynTabManager constructor!");

	}
	
	private List<DynTab> initialTabs = new ArrayList<DynTab>();
    public void setInitialTabs(List<DynTab> initialTabs) {
      this.initialTabs = initialTabs;
    }

    public List<DynTab> getInitialTabs(){
      return initialTabs;
    }
    
    @PostConstruct
    public void init() {
      System.out.println("DynTabManager init() begin");  
      for (DynTab tab: initialTabs){
           // Cvele : originalno je ovde bio samo poziv addTab(tab)
          // ali umesto toga dodajem ovaj kod, koji obezbedjuje da TF na initial tabu dobije "dynTabManager"
          // parametar, to jest, instancu ove klase. Ako se dobije existingTab, to znaci da je tab vec dodat i to
          // u DynTabTracker.init() metodu, jer taj bean takodje _MORA da ima initialTabs zadat property.
          // Medjutim, tamo dodat tab nema dynTabManager instancu, i stoga bez ovog koda, sa initialTab ne moze da se
          // otvori neki drugi dyn tab
    	  
    	  
           DynTab existingTab = getMatchingTab(tab);

           if (existingTab != null){
            // System.out.println("   pronadjen takav tab (dodat u DynTabTracker.init()) : " + existingTab.getId());
             //System.out.println("   njemu sledi prosledjivanje dynTabManager propertija, da se sa inicijalnog taba omoguci otvaranje novog dynTab-a");
             //existingTab.getParameters().put("dynTabManager", this);
             setSelectedTabId(existingTab.getId());
           }else {
               //System.out.println(" nije existing tab, pozivam addOrSelectTab(), gde ce biti prosledjen dynTabManager parametar");
               addOrSelectTab(tab);
           }
           
      }// of for loop()
      
      System.out.println("DynTabManager init() end");  
    }
    /**
     * Ovo je ustvari 
     *    public void tabActivatedEvent(DisclosureEvent action)

     * @param event
     */
    public void onTabChange(TabChangeEvent event) {
        // FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        
        /* 
        UIComponent component = action.getComponent();
        String tabId = String.valueOf(component.getAttributes().get("tabId"));
        setSelectedTabId(tabId);
        */
    	String tabId = event.getTab().getId();
    	//System.out.println("onTabChange(), selektovan tabId = " + tabId);
    	setSelectedTabId(tabId);
    	PrimeFaces.current().ajax().update("mainGrowl"); 

    }
    private Integer activeTabIndex = 0;
    public Integer getActiveTabIndex() {
    	   return getIndexOfSelecedTab();
    }
    
    public void setActiveTabIndex(Integer activeTabIndex) {
      //  this.activeTabIndex = activeTabIndex;
    } 
       
    
    public void setSelectedTabId(String id){
      DynTab tab = getTab(id); // pomocu tabTracker, uzima iz mape DynTab sa tim Id
      if (tab == null) {
        throw new IllegalArgumentException("Tab: " + id + " je nepoznat!");
      }
      System.out.println("setSelectedTabId(), include = " + tab.getIncludePage());
      setSelectedTab(tab);
    }
    
    public void setSelectedTab(DynTab tab){
      tabTracker.setSelectedTabId(tab.getId());
      tab.setActive(true);
      //Cvele ovde fire dynTabSelected
      //refreshTabsAndContentArea();
      //updateDocumentTitle();
      fireCDIEvent(new DynTabCDIEvent("dynTabSelected", tab));
    }
    
    public DynTab getTab(String id) {
      return tabTracker.getTabMap().get(id);
    }
    
    /**
     * Stoji u template kao listener za ajax "tabClose" event, na p:tabView id="mainTabView"
     *  
     * Ovde treba iz viewScope izbaciti atribut koji se odnosi na vidljvost dijaloga, vidi komentar u DrugiBean.crudListener(), pri kraju metoda
     * To radi metoda hideDialogsRelatedToTab()    
     * @param event
     */
    public void onTabClose(TabCloseEvent event) {
    	System.out.println("onTabClose() begin");
    	Tab tab =  event.getTab();
    	// u masterLayout.xhtml se vidi da p:tab ima isti id kako DynTab ciji sadrzaj prikazuje
    	String tabId = tab.getId();
    	
    	//System.out.println("onTabClose(), zatvoren tabId = " + tabId +", pozivam removeTab(String id)");
    	removeTab(tabId);
    	// zatvoriti sve dijaloge koji su childovi od taba koji se zatvara:
    	//hideDialogsRelatedToTab(tab);
    	//hideDialogsRelatedToTab(tabId);
    	System.out.println("onTabClose() end");
    }
    /*
    private void hideDialogsRelatedToTab(Tab tab) {
    	// String jsCloseDlg = "PF('" + this.getDialogVar() + "').hide()";
    	// PrimeFaces.current().executeScript(jsCloseDlg);
    	System.out.println("hideDialogsRelatedToTab() call, tab = " + tab);
    	if (tab != null) {
	       FacesContext facesContext = FacesContext.getCurrentInstance();
	  	   VisitContext visitContext = VisitContext.createVisitContext(facesContext);
	  	   tab.visitTree(visitContext, new DialogsHideVisitCallback()); 
    	}
    }
    */
    private void hideDialogsRelatedToTab(DynTab tab) {
    	if (tab != null) {
    		tab.getDynTabVisibleMap().clear();
    		System.out.println("za DynTab: " + tab.getId() + " ispraznjena visible mapa!");
    	}
    }
    
    class DialogsHideVisitCallback implements VisitCallback {
    	/**
         * This method will be invoked on every node of the component tree.
         * 
         * VAZNO: PREMA
         *     https://forum.primefaces.org/viewtopic.php?f=3&t=60585
         * ispada da je    
         *    PrimeFaces.current().executeScript(jsCloseDlg);
         * jedini nacin da se zatvori p:dialog sa Java strane
         * Treba znati da ovakav nacin zatvaranja dijaloga _ne _okida handleDlgClose(), tj listener
         * za p:ajax event="close". Taj listener explicitno uklanja iz viewScope atribut koji odredjuje visible za
         * dijalog, pa to isto teba uciniti i ovde   
         */
    	@Override
    	public VisitResult visit(VisitContext vCtx, UIComponent target) {
    		//System.out.println("visit() call, target = " + target);
    		if (target instanceof Dialog ) {
    				// ako se zavrsava na cifru, to je dijalog tipa pBx:
    				Dialog dlg = (Dialog)target;
    				String dlgId = dlg.getId();
    				char last = dlgId.charAt(dlgId.length() - 1);
    				if (Character.isDigit(last)) {//ok, to je dijalog koga treba sakriti pomocu hide
    					JsfUtils.closePFDialog(dlg);
    				}
    		}// ako je dijalog koji pocinje sa pB
    		// descend into current subtree, if applicable
            return VisitResult.ACCEPT;
    	}// of visit() 
    }  	
    
    public void removeTab(String id){
      removeTab(id, false);
    }
    
    protected void removeTab(String id, boolean force) {
      DynTab tab = getTab(id); // ovo je DynTab instanca iz tabTracker.tabMap koja ima taj Id (r0, r1,.)
      if (tab == null) {
        throw new IllegalArgumentException("Tab: " + id + " ne postoji!");
      }
      removeTab(tab, force);
    }
    /**
     * Ovo se desava na gasenje <p:tab> na UI (p:ajax  onTabClose na template)
     * Posle ove metode, u tabTracker.tabList nema novih DynTab instanci, samo se premesta postojeca na kraj liste, i menjaju joj se osobine:
     *    UniqueIdentifier joj je null, active i acitaved postaju false
     * Takodje se broj aktivnih  (tabTracker.numActive) smanjuje za 1
     * 
     * 
     * Ako je uklonjen trenutno aktivni tab, postavlja se novi aktivni
     * @param tab DynTab instanca iz tabTracker.tabMap
     * @param force
     */
    protected void removeTab(DynTab tab, boolean force){
        // fix za "TabList state is corrupted!", prema:
        // https://community.oracle.com/thread/2580079
        // https://dehuaste.wordpress.com/2013/10/14/dynamic-tab-indexing-error/comment-page-1/
        if (tab.getUniqueIdentifier() == null) {
           // System.err.println("*fix za TabList state is corrupted, ne radim nista");  
            return;
        }
        if (!tab.isCloseable() && !force){
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tab ne moze da se zatvori ", null);
  	       FacesContext.getCurrentInstance().addMessage(null, message); 
          return;
        }
        
        hideDialogsRelatedToTab(tab);
        // Completely clear the tab.
       // tab.setTaskflowId(getEmptyTaskFlowId());
        // ali najore napraviti kopiju taba, da se posalje u CDI event:
        
        if (tab.getCdiBean() != null) {
        	tab.getCdiBean().callExitPointMethod();
        	System.out.println("cdi bean-u " + tab.getCdiBean() + " pozvan callExitPointMethod() ");
        }	
        
        DynTab removedTabCopy = new DynTab();
        removedTabCopy.setUniqueIdentifier(tab.getUniqueIdentifier());
        removedTabCopy.setId(tab.getId());
        removedTabCopy.setTitle(tab.getTitle());
        removedTabCopy.setCdiBean(tab.getCdiBean());
        // konacno ga pocistiti:
        tab.setIncludePage("WEB-INF/include/empty/empty.xhtml");
        tab.setParameters(null);
        tab.setUniqueIdentifier(null);
        tab.setTitle("");
        //tab.setIcon("");
        tab.setActive(false); // ne vidi se na UI - ovo je rendered property od p:tab - 
        tab.setActivated(false);  // nije vise aktivna radna povrsina
        // Reduce the numActive count.
        tabTracker.setNumActive(tabTracker.getNumActive() - 1);

        // Make sure that all active tabs are in the front of the list. This will  prevent fragmentation of the tablist, so this will make 
        // everything a lot easier.
        //Cvele: tabList sadrzi listu aktivnih tabova (vidljivih na ekranu, active = true) i neaktivnih (active = false)
        // izbaciti DynTab iz tablist PRI CEMU se svi elementi desno shiftuju za jedno mesto levo!
        // i onda dodati istu tu  instancu na kraj tabList liste
        List<DynTab> tabList = tabTracker.getTabList();
        int oldIndex = tabList.indexOf(tab);
        tabList.add(tabList.remove(oldIndex));
        // posle ovoga gore, ako je recimo ugasen p:tab id = "r1" (drugi p:tab na template), njegov DynTab je premesten na zadnje mesto u tabList
        
        // Determine the tab that should be should be currently open (in case the current tab was closed).
        // Ako je uklonjen trenutno aktivni tab, i ipak je preostalo jos vidljivh tabova, treba zapamtiti novi aktivni tab:
        if (tab.equals(getSelectedTab()) && tabTracker.getNumActive() > 0) {
        	  // kandidat za novi selektovani tab je onaj na indexu sa koga je uklonjen (1):
	          int newIndex = oldIndex;
	          if (newIndex >= tabTracker.getNumActive()) {// ovo znaci da je uklonjen tab koji je bio trenutno aktivan, i bio je  zadnji
	        	 // index novog treba da bude broj aktivnih tabova minus 1 (naime, sad treba da bude aktivan prethodno zadnji:) 
  	             newIndex = tabTracker.getNumActive()-1;
	          }
	          setSelectedTab(tabList.get(newIndex));
        }
        
        
       // System.err.println("removeTab(), uklonjen tab " + tab.getId());
       // refreshTabsAndContentArea();
        // ovo treba, sklanjam samo za test:
        PrimeFaces.current().ajax().update("mainForm:mainTabView");
       // ------------ resetCdiBeanWithName(cdiBeanName);
        if (tab.getCdiBean() != null) {
        	tab.getCdiBean().init();
        	tab.getCdiBean().setActive(false);
        	System.out.println("deaktiviran bean " + tab.getCdiBean() + " da vise ne reaguje na CDI  evente");
        	tab.getCdiBean().setDynTab(null);
        }	
        tab.setCdiBean(null);

        fireCDIEvent(new DynTabCDIEvent("dynTabRemoved", removedTabCopy));
      }// of removeTab()
    
    
    
    public DynTab getSelectedTab()    {
      String selectedTabId = getSelectedTabId();
      if (selectedTabId != null) {
        return getTab(selectedTabId);
      }
      return null;
    }
    
    public int getIndexOfSelecedTab() {
        List<DynTab> tabList = tabTracker.getActiveTabList();
        return tabList.indexOf(getSelectedTab());
    }

    public static DynTabManager getCurrentInstance() {
      // viewScope might not be available yet, which results in NPE when evaluating.
      // In this case, we just return null
      try       {
        DynTabManager context =
          (DynTabManager) JsfUtils.getExpressionValue("#{viewScope.dynTabManager}");
        if (context == null)
        {
          context =
              (DynTabManager) JsfUtils.getExpressionValue("#{pageFlowScope.dynTabManager}");
        }
        return context;
      }
      catch (Exception e)
      {
        // do nothing
      }
      return null;
    }
    

    /**
     * This method is called to open a new dynamic tab, or select the existing tab for the same uniqueIdentifier when
     * checkTabExists property is true. 
     * This method receives a tabName argument, this value is suffixed with "DynTab" to lookup a managed bean in request
     * scope of type DynTab. For example, if the tabName passed in is "Jobs", there should be a managed bean named
     * "JobsDynTab" defined. This managed bean holds the information needed to launch the tab: taskFlowId, parameters,
     * tab title, whether the tab is closeable, etc.
     * 
     * tabName - ovo je deo od <managed-bean-name>DrugiDynTab</managed-bean-name> ALI bez DynTab
     */
    public void launchTab(String tabName)     {
      // ovo je managed bean (iz faces-confix.xml) koji se zove po paternu:
      // tabName + "DynTab"	
      DynTab tab = DynTab.getInstance(tabName);   
      addOrSelectTab(tab);
      
    }
    /**
     * Ako u listi aktivnih tablova (deo od tabTracker.tablist) postoji DynTab ciji je UniqueIdentifier isti kao UID od prosledjenog DynTab
     * metod uzima i vraca tu instancu. Inace vraca null
     * @param compareTab
     * @return
     */
    public DynTab getMatchingTab(DynTab compareTab) {
    	System.out.println("getMatchingTab(), include = " + compareTab.getIncludePage() + ", uniqueId = " + compareTab.getUniqueIdentifier());
    	/*
    	System.out.println("getMatchingTab() call, compareTab = " + compareTab);
    	System.out.println(" ---compareTab. name = " + compareTab.getName()); // name se postavlja u DynTab.getInstance() i isto je sto i UniqueIdentifier
    	System.out.println(" --compareTab. uniqueId = " + compareTab.getUniqueIdentifier());
    	System.out.println(" --compareTab. includePage = " + compareTab.getIncludePage());
    	*/
      	// nastavi sa:
        if (compareTab.getUniqueIdentifier() == null) { // moglo je da postane null ako se zatvorio tab, tj, na removeTab
         // return getFirstTabWithTaskFlowId(compareTab.getTaskFlowIdString());
        	return getFirstTabWithIncludePage(compareTab.getIncludePage());
        }
      
        for (DynTab tab: tabTracker.getActiveTabList()) {
	        //System.out.println(" --tab = " + tab);
	        //System.out.println(" --unique id = " + tab.getUniqueIdentifier());  
	        if (compareTab.getUniqueIdentifier().equals(tab.getUniqueIdentifier())){
	          return tab;
	        }
        }
      return null;
    }
    
    /**
     * U spisku akitvnih tabova, pronalai tab koji ima zadati izludePage 
     * @param taskflowId
     * @return
     */
    public DynTab getFirstTabWithIncludePage(String includePage) {
      for (DynTab tab: tabTracker.getActiveTabList()){
        if (tab.getIncludePage().equals(includePage)) {
          return tab;
        }
      }
      return null;
    }




    public void addOrSelectTab(DynTab tab)     {
      System.out.println("addOrSelectTab: " + tab.getIncludePage());

      DynTab existingTab = getMatchingTab(tab);

      if (existingTab != null) {
        System.out.println("Matching existing tab found, select it");
        setSelectedTabId(existingTab.getId());
      }
      else {
        //System.out.println("No matching existing tab found, add new tab");
        addTab(tab);
      }
      //refreshUITabs();
     PrimeFaces.current().ajax().update("mainForm:mainTabView");
     // za ovaj event postoji observeDynTabEvent() u DrugiBean recimo, koji treba da spreci osvezavanje LazyModela
     // koje se desava kao posledica gornjeg poziva
    // fireCDIEvent(new DynTabCDIEvent("dynTabAdded", tab));
     
    }
    
    private void fireCDIEvent(DynTabCDIEvent cdiEvent) {
    	if (cdiEvent != null)
    	   System.out.println("fireCDIEvent(), _Event:  " + cdiEvent.getEventType());
        BeanManager bm = CDI.current().getBeanManager();
        bm.fireEvent(cdiEvent);
    }

    public void addTab(DynTab tabToAdd) {
        System.out.println("DynTabManager.addTab() begin");
        if (tabToAdd != null)
            System.out.println("   dodaje se tab " + tabToAdd.getName() + ", title = " + tabToAdd.getTitle());
        
      if (tabTracker.getNumActive() >= tabTracker.getMaxNumberOfTabs()) {
        // handleTooManyTabsOpen();
      }
      else {
        // The list of tabs doesn't get fragmented, so the first inactive tab always
        // shows as the right most tab.
        DynTab tab = getFirstInactiveTabOrThrow(); // tab je DynTab, i to _NIJE managed-bean iz faces-config, nego instanca iz DynTabTracker liste (koja se prikazuje u template na UI), njegov Id je oblika r1, r2,...
        tab.setTitle(tabToAdd.getTitle());
        //tab.setIcon(tabToAdd.getIcon());  
        tab.setActive(true);
        //tab.setTaskflowId(tabToAdd.getTaskflowId());
        tab.setIncludePage(tabToAdd.getIncludePage());
        tab.setCdiBean(tabToAdd.getCdiBean()); // znaci, tab _NIJE managed bean, nego instanca iz DynTab iz DynTabTracker liste koja se prikazuje na UI u tepmlate
        tab.setUniqueIdentifier(tabToAdd.getUniqueIdentifier());
        
        System.out.println("tab id = " + tab.getId());
        tab.setParameters(tabToAdd.getParameters());
        
        if (tab.getCdiBean() != null)
        	tab.getCdiBean().setDynTab(tab);
        // cdiBean-u proslediti Id taba:
        

        
        // proslediti parametre u CDI bean, stim da klasa treba da implementira InitInterface:
        // 29.09.2020: od sada se parametri prosledjuju u DynTab.setParameters(), pa ovo komentarisem:
        // sendParamsToCdiBean(tab, cdiBeanName, tabToAdd.getParameters());
        
        // vreme je da sepozove firstActivity() iz CDI bean-a koji treba da odradi neki posao, sto je ekvivalent za ADF default acivity:
        if (tab.getCdiBean() != null)
        	tab.getCdiBean().callAccessPointMethod();
        
        tabTracker.setNumActive(tabTracker.getNumActive() + 1);
        fireCDIEvent(new DynTabCDIEvent("dynTabAdded", tab));
        setSelectedTabId(tab.getId());
       
        // aktivirati Cdi bean, tako da reaguje na CDI evente:
        if (tab.getCdiBean() != null)
        	  tab.getCdiBean().setActive(true);
              System.out.println("aktiviran Cdi bean " + tab.getCdiBean() + " da reaguje na Cdi evente");
      }
      System.out.println("DynTabManager.addTab() end");
    }
    
    

    private DynTab getFirstInactiveTabOrThrow()
    {
      // We can do this, because we always make sure that all active tabs are in the
      // front of the list.
      System.out.print("trackerNumAc  = " + tabTracker.getNumActive());
      DynTab tab = tabTracker.getTabList().get(tabTracker.getNumActive());
      System.out.print("getOrThrow(), tab = " + tab.getUniqueIdentifier());
      if (!tab.isActive()){
        return tab;
      }

      throw new IllegalStateException("TabList state lista je neispravna (prvi neaktivan DynTab i spisku je i dalje Active!)!");
    }


     // --- zatvaranje tabova
    
    public void removeCurrentTab(ActionEvent e){
    	// zatvara se preko javaScript, koji je izazvati izvrsenje onTabClose listenera
    	// (za ajax 'tabClose' event), to je onTabClose() metod odavde, koji ce efektivno zatvoriti
    	// i DynTab. Patern javaScript fukcije za zatvaranje tab-a je sledeci:
    	//     PF('mainTabView').remove(1);
    	//   mainTabView je widgetVar za p:tabView iz template
    	
    	// jedino sto treba odrediti index tekuceg tab-a, pa sastaviti i pozvati ovu JS funkciju
    	// OVDE NE TREBA POZIVATI removeTab(tab.getId());, jer se poziva implicitno, preko onTabClose(), koji 
    	// ce se okinuti zbog ovoga
    	
        DynTabManager manager = DynTabManager.getCurrentInstance();
        if (manager == null) {
              // log.warn("DynTabManager je null, niste se ne radi");
            return;
        }
        
        if (manager.getSelectedTab().isCloseable()){
        	String jsCode = "PF('mainTabView').remove(";
        	String selTabId = this.getSelectedTabId();
        	List<DynTab> mModel = this.getTabMenuModel();
        	int i = -1;
        	for (DynTab tab : mModel) {
        		i++;
                if (tab.getId().equalsIgnoreCase(selTabId)) {
                	jsCode = jsCode + i + ");";
                	PrimeFaces.current().executeScript(jsCode);
                	break;
                }	
        	}    	
        }// ako se tab moze zatvoriti 
     }
    

    public void closeAllActiveTabs(ActionEvent e){
        //debug("closeAllActiveTabs() begin");
    	// napraviti kopiju menu modela:
    	List<DynTab> mCopy = new ArrayList<DynTab>(getTabMenuModel());
    	
    	// ===
    	String jsCode = "PF('mainTabView').remove(";
    	int idx;
    	while ((idx = getCloseableTabIndex(mCopy)) != -1) {
			PrimeFaces.current().executeScript(jsCode + idx + ");");
            mCopy.remove(idx);
    	}
    }// of closeAllActiveTabs
    
    private int getCloseableTabIndex(List<DynTab> mCopy) {
    	System.out.println("getCloseableTabIndex() begin ");
    	int result = -1;
    	int i = -1;
    	for (DynTab tab : mCopy) {
    		i++;
    		if (tab.isCloseable()){
    		  result = i;
    		  break;
    		}  
    	}
    	System.out.println("getCloseableTabIndex() vraca: " + result);
    	return result;
    }
    
     
    public List<DynTab> getActiveTabList()
    {
      return Collections.unmodifiableList(tabTracker.getActiveTabList());
    }

    public void removeCurrentTab(boolean ignorePendingChanges)    {
        removeTab(getSelectedTabId(), ignorePendingChanges);
      }



}
