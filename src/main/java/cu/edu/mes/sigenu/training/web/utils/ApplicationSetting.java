package cu.edu.mes.sigenu.training.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;



public class ApplicationSetting {
    
	private static String username;
	private static String password;
	
	public ApplicationSetting(String username, String password) {
		this.username = username;
		this.password = password;
	}


	private static Hashtable environment = new Hashtable();
    
    private static Properties properties = new Properties();

    public static Hashtable getEnvironment() {
    	LoadConfiguration();
    	environment.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.LoginInitialContextFactory");
        //environment.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        environment.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        environment.put(Context.PROVIDER_URL, "jnp://" + properties.getProperty("appserver"));
        environment.put(Context.SECURITY_PRINCIPAL, getUsername());
        environment.put(Context.SECURITY_CREDENTIALS, getPassword());
        environment.put("jnp.multi-threaded", "true");
        return environment;
    }
    
    public static Hashtable loadEnvironmet(String username, String password){
    	setUsername(username);
    	setPassword(password);
    	return getEnvironment();
    }

    private static void LoadConfiguration(){
        try {
        	String path = new File(System.getProperty("catalina.base"))+ "/conf/config.properties";
            properties.load(new FileInputStream(path));
        } catch (IOException ex) {
        }
    }

    
    public static String getProperty(String key) {
    	return properties.getProperty(key);
    }

	public static String getUsername() {
		return username; 
	}

	public static void setUsername(String username) {
		ApplicationSetting.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		ApplicationSetting.password = password;
	}
	
	//MIGUEL ALEJANDRO
	public static Properties loadJdbcPropertiesFile(){
		try {
			String path = new File(System.getProperty("catalina.base"))+ "/conf/jdbc.properties";
			properties.load(new FileInputStream(path));
		} catch (IOException ex) {
		}
		return properties;
	}
	
    
}
