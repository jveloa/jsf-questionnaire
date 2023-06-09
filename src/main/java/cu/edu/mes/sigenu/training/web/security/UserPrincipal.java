package cu.edu.mes.sigenu.training.web.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cu.edu.mes.sigenu.training.web.dto.security.UserAuthenticatedDto;

public class UserPrincipal implements UserDetails {
    private String userId;
    private String email;
    private String password;
    private boolean active;
    private String username;
    private String fullName;
    private String identification;
    private String token;
    private String refreshToken;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String userId, String email, String password, boolean active, String identification, Collection<? extends GrantedAuthority> authorities, String token, String username, String fullName, String refreshToken) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.active = active;
        this.authorities = authorities;
        this.identification = identification;
        this.token = token;
        this.username = username;
        this.fullName = fullName;
        this.refreshToken = refreshToken;
    }

    public static UserPrincipal create(UserAuthenticatedDto user) {
    	List<GrantedAuthority> authorities;
    	try {
    		authorities = AuthorityUtils.createAuthorityList(user.getRoles().toArray(new String[0]));
		} catch (Exception e) {
			authorities = Collections.
	                singletonList(new SimpleGrantedAuthority("ROLE_USER"));
			
		}
    	return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                true,
                user.getIdentification(),
                authorities,
                user.getToken(),
                user.getUsername(),
                user.getFullName(),
                user.getRefreshToken()
        );
    }

    public static UserPrincipal create(UserAuthenticatedDto user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        return userPrincipal;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getFullName() {
        return fullName;
    }

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
