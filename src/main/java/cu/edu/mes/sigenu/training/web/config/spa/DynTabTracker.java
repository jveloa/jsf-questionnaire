package cu.edu.mes.sigenu.training.web.config.spa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

// @Named
// @javax.faces.view.ViewScoped
public class DynTabTracker implements Serializable {
	private int maxNumberOfTabs = 15;
    /**
     * Sets the maximum number of tabs that the user is allowed to open.
     * <p>
     * This can never be more than <code>{@link #maxNumberOfTabsDefined}</code>.
     *
     * @param maxNumberOfTabs the new maximum number of tabs that the user is allowed to open
     */
      public void setMaxNumberOfTabs(Integer maxNumberOfTabs) {
	      if (maxNumberOfTabs > getNumberOfTabsDefined()) {
	        throw new IllegalArgumentException("maxNumberOfTabs may never exceed " +
	                                           getNumberOfTabsDefined());
	      }
          this.maxNumberOfTabs = maxNumberOfTabs;
      }

	  public Integer getMaxNumberOfTabs() {
	    return maxNumberOfTabs;
	  }


    private List<DynTab> initialTabs = new ArrayList<DynTab>();
    public void setInitialTabs(List<DynTab> initialTabs) {
       System.out.println("Tracker setInitialTabs(), value =  " + initialTabs);    	
       this.initialTabs = initialTabs;
       
       // init();
    }

    public List<DynTab> getInitialTabs() {
       return initialTabs;
    }


    /**
     * The number of tabs that are currently used (visible on the screen). This is
     * always equal to the number of tabs in <code>tabList</code> that are active
     * (<code>tab.isActive()</code>).
     * 
     * Cvele: broj tabova koji se trenutno koriste (=vidljivi su na ekranu)
     * Ovaj broj je uvek jednak broju DynTab instanci u tabList za koje je active = true
     */
    private int numActive = 0;
    public void setNumActive(int numActive) {
    	this.numActive  = numActive;
   	}
    
    public int getNumActive() {
    	return numActive;
    }
    
    /**
     * The <code>tabMap</code> contains all tabs based on their <code>id</code>.
     */
    private final Map<String, DynTab> tabMap = new HashMap<String, DynTab>();
    /**
     * Returns the map containing all tabs based on their <code>id</code>s.
     *Cvele: koristi se za rendered property za p:tab koji je u template
     * 
     *   p:tab id = "r0" rendered="#{dynTabManager.tabMap['r0'].active
     * @return the map containing all tabs based on their ids
     */
    public Map<String, DynTab> getTabMap()
    {
      return tabMap;
    }
    /**
     * ovoliko ima p:tab komponenti u  dyntab_test.xhtml , tj, u template
     */

    private int numberOfTabsDefined = 10; // 

    public void setNumberOfTabsDefined(int numberOfTabsDefined)
    {
      this.numberOfTabsDefined = numberOfTabsDefined;
    }

    public int getNumberOfTabsDefined(){
      return numberOfTabsDefined;
    }
    
    /**
     * The list of tabs that defines the order of the tabs on the screen. All
     * tabs are in this list at all times.
     * <ul>
     * <li><code>tabList[0..numActive)</code> contains the active (used) tabs
     * <li><code>tabList[numActive..n)</code> contains the inactive (unused) tabs, where n =
     * the size of tabList.
     * </ul>
     */
    private final List<DynTab> tabList = new ArrayList<DynTab>();



	
	  public DynTabTracker() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("DynTabTracker constructor!");
	}

	  @PostConstruct
	  public void init() {
		System.out.println("Tracker init(), initialTabs = " + initialTabs);
	    for (DynTab tab : initialTabs){
	      tab.setId(createId(numActive));
	      tab.setActive(true);
	      if (numActive==0)	      {
	        setSelectedTabId(tab.getId());
	      }
	      tabList.add(tab);
	      tabMap.put(tab.getId(), tab);
	      numActive++;
	      
	        // aktivirati Cdi bean, tako da reaguje na CDI evente:
	        if (tab.getCdiBean() != null) {
	        	  tab.getCdiBean().setActive(true);
	              System.out.println("aktiviran Cdi bean " + tab.getCdiBean() + " da reaguje na Cdi evente");
	              // ovo se inace radi u DynTabManager.addTab(), a za inicijalne tabove, treba uraditi ovde:
        		  tab.getCdiBean().setDynTab(tab);	              
        		  System.out.println("Cdi beanu " + tab.getCdiBean() + " dodat DynTab: " + tab);
	        }
	    }
        // posle gornje petlje, u tabList i tabMap su inicijalne DynTab isntance navedene u faces-config.xml, i one imaju svoje:
	    // -uniqueIdentifier 
	    // -icludePage,
	    // -Id (r0, r1,...) postavljen gore 
	    // -active = true (zato sto se inicijalno prikazuju po otvaranju app) postavljen gore
	    
	    // Create other "placeholder"tabs    
	    for (int i = initialTabs.size(); i < getNumberOfTabsDefined(); i++){
	        DynTab tab = new DynTab(createId(i),"WEB-INF/include/empty/empty.xhtml" ); // drugi param je includePage, sem toga, isActive je false
	        tabList.add(tab);
	        tabMap.put(tab.getId(), tab);
	    }    
	    // posle ove gore petlje, tabList i tabMap imaju i prazne DynTab instance: 
	    // imaju 
        // -Id (r2, r3,...) postavljen gore
	    // -incudePage je empty (pitanje da li ovo uopste treba?), 
	    // -active = false (u contructoru)
	    // -uniqueIdentifier = null

	      // Make sure that the shown tab is activated.
	      String id = getSelectedTabId();
	      if (id != null)	      {
	        tabMap.get(id).setActivated(true);
	      }
	      // posle ovoga gore, samo jedna DynTab iz tabMap ima activated = true, to je ona radna povrsina koja je aktivna (sa kojom korisnik radi)
	      System.out.println("DynTabTracker init() end");  
	  }// of init()
	  
	    /**
	     * Pravi Id za DynTab, po sablonu: 
	     *     'r' + n
	     * to su istovremeno i Id od p:tab na template, u kojima treba da se otvaraju radne povrsine    
	     * @param n
	     * @return
	     */
	   protected String createId(int n) {
	      return "r" + n;
	   }
	   
	    /**
	     * The id of the tab that is currently selected / open.
	     */
	    private String selectedTabId = null;


	    public void setSelectedTabId(String selectedTabId) {
	        this.selectedTabId = selectedTabId;
	    }

	    public String getSelectedTabId() {
	        return selectedTabId;
	    }
	   




	 public List<DynTab>  getTabList(){
        return tabList;    		 
	 }
	 
	    /**
	     * Returns the list of tabs that are currently active on screen. This list is
	     * modifiable and can be used to change the order of the tabs on the screen. This
	     * list is backed by <code>{@link #getTabList}</code>, so make sure not to modify
	     * both structurally at the same time.
	     *
	     * @return the list of tabs that are currently active on screen
	     * @see List#subList
	     */
    public List<DynTab> getActiveTabList()  {
	      return getTabList().subList(0, getNumActive());
	}
	 
}
