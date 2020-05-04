package first.comment.dao;
 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import first.comment.mapper.CommentMapper;
import first.comment.vo.CommentVO;

@Repository
public class CommentDAO {
 	
	private CommentMapper commentMapper;
	
    @Autowired
    public CommentDAO(CommentMapper commentMapper) {
    	this.commentMapper = commentMapper;
    }
	
	public List<CommentVO> selectCommentList(Map<String, Object> map) {
		return commentMapper.selectCommentList(map);
	}

	public void insertComment(Map<String, Object> map) {
		commentMapper.insertComment(map);
	}

	public void deleteComment(Map<String, Object> map) {
		commentMapper.deleteComment(map);
	}
	
	public CommentVO checkThumbsup(Map<String, Object> map) {
		return commentMapper.checkThumbsup(map);
	}
	
	public void insertThumbsup(Map<String, Object> map) {
		commentMapper.insertThumbsup(map);
	}
	
	public CommentVO checkThumbsdown(Map<String, Object> map) {
		return commentMapper.checkThumbsdown(map);
	}
	
	public void insertThumbsdown(Map<String, Object> map) {
		commentMapper.insertThumbsdown(map);
	}
	
}