package cu.edu.mes.sigenu.training.web.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;



public class CurrentUserUtils {
	
	public CurrentUserUtils() {
		super();
	}

	private static final String ANONYMOUS_USER = "anonymousUser";
	
	public static String getUsername() {
		String username = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			username = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		}
		return username;
	}
	
	public static String getEmail() {
		String email = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			email = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
		}
		return email;
	}
	
	public static String getTokenBearer() {
		String token = null;
		if (isLogged()) {
			token = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getToken();
		}
		return token;
	}
	
	public static String getRefreshTokenBearer() {
		String token = null;
		if (isLogged()) {
			token = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRefreshToken();
		}
		return token;
	}
	
	public static String getFullName() {
		String fullName = "Invitado";
		if (isLogged()) {
			fullName = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getFullName();
		}
		return fullName;
	}
	
	public static String getUserId() {
		String userId = null;
		if (isLogged()) {
			userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
		}
		return userId;
	}
	
	public static boolean isLogged() {
		boolean logged = false;
		if ((SecurityContextHolder.getContext().getAuthentication() != null) && (!getUsername().equals(ANONYMOUS_USER))) {
			logged = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		}
		return logged;
	}
	
	public static String getPassword() {
		String password = null;
		if (isLogged()) {
			password = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
		}
		return password;
	}
	
	public static boolean hasAnyAuthority(String ...authorities) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<String> listAuth = Arrays.asList(authorities);
		List<GrantedAuthority> auths = (List<GrantedAuthority>) auth.getAuthorities();

	    for (GrantedAuthority grantedAuthority : auths) {
	        if (listAuth.stream().anyMatch(authority -> authority.equals(grantedAuthority.getAuthority()))) {
	            return true;
	        }
	    }
		return false;
	}
	
}
