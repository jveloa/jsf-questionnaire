package cu.edu.mes.sigenu.training.web.service.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cu.edu.mes.sigenu.training.web.dto.security.LoginRequestDto;
import cu.edu.mes.sigenu.training.web.dto.security.TokenRefreshRequestDto;
import cu.edu.mes.sigenu.training.web.dto.security.UserAuthenticatedDto;
import cu.edu.mes.sigenu.training.web.utils.ApiRestMapper;
import cu.edu.mes.sigenu.training.web.utils.RestService;


@Named
@ApplicationScoped
public class AuthServiceImpl implements AuthService {
	
	@Inject
	private RestService restService;

	@Override
	public UserAuthenticatedDto login(String username, String password) {
		UserAuthenticatedDto authenticatedDto = null;
		try {
		    ApiRestMapper<UserAuthenticatedDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.POST("/api/v1/identity/auth", new LoginRequestDto(username, password), String.class).getBody();
		    authenticatedDto = apiRestMapper.mapOne(response, UserAuthenticatedDto.class);
		} catch (Exception e) {
			authenticatedDto = null;
		}
		return authenticatedDto;
	}

	@Override
	public UserAuthenticatedDto resolveToken(String refreshToken) {
		UserAuthenticatedDto authenticatedDto = null;
		try {
		    ApiRestMapper<UserAuthenticatedDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.POST("/api/v1/identity/refresh-token", new TokenRefreshRequestDto(refreshToken), String.class).getBody();
		    authenticatedDto = apiRestMapper.mapOne(response, UserAuthenticatedDto.class);
		} catch (Exception e) {
			authenticatedDto = null;
		}
		return authenticatedDto;
	}
	
}
