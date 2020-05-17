package first.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import first.login.dao.LoginDAO;
import first.login.vo.UserVO;

public class CustomUserDetailsService implements UserDetailsService {
    
    private LoginDAO loginDAO;
    
    @Autowired
    public CustomUserDetailsService(LoginDAO loginDAO) {
    	this.loginDAO = loginDAO;
    }
 
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO user = loginDAO.selectUserByUsername(username);
    	
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
             
        List<String> authoritys = loginDAO.selectAuthorityByUsername(username);
        user.setAuthotitys(authoritys);
        
        return user;
    }
 
}

