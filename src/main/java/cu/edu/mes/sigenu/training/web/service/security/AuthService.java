package cu.edu.mes.sigenu.training.web.service.security;

import cu.edu.mes.sigenu.training.web.dto.security.UserAuthenticatedDto;

public interface AuthService {
	
	UserAuthenticatedDto login(String username, String password);
	UserAuthenticatedDto resolveToken(String refreshToken);
	
}
