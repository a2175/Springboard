package first.comment.service;

import java.util.List;
import java.util.Map;

public interface CommentService {

	List<Map<String, Object>> selectCommentList(Map<String, Object> map) throws Exception;

	void insertComment(Map<String, Object> map) throws Exception;

	void deleteComment(Map<String, Object> map) throws Exception;

	Map<String, Object> checkThumbsup(Map<String, Object> map) throws Exception;

	void insertThumbsup(Map<String, Object> map) throws Exception;
	
	Map<String, Object> checkThumbsdown(Map<String, Object> map) throws Exception;

	void insertThumbsdown(Map<String, Object> map) throws Exception;
	
}
