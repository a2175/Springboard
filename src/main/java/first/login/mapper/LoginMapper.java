package first.login.mapper;

import java.util.List;
import java.util.Map;

import first.login.vo.CustomUserDetails;
import first.login.vo.UserVO;

public interface LoginMapper {
	
	UserVO selectUserByIdAndPassword(Map<String, Object> map);
	
	UserVO selectUserById(Map<String, Object> map);
	
	UserVO selectUserByNickname(Map<String, Object> map);
	
	void insertUser(Map<String, Object> map);
	
	CustomUserDetails selectUserById(String username);
	
	List<String> selectAuthorityById(String username);
}
