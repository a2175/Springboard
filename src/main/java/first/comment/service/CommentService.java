package first.comment.service;

import java.util.List;
import java.util.Map;

public interface CommentService {

	List<Map<String, Object>> selectCommentList(Map<String, Object> map);

	void insertComment(Map<String, Object> map);

	void deleteComment(Map<String, Object> map);

	Map<String, Object> checkThumbsup(Map<String, Object> map);

	void insertThumbsup(Map<String, Object> map);
	
	Map<String, Object> checkThumbsdown(Map<String, Object> map);

	void insertThumbsdown(Map<String, Object> map);
	
}
