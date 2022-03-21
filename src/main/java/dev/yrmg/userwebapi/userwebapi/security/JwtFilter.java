package dev.yrmg.userwebapi.userwebapi.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import dev.yrmg.userwebapi.userwebapi.application.interfaces.IUserDetailService;

public class JwtFilter extends GenericFilterBean {    
    private IUserDetailService userDetailService;
    private static final String BEARER = "Bearer";

    public JwtFilter(IUserDetailService userDetailService){
        this.userDetailService = userDetailService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                String header = ((HttpServletRequest) request).getHeader("Authorization");

                if(header != null && header.startsWith("Bearer")){
                    userDetailService.getUserByJwtToken(getToken(header).get()).ifPresent(userDetails -> {
                        //Add the user details (Permissions) to the Context for just this API invocation
                        SecurityContextHolder.getContext().setAuthentication(
                                new PreAuthenticatedAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
                    });
                }
               
        
                //move on to the next filter in the chains
                chain.doFilter(request, response);
        
    }

    private Optional<String> getToken(String header){
        return Optional.of(header.replace(BEARER, "").trim());
    }
    
}
