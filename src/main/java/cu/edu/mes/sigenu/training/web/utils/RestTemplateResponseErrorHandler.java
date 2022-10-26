package cu.edu.mes.sigenu.training.web.utils;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.faces.context.FacesContext;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

import cu.edu.mes.sigenu.training.web.dto.security.TokenRefreshRequestDto;
import cu.edu.mes.sigenu.training.web.dto.security.UserAuthenticatedDto;
import cu.edu.mes.sigenu.training.web.security.CurrentUserUtils;
import cu.edu.mes.sigenu.training.web.security.UserPrincipal;


public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
	
    @Override
    public boolean hasError(ClientHttpResponse response)
            throws IOException {
        return (
                response.getStatusCode().series() == CLIENT_ERROR
                        || response.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response)
            throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        switch (statusCode.series()) {
            case CLIENT_ERROR:
                errorResponse(response);
            case SERVER_ERROR:
                throw new HttpServerErrorException(statusCode, response.getStatusText(),
                        response.getHeaders(), convertToByteArray(response.getBody()), Charset.forName("UTF-8"));
            default:
                throw new RestClientException("Unknown status code [" + statusCode + "]");
        }
    }
    
    private byte[] convertToByteArray(InputStream stream) throws IOException {
    	InputStream initialStream = stream;

        byte[] targetArray = new byte[initialStream.available()];
        initialStream.read(targetArray);
        return targetArray;
    }
    
    private void errorResponse(ClientHttpResponse response) throws IOException{
        String errorMessage = response.getHeaders().containsKey("error") ? response.getHeaders().get("error").get(0) : response.getStatusText();
        switch(response.getStatusCode().value()){
            case 400:
                throw new BadRequestException(errorMessage);
            
            case 401:
            	refreshToken(response);
            case 403:
            	FacesContext.getCurrentInstance().getExternalContext().redirect("signin");
            /*case 404:
                throw new ResourceNotFoundException(errorMessage);*/
            
            default:
                return;
        }
    }
    
    
    private void refreshToken(ClientHttpResponse resp) {
    	
    	try {
    		RestService restService = new RestService();
    		String refreshToken = CurrentUserUtils.getRefreshTokenBearer();
    		ApiRestMapper<UserAuthenticatedDto> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.POST("/api/v1/identity/auth/refresh-token", new TokenRefreshRequestDto(refreshToken), String.class, refreshToken).getBody();
		    UserAuthenticatedDto userAuthenticated = apiRestMapper.mapOne(response, UserAuthenticatedDto.class);
			UserDetails userDetails = UserPrincipal.create(userAuthenticated);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch (Exception e) {
			e.printStackTrace();
	        
		}
    }

    
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    private class UnauthorizedException extends RuntimeException
    {
        private static final long serialVersionUID = -6252766749487342137L;
        public UnauthorizedException(String message) {
            super(message);
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private class BadRequestException extends RuntimeException
    {
        private static final long serialVersionUID = -6252766749487342137L;
        public BadRequestException(String message) {
            super(message);
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private class ResourceNotFoundException extends RuntimeException
    {
        private static final long serialVersionUID = -6252766749487342137L;
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }


}
