package dev.yrmg.userwebapi.userwebapi.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import dev.yrmg.userwebapi.userwebapi.application.interfaces.IUserDetailService;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtFilter extends GenericFilterBean {    
    private IUserDetailService userDetailService;
    private static final String BEARER = "Bearer";

    public JwtFilter(IUserDetailService userDetailService){
        this.userDetailService = userDetailService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                String header = ((HttpServletRequest) request).getHeader(AUTHORIZATION);

                if(header != null && header.startsWith("Bearer")){
                    Optional<String> token = getToken(header);
                    Optional<UserDetails> UserDetailsOptional = Optional.empty();
                    if(token.isPresent()){
                        UserDetailsOptional = userDetailService.getUserByJwtToken(token.get());
                        if(UserDetailsOptional.isPresent()) {                        
                            UserDetails userDetails = UserDetailsOptional.get();
                            SecurityContextHolder.getContext().setAuthentication(
                                    new PreAuthenticatedAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
                        }
                    }
                }
               
        
                //move on to the next filter in the chains
                chain.doFilter(request, response);
        
    }

    private Optional<String> getToken(String header){
        return Optional.of(header.replace(BEARER, "").trim());
    }
    
}
