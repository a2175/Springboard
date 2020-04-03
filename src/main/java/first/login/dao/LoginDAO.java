package first.login.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import first.common.dao.AbstractDAO;
import first.login.vo.CustomUserDetails;

@SuppressWarnings("unchecked")
@Repository
public class LoginDAO extends AbstractDAO{

    public Map<String, Object> selectUserDetail(Map<String, Object> map) {
        return (Map<String, Object>) selectOne("login.selectUserDetail", map);
    }
		
	public Map<String, Object> selectLoginCheck(Map<String, Object> map) {
		return (Map<String, Object>) selectOne("login.selectLoginCheck", map);
    }
		
	public Map<String, Object> selectIdCheck(Map<String, Object> map) {
		return (Map<String, Object>) selectOne("login.selectIdCheck", map);
    }

	public void insertUser(Map<String, Object> map) {
		insert("login.insertUser", map);
	}

	public Map<String, Object> selectNicknameCheck(Map<String, Object> map) {
		return (Map<String, Object>) selectOne("login.selectNicknameCheck", map);
    }
	
	public CustomUserDetails selectUserById(String username){
		return (CustomUserDetails) selectOne("login.selectUserById", username);
    }
	
	public List<String> selectAuthorityById(String username){
		return (List<String>) selectList("login.selectAuthorityById", username);
    }
	
}