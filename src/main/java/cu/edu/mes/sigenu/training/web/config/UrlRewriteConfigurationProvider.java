package cu.edu.mes.sigenu.training.web.config;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;



@RewriteConfiguration
public class UrlRewriteConfigurationProvider extends HttpConfigurationProvider{

	@Override
	public Configuration getConfiguration(ServletContext context) {
		return ConfigurationBuilder.begin()
				//Generals
				.addRule(Join.path("/workspace").to("/pages/content.xhtml"))
				//login page
				.addRule(Join.path("/signin").to("/pages/security/login.xhtml"))
				//welcome page
				.addRule(Join.path("/welcome").to("/pages/welcome/welcome.xhtml"))
				
				//change-password
				.addRule(Join.path("/change-password").to("/pages/security/changePassword.xhtml"))
				//error-page
				.addRule(Join.path("/error-fatal").to("/pages/error/error.xhtml"))
				//view-expired-page
				.addRule(Join.path("/error-view-expired").to("/pages/error/viewExpired.xhtml"))
				//not-found-page
				.addRule(Join.path("/error-not-found").to("/pages/error/notFound.xhtml"))
				//access denied page
				.addRule(Join.path("/error-access-denied").to("/pages/error/accessDenied.xhtml"))
				
				

				
				
				
				;
				
				
				
				
				
				


		//ej pasando parametros            
		//.addRule(Join.path("/shop/{category}").to("/faces/viewCategory.xhtml"));
	}

	@Override
	public int priority() {
		return 0;
	}

}
