package first.login.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import first.login.dao.LoginDAO;

@Service
public class LoginServiceImpl implements LoginService {
	Logger log = Logger.getLogger(this.getClass());
      
    private LoginDAO loginDAO;
    
    @Autowired
    public LoginServiceImpl(LoginDAO loginDAO) {
    	this.loginDAO = loginDAO;
    }
    
	@Override
	public Map<String, Object> selectUserDetail(Map<String, Object> map) {	
		return loginDAO.selectUserDetail(map);
	}

	@Override
	public Map<String,Object> selectLoginCheck(Map<String, Object> map) {
		Map<String, Object> result = loginDAO.selectLoginCheck(map);
		
		return result;
	}
	
	@Override
	public boolean selectIdCheck(Map<String, Object> map) {
		Map<String, Object> result = loginDAO.selectIdCheck(map);
			
		if(result == null)
			return false;
		else
			return true;
	}
	
	@Override
	public boolean selectNicknameCheck(Map<String, Object> map) {
		Map<String, Object> result = loginDAO.selectNicknameCheck(map);
		
		if(result == null)
			return false;
		else
			return true;
	}

	@Override
	public void insertUser(Map<String, Object> map) {
		loginDAO.insertUser(map);
	}
}
