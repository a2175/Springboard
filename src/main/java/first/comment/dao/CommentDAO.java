package first.comment.dao;
 
import java.util.List;
import java.util.Map;
 
import org.springframework.stereotype.Repository;
 
import first.common.dao.AbstractDAO;
 
@Repository("commentDAO")
public class CommentDAO extends AbstractDAO{
 
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectCommentList(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>)selectList("sample.selectCommentList", map);
	}

	public void insertComment(Map<String, Object> map) {
		insert("sample.insertComment", map);
	}

	public void deleteComment(Map<String, Object> map) {
		update("sample.deleteComment", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> checkThumbsup(Map<String, Object> map) {
		return (Map<String, Object>) selectOne("sample.checkThumbsup", map);
	}
	
	public void insertThumbsup(Map<String, Object> map) {
		insert("sample.insertThumbsup", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> checkThumbsdown(Map<String, Object> map) {
		return (Map<String, Object>) selectOne("sample.checkThumbsdown", map);
	}
	
	public void insertThumbsdown(Map<String, Object> map) {
		insert("sample.insertThumbsdown", map);
	}
	
}