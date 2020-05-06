package first.comment.dao;

import java.util.List;
import java.util.Map;

import first.comment.vo.CommentVO;

public interface CommentDAO {

	List<CommentVO> selectCommentList(Map<String, Object> map);
	
	void insertComment(Map<String, Object> map);
	
	void deleteComment(Map<String, Object> map);
	
	CommentVO checkThumbsup(Map<String, Object> map);
	
	void insertThumbsup(Map<String, Object> map);
	
	CommentVO checkThumbsdown(Map<String, Object> map);
	
	void insertThumbsdown(Map<String, Object> map);
	
}
