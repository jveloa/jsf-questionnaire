package cu.edu.mes.sigenu.training.web.dto.security;

import java.util.List;

public class UserAuthenticatedDto {
	private String id;
	private String username;
	private String fullName;
	private String password;
	private String email;
	private String identification;
	private List<String> roles;
	private String token;
	private String refreshToken;
	
	public UserAuthenticatedDto() {
		super();
	}

	public UserAuthenticatedDto(String id, String username, String fullName, String password, String email, String identification,
			List<String> roles, String token, String refreshToken) {
		super();
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.password = password;
		this.email = email;
		this.identification = identification;
		this.roles = roles;
		this.token = token;
		this.refreshToken = refreshToken;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
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
