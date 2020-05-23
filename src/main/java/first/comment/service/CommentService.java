package first.comment.service;

import java.util.List;
import java.util.Map;

import first.comment.vo.CommentVO;

public interface CommentService {

	List<CommentVO> selectCommentList(Map<String, Object> map);

	void insertComment(Map<String, Object> map);

	void deleteComment(Map<String, Object> map);

	int insertThumbsup(Map<String, Object> map);
	
	int insertThumbsdown(Map<String, Object> map);
	
}
