package first.login.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import first.login.mapper.LoginMapper;
import first.login.vo.CustomUserDetails;
import first.login.vo.UserVO;

@Repository
public class LoginDAO {

	private LoginMapper loginMapper;
	
    @Autowired
    public LoginDAO(LoginMapper loginMapper) {
    	this.loginMapper = loginMapper;
    }
		
	public UserVO selectUserByIdAndPassword(Map<String, Object> map) {
		return loginMapper.selectUserByIdAndPassword(map);
    }
		
	public UserVO selectUserById(Map<String, Object> map) {
		return loginMapper.selectUserById(map);
    }
	
	public UserVO selectUserByNickname(Map<String, Object> map) {
		return loginMapper.selectUserByNickname(map);
    }

	public void insertUser(Map<String, Object> map) {
		loginMapper.insertUser(map);
	}

	public CustomUserDetails selectUserById(String username){
		return loginMapper.selectUserById(username);
    }
	
	public List<String> selectAuthorityById(String username){
		return loginMapper.selectAuthorityById(username);
    }
	
}