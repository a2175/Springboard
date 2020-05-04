package first.login.service;

import java.util.Map;

import first.login.vo.UserVO;

public interface LoginService {

	UserVO selectLoginCheck(Map<String, Object> map);
	
	boolean selectIdCheck(Map<String, Object> map);
	
	boolean selectNicknameCheck(Map<String, Object> map);

	void insertUser(Map<String, Object> map);

}
