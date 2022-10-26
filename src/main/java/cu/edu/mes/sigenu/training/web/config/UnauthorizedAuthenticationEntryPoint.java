package cu.edu.mes.sigenu.training.web.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class UnauthorizedAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public final void commence(HttpServletRequest request, HttpServletResponse response,
                               AuthenticationException authException) throws IOException {
        
        response.sendRedirect("signin");
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
