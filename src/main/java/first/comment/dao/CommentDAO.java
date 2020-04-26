package first.comment.dao;
 
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import first.comment.vo.CommentVO;
import first.common.dao.AbstractDAO;

@SuppressWarnings("unchecked")
@Repository
public class CommentDAO extends AbstractDAO{
 	
	public List<CommentVO> selectCommentList(Map<String, Object> map) {
		return (List<CommentVO>) selectList("comment.selectCommentList", map);
	}

	public void insertComment(Map<String, Object> map) {
		insert("comment.insertComment", map);
	}

	public void deleteComment(Map<String, Object> map) {
		update("comment.deleteComment", map);
	}
	
	public CommentVO checkThumbsup(Map<String, Object> map) {
		return (CommentVO) selectOne("comment.checkThumbsup", map);
	}
	
	public void insertThumbsup(Map<String, Object> map) {
		insert("comment.insertThumbsup", map);
	}
	
	public CommentVO checkThumbsdown(Map<String, Object> map) {
		return (CommentVO) selectOne("comment.checkThumbsdown", map);
	}
	
	public void insertThumbsdown(Map<String, Object> map) {
		insert("comment.insertThumbsdown", map);
	}
	
}