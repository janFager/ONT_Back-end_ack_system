package ONT.kuittausjarjestelma.web;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.util.Collections.emptyList;

public class AuthenticationService {
  private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
  static final long EXPIRATIONTIME = 864_000_00; // 1 day in milliseconds
  static final String SIGNINGKEY = "SecretKeyForAckSystemKuittausjarjestelmaBussiyritykselleSecretKeyForAckSystemKuittausjarjestelmaBussiyritykselle";
  static final String PREFIX = "Bearer";
  static final String HEADER_STRING = "Authorization";

  // Add token to Authorization header
  static public void addToken(HttpServletResponse res, String username) {
	 LOGGER.info(username + " username from AuthenticationService");
    String JwtToken = Jwts.builder().setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() 
            + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SIGNINGKEY)
        .compact();
    res.addHeader(HEADER_STRING, PREFIX + " " + JwtToken);
  res.addHeader("Access-Control-Expose-Headers", "Authorization");
  }

  // Get token from Authorization header
  static public Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    LOGGER.info(token + " THIS IS TOKEN");
    LOGGER.info(PREFIX+" :PREFIX");
    if (token != null) {
    	try {
      String user = Jwts.parser()
    		  .setSigningKey(SIGNINGKEY)
    		  .parseClaimsJws(token)
    		  .getBody()
    		  .getSubject();
      
      LOGGER.info(user);
      if (user != null) 
    	  LOGGER.info(user);
        return new UsernamePasswordAuthenticationToken(user, null, emptyList());
    	} catch (Exception e) {
    		System.out.println("this error " + e);
    		LOGGER.info(e + " error");
    	}
    	
    }
    return null;
  }
}
