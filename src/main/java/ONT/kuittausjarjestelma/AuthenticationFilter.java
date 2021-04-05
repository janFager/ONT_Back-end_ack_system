package ONT.kuittausjarjestelma;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import ONT.kuittausjarjestelma.web.AuthenticationService;

public class AuthenticationFilter extends GenericFilterBean {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
	
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest)request);
    LOGGER.info(authentication + "authentication from AuthenticationFilter");
    SecurityContextHolder.getContext().
        setAuthentication(authentication);
    filterChain.doFilter(request, response);
    LOGGER.info(request + " :request, response: " +response);
  }
}