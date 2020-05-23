package first.login.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import first.login.dao.LoginDAO;
import first.login.vo.UserVO;

@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
@Service
public class LoginServiceImpl implements LoginService {
	Logger log = Logger.getLogger(this.getClass());
      
    private LoginDAO loginDAO;
    
    @Autowired
    public LoginServiceImpl(LoginDAO loginDAO) {
    	this.loginDAO = loginDAO;
    }
	
	@Override
	public boolean selectIdCheck(Map<String, Object> map) {
		UserVO result = loginDAO.selectUserById(map);
		
		return result == null ? false : true;
	}
	
	@Override
	public boolean selectNicknameCheck(Map<String, Object> map) {
		UserVO result = loginDAO.selectUserByNickname(map);
		
		return result == null ? false : true;
	}
	
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	@Override
	public void insertUser(Map<String, Object> map) {
		loginDAO.insertUser(map);
	}
}
