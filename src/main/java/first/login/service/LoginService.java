package first.login.service;

import java.util.Map;

public interface LoginService {
	
	boolean selectIdCheck(Map<String, Object> map);
	
	boolean selectNicknameCheck(Map<String, Object> map);

	void insertUser(Map<String, Object> map);

}
