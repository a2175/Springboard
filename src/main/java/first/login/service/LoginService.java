package first.login.service;

import java.util.Map;

public interface LoginService {

	Map<String, Object> selectUserDetail(Map<String, Object> map);
	
	Map<String,Object> selectLoginCheck(Map<String, Object> map);
	
	boolean selectIdCheck(Map<String, Object> map);
	
	boolean selectNicknameCheck(Map<String, Object> map);

	void insertUser(Map<String, Object> map);

}
