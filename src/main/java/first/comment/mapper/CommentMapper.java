package first.comment.mapper;

import java.util.List;
import java.util.Map;

import first.comment.vo.CommentVO;

public interface CommentMapper {
	List<CommentVO> selectCommentList(Map<String, Object> map);
	void insertComment(Map<String, Object> map);
	void deleteComment(Map<String, Object> map);
	CommentVO selectThumbsUpByIdAndIdx(Map<String, Object> map);
	void insertThumbsup(Map<String, Object> map);
	CommentVO selectThumbsDownByIdAndIdx(Map<String, Object> map);
	void insertThumbsdown(Map<String, Object> map);
}
