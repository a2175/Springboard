package first.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import first.login.dao.LoginDAO;
import first.login.vo.CustomUserDetails;

public class CustomUserDetailsService implements UserDetailsService {
    
    private LoginDAO loginDAO;
    
    @Autowired
    public CustomUserDetailsService(LoginDAO loginDAO) {
    	this.loginDAO = loginDAO;
    }
 
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUserDetails user = loginDAO.selectUserById(username);
    	
        if(user==null) {
            throw new UsernameNotFoundException(username);
        }
             
        List<String> authority = loginDAO.selectAuthorityById(username);
        user.setAuthority(authority);
        
        return user;
    }
 
}

