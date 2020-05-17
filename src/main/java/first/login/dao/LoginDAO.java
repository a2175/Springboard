package first.login.dao;

import java.util.List;
import java.util.Map;

import first.login.vo.UserVO;

public interface LoginDAO {
	
	UserVO selectUserById(Map<String, Object> map);
	
	UserVO selectUserByNickname(Map<String, Object> map);
	
	void insertUser(Map<String, Object> map);
	
	UserVO selectUserByUsername(String username);
	
	List<String> selectAuthorityByUsername(String username);

}
