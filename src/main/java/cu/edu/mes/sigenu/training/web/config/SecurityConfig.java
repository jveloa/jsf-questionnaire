package cu.edu.mes.sigenu.training.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // todas las solicitudes deben estar autenticadas excepto las que se definan en este code
        http.authorizeRequests().antMatchers("/javax.faces.resource/**", "/resources/**", "/signin", "/error-**")
        .permitAll()
        .antMatchers("/welcome").hasAnyAuthority("SECRETARY", "GENERAL_SECRETARY", "MATRICULATOR", "SP_CONTROLLER", "ASSISTANT_STUDENTS_CONTROLLER", "SECRETARY_PARTIAL")
        .antMatchers("/change-password").hasAnyAuthority("SECRETARY", "GENERAL_SECRETARY", "MATRICULATOR", "SP_CONTROLLER", "ASSISTANT_STUDENTS_CONTROLLER", "SECRETARY_PARTIAL")
        .antMatchers("/error-**").hasAnyAuthority("ANONYMOUS", "SECRETARY", "GENERAL_SECRETARY", "MATRICULATOR", "SP_CONTROLLER", "ASSISTANT_STUDENTS_CONTROLLER", "SECRETARY_PARTIAL")
        .anyRequest().authenticated();
        
        
        // configurando el login
        http
        .exceptionHandling().authenticationEntryPoint(unauthorizedAuthenticationEntryPoint()).accessDeniedPage("/error-access-denied");
        
        
        // logout, cuando se ejecute el logout va para el login
        http.logout().logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/login");
        
        // not needed as JSF 2.2 is implicitly protected against CSRF
        http.csrf().disable();
        
       
    }
    
    @Bean
    public UnauthorizedAuthenticationEntryPoint unauthorizedAuthenticationEntryPoint() {
        return new UnauthorizedAuthenticationEntryPoint();
    }
    
}