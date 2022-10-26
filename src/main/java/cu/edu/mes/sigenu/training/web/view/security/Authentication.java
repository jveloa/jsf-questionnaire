package cu.edu.mes.sigenu.training.web.view.security;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import cu.edu.mes.sigenu.training.web.dto.security.UserAuthenticatedDto;
import cu.edu.mes.sigenu.training.web.security.CurrentUserUtils;
import cu.edu.mes.sigenu.training.web.security.UserPrincipal;
import cu.edu.mes.sigenu.training.web.service.security.AuthService;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
public class Authentication implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	private String username;
	@Getter @Setter
	private String password;
	
	@Inject
	private AuthService authService;
	
	public String auth() {
		
		try {
			UserAuthenticatedDto userAuthenticated = authService.login(username, password);
			UserDetails userDetails = UserPrincipal.create(userAuthenticated);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch (Exception e) {
	        JsfUtils.addMessageFromBundle("securityMessages", FacesMessage.SEVERITY_INFO, "error_CredencialesInvalidas");
	        return null;
		}
		return dispatchToUrl("workspace");
	}
	
	public boolean ifAnyGranted(String roles) {
		return CurrentUserUtils.hasAnyAuthority(roles.split(","));
	}
	
	public String getFullname() {
		return CurrentUserUtils.getFullName();
	}
	
	public String logout() {
		return dispatchToUrl("/logout");
	}
	
	public String getUserLogued() {
		return CurrentUserUtils.getFullName();
	}
	
	private String dispatchToUrl(String url) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		try {
			//dispatcher.forward(request, response);
			response.sendRedirect(url);
			facesContext.responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return null;
	}

}
