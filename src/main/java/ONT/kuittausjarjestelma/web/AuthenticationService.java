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
  static final long EXPIRATIONTIME = 864_000_00; // 1 day in milliseconds
  static final String SIGNINGKEY = "U2VjcmV0S2V5S3VpdHRhdXNqYXJqZXN0ZWxtYQ==";
  static final String PREFIX = "Bearer";
  
  static final String HEADER_STRING = "Authorization";

  // Add token to Authorization header
  static public void addToken(HttpServletResponse res, String username) {
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
    if (token != null) {
    	try {
      String user = Jwts.parser()
    		  .setSigningKey(SIGNINGKEY)
    		  .parseClaimsJws(token)
    		  .getBody()
    		  .getSubject();
      

      if (user != null) 
        return new UsernamePasswordAuthenticationToken(user, null, emptyList());
    	} catch (Exception e) {
    		System.out.println("this error " + e);
    	}
    	
    }
    return null;
  }
}
