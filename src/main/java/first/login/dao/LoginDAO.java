package first.login.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import first.common.dao.AbstractDAO;

@Repository("loginDAO")
public class LoginDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
    public Map<String, Object> selectUserDetail(Map<String, Object> map) throws Exception{
        return (Map<String, Object>) selectOne("login.selectUserDetail", map);
    }
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectLoginCheck(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("login.selectLoginCheck", map);
    }
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectIdCheck(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("login.selectIdCheck", map);
    }

	public void insertUser(Map<String, Object> map) {
		insert("login.insertUser", map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectNicknameCheck(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("login.selectNicknameCheck", map);
    }
	
}