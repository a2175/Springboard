package first.login.service;

import java.util.Map;

public interface LoginService {

	Map<String, Object> selectUserDetail(Map<String, Object> map) throws Exception;
	
	Map<String,Object> selectLoginCheck(Map<String, Object> map) throws Exception;
	
	boolean selectIdCheck(Map<String, Object> map) throws Exception;
	
	boolean selectNicknameCheck(Map<String, Object> map) throws Exception;

	void insertUser(Map<String, Object> map) throws Exception;

}
