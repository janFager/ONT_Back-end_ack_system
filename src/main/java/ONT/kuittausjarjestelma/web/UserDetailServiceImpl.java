package ONT.kuittausjarjestelma.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ONT.kuittausjarjestelma.LoginFilter;
import ONT.kuittausjarjestelma.domain.User;
import ONT.kuittausjarjestelma.domain.UserRepository;

/**
 * This class is used by spring security to authenticate and authorize user
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService  {
	
	private final static Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);
	
	@Autowired
	private UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {   
    	User curruser = repository.findByUsername(username);
        UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPasswordHash(), true, true, true, true, AuthorityUtils.createAuthorityList(curruser.getRole()));
 
        return user;
    }   
      
} 
