package cu.edu.mes.sigenu.training.web.dto.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {


	private String username;
	private String password;

	public LoginRequestDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

}
